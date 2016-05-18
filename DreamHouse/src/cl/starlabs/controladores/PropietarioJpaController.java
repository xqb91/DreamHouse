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
import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Propiedad;
import cl.starlabs.modelo.Propietario;
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
public class PropietarioJpaController implements Serializable {

    public PropietarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propietario propietario) throws PreexistingEntityException, Exception {
        if (propietario.getPropiedadCollection() == null) {
            propietario.setPropiedadCollection(new ArrayList<Propiedad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad = propietario.getCiudad();
            if (ciudad != null) {
                ciudad = em.getReference(ciudad.getClass(), ciudad.getIdciudad());
                propietario.setCiudad(ciudad);
            }
            Collection<Propiedad> attachedPropiedadCollection = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionPropiedadToAttach : propietario.getPropiedadCollection()) {
                propiedadCollectionPropiedadToAttach = em.getReference(propiedadCollectionPropiedadToAttach.getClass(), propiedadCollectionPropiedadToAttach.getNumpropiedad());
                attachedPropiedadCollection.add(propiedadCollectionPropiedadToAttach);
            }
            propietario.setPropiedadCollection(attachedPropiedadCollection);
            em.persist(propietario);
            if (ciudad != null) {
                ciudad.getPropietarioCollection().add(propietario);
                ciudad = em.merge(ciudad);
            }
            for (Propiedad propiedadCollectionPropiedad : propietario.getPropiedadCollection()) {
                Propietario oldNumpropietarioOfPropiedadCollectionPropiedad = propiedadCollectionPropiedad.getNumpropietario();
                propiedadCollectionPropiedad.setNumpropietario(propietario);
                propiedadCollectionPropiedad = em.merge(propiedadCollectionPropiedad);
                if (oldNumpropietarioOfPropiedadCollectionPropiedad != null) {
                    oldNumpropietarioOfPropiedadCollectionPropiedad.getPropiedadCollection().remove(propiedadCollectionPropiedad);
                    oldNumpropietarioOfPropiedadCollectionPropiedad = em.merge(oldNumpropietarioOfPropiedadCollectionPropiedad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropietario(propietario.getNumpropietario()) != null) {
                throw new PreexistingEntityException("Propietario " + propietario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propietario propietario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propietario persistentPropietario = em.find(Propietario.class, propietario.getNumpropietario());
            Ciudad ciudadOld = persistentPropietario.getCiudad();
            Ciudad ciudadNew = propietario.getCiudad();
            Collection<Propiedad> propiedadCollectionOld = persistentPropietario.getPropiedadCollection();
            Collection<Propiedad> propiedadCollectionNew = propietario.getPropiedadCollection();
            List<String> illegalOrphanMessages = null;
            for (Propiedad propiedadCollectionOldPropiedad : propiedadCollectionOld) {
                if (!propiedadCollectionNew.contains(propiedadCollectionOldPropiedad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propiedad " + propiedadCollectionOldPropiedad + " since its numpropietario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadNew != null) {
                ciudadNew = em.getReference(ciudadNew.getClass(), ciudadNew.getIdciudad());
                propietario.setCiudad(ciudadNew);
            }
            Collection<Propiedad> attachedPropiedadCollectionNew = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionNewPropiedadToAttach : propiedadCollectionNew) {
                propiedadCollectionNewPropiedadToAttach = em.getReference(propiedadCollectionNewPropiedadToAttach.getClass(), propiedadCollectionNewPropiedadToAttach.getNumpropiedad());
                attachedPropiedadCollectionNew.add(propiedadCollectionNewPropiedadToAttach);
            }
            propiedadCollectionNew = attachedPropiedadCollectionNew;
            propietario.setPropiedadCollection(propiedadCollectionNew);
            propietario = em.merge(propietario);
            if (ciudadOld != null && !ciudadOld.equals(ciudadNew)) {
                ciudadOld.getPropietarioCollection().remove(propietario);
                ciudadOld = em.merge(ciudadOld);
            }
            if (ciudadNew != null && !ciudadNew.equals(ciudadOld)) {
                ciudadNew.getPropietarioCollection().add(propietario);
                ciudadNew = em.merge(ciudadNew);
            }
            for (Propiedad propiedadCollectionNewPropiedad : propiedadCollectionNew) {
                if (!propiedadCollectionOld.contains(propiedadCollectionNewPropiedad)) {
                    Propietario oldNumpropietarioOfPropiedadCollectionNewPropiedad = propiedadCollectionNewPropiedad.getNumpropietario();
                    propiedadCollectionNewPropiedad.setNumpropietario(propietario);
                    propiedadCollectionNewPropiedad = em.merge(propiedadCollectionNewPropiedad);
                    if (oldNumpropietarioOfPropiedadCollectionNewPropiedad != null && !oldNumpropietarioOfPropiedadCollectionNewPropiedad.equals(propietario)) {
                        oldNumpropietarioOfPropiedadCollectionNewPropiedad.getPropiedadCollection().remove(propiedadCollectionNewPropiedad);
                        oldNumpropietarioOfPropiedadCollectionNewPropiedad = em.merge(oldNumpropietarioOfPropiedadCollectionNewPropiedad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = propietario.getNumpropietario();
                if (findPropietario(id) == null) {
                    throw new NonexistentEntityException("The propietario with id " + id + " no longer exists.");
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
            Propietario propietario;
            try {
                propietario = em.getReference(Propietario.class, id);
                propietario.getNumpropietario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propietario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Propiedad> propiedadCollectionOrphanCheck = propietario.getPropiedadCollection();
            for (Propiedad propiedadCollectionOrphanCheckPropiedad : propiedadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propietario (" + propietario + ") cannot be destroyed since the Propiedad " + propiedadCollectionOrphanCheckPropiedad + " in its propiedadCollection field has a non-nullable numpropietario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudad = propietario.getCiudad();
            if (ciudad != null) {
                ciudad.getPropietarioCollection().remove(propietario);
                ciudad = em.merge(ciudad);
            }
            em.remove(propietario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propietario> findPropietarioEntities() {
        return findPropietarioEntities(true, -1, -1);
    }

    public List<Propietario> findPropietarioEntities(int maxResults, int firstResult) {
        return findPropietarioEntities(false, maxResults, firstResult);
    }

    private List<Propietario> findPropietarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propietario.class));
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

    public Propietario findPropietario(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propietario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropietarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propietario> rt = cq.from(Propietario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
