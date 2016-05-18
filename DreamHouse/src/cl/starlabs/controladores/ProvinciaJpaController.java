/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Region;
import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Provincia;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class ProvinciaJpaController implements Serializable {

    public ProvinciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provincia provincia) throws PreexistingEntityException, Exception {
        if (provincia.getCiudadCollection() == null) {
            provincia.setCiudadCollection(new ArrayList<Ciudad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region region = provincia.getRegion();
            if (region != null) {
                region = em.getReference(region.getClass(), region.getIdregion());
                provincia.setRegion(region);
            }
            Collection<Ciudad> attachedCiudadCollection = new ArrayList<Ciudad>();
            for (Ciudad ciudadCollectionCiudadToAttach : provincia.getCiudadCollection()) {
                ciudadCollectionCiudadToAttach = em.getReference(ciudadCollectionCiudadToAttach.getClass(), ciudadCollectionCiudadToAttach.getIdciudad());
                attachedCiudadCollection.add(ciudadCollectionCiudadToAttach);
            }
            provincia.setCiudadCollection(attachedCiudadCollection);
            em.persist(provincia);
            if (region != null) {
                region.getProvinciaCollection().add(provincia);
                region = em.merge(region);
            }
            for (Ciudad ciudadCollectionCiudad : provincia.getCiudadCollection()) {
                Provincia oldProvinciaOfCiudadCollectionCiudad = ciudadCollectionCiudad.getProvincia();
                ciudadCollectionCiudad.setProvincia(provincia);
                ciudadCollectionCiudad = em.merge(ciudadCollectionCiudad);
                if (oldProvinciaOfCiudadCollectionCiudad != null) {
                    oldProvinciaOfCiudadCollectionCiudad.getCiudadCollection().remove(ciudadCollectionCiudad);
                    oldProvinciaOfCiudadCollectionCiudad = em.merge(oldProvinciaOfCiudadCollectionCiudad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvincia(provincia.getIdprovincia()) != null) {
                throw new PreexistingEntityException("Provincia " + provincia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provincia provincia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia persistentProvincia = em.find(Provincia.class, provincia.getIdprovincia());
            Region regionOld = persistentProvincia.getRegion();
            Region regionNew = provincia.getRegion();
            Collection<Ciudad> ciudadCollectionOld = persistentProvincia.getCiudadCollection();
            Collection<Ciudad> ciudadCollectionNew = provincia.getCiudadCollection();
            List<String> illegalOrphanMessages = null;
            for (Ciudad ciudadCollectionOldCiudad : ciudadCollectionOld) {
                if (!ciudadCollectionNew.contains(ciudadCollectionOldCiudad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ciudad " + ciudadCollectionOldCiudad + " since its provincia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (regionNew != null) {
                regionNew = em.getReference(regionNew.getClass(), regionNew.getIdregion());
                provincia.setRegion(regionNew);
            }
            Collection<Ciudad> attachedCiudadCollectionNew = new ArrayList<Ciudad>();
            for (Ciudad ciudadCollectionNewCiudadToAttach : ciudadCollectionNew) {
                ciudadCollectionNewCiudadToAttach = em.getReference(ciudadCollectionNewCiudadToAttach.getClass(), ciudadCollectionNewCiudadToAttach.getIdciudad());
                attachedCiudadCollectionNew.add(ciudadCollectionNewCiudadToAttach);
            }
            ciudadCollectionNew = attachedCiudadCollectionNew;
            provincia.setCiudadCollection(ciudadCollectionNew);
            provincia = em.merge(provincia);
            if (regionOld != null && !regionOld.equals(regionNew)) {
                regionOld.getProvinciaCollection().remove(provincia);
                regionOld = em.merge(regionOld);
            }
            if (regionNew != null && !regionNew.equals(regionOld)) {
                regionNew.getProvinciaCollection().add(provincia);
                regionNew = em.merge(regionNew);
            }
            for (Ciudad ciudadCollectionNewCiudad : ciudadCollectionNew) {
                if (!ciudadCollectionOld.contains(ciudadCollectionNewCiudad)) {
                    Provincia oldProvinciaOfCiudadCollectionNewCiudad = ciudadCollectionNewCiudad.getProvincia();
                    ciudadCollectionNewCiudad.setProvincia(provincia);
                    ciudadCollectionNewCiudad = em.merge(ciudadCollectionNewCiudad);
                    if (oldProvinciaOfCiudadCollectionNewCiudad != null && !oldProvinciaOfCiudadCollectionNewCiudad.equals(provincia)) {
                        oldProvinciaOfCiudadCollectionNewCiudad.getCiudadCollection().remove(ciudadCollectionNewCiudad);
                        oldProvinciaOfCiudadCollectionNewCiudad = em.merge(oldProvinciaOfCiudadCollectionNewCiudad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = provincia.getIdprovincia();
                if (findProvincia(id) == null) {
                    throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia provincia;
            try {
                provincia = em.getReference(Provincia.class, id);
                provincia.getIdprovincia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ciudad> ciudadCollectionOrphanCheck = provincia.getCiudadCollection();
            for (Ciudad ciudadCollectionOrphanCheckCiudad : ciudadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Provincia (" + provincia + ") cannot be destroyed since the Ciudad " + ciudadCollectionOrphanCheckCiudad + " in its ciudadCollection field has a non-nullable provincia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Region region = provincia.getRegion();
            if (region != null) {
                region.getProvinciaCollection().remove(provincia);
                region = em.merge(region);
            }
            em.remove(provincia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provincia> findProvinciaEntities() {
        return findProvinciaEntities(true, -1, -1);
    }

    public List<Provincia> findProvinciaEntities(int maxResults, int firstResult) {
        return findProvinciaEntities(false, maxResults, firstResult);
    }

    private List<Provincia> findProvinciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provincia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Provincia findProvincia(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provincia.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provincia> rt = cq.from(Provincia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
