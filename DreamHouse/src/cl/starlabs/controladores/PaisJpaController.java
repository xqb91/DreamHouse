/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Pais;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Region;
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
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws PreexistingEntityException, Exception {
        if (pais.getRegionCollection() == null) {
            pais.setRegionCollection(new ArrayList<Region>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Region> attachedRegionCollection = new ArrayList<Region>();
            for (Region regionCollectionRegionToAttach : pais.getRegionCollection()) {
                regionCollectionRegionToAttach = em.getReference(regionCollectionRegionToAttach.getClass(), regionCollectionRegionToAttach.getIdregion());
                attachedRegionCollection.add(regionCollectionRegionToAttach);
            }
            pais.setRegionCollection(attachedRegionCollection);
            em.persist(pais);
            for (Region regionCollectionRegion : pais.getRegionCollection()) {
                Pais oldPaisOfRegionCollectionRegion = regionCollectionRegion.getPais();
                regionCollectionRegion.setPais(pais);
                regionCollectionRegion = em.merge(regionCollectionRegion);
                if (oldPaisOfRegionCollectionRegion != null) {
                    oldPaisOfRegionCollectionRegion.getRegionCollection().remove(regionCollectionRegion);
                    oldPaisOfRegionCollectionRegion = em.merge(oldPaisOfRegionCollectionRegion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPais(pais.getIdpais()) != null) {
                throw new PreexistingEntityException("Pais " + pais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdpais());
            Collection<Region> regionCollectionOld = persistentPais.getRegionCollection();
            Collection<Region> regionCollectionNew = pais.getRegionCollection();
            List<String> illegalOrphanMessages = null;
            for (Region regionCollectionOldRegion : regionCollectionOld) {
                if (!regionCollectionNew.contains(regionCollectionOldRegion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Region " + regionCollectionOldRegion + " since its pais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Region> attachedRegionCollectionNew = new ArrayList<Region>();
            for (Region regionCollectionNewRegionToAttach : regionCollectionNew) {
                regionCollectionNewRegionToAttach = em.getReference(regionCollectionNewRegionToAttach.getClass(), regionCollectionNewRegionToAttach.getIdregion());
                attachedRegionCollectionNew.add(regionCollectionNewRegionToAttach);
            }
            regionCollectionNew = attachedRegionCollectionNew;
            pais.setRegionCollection(regionCollectionNew);
            pais = em.merge(pais);
            for (Region regionCollectionNewRegion : regionCollectionNew) {
                if (!regionCollectionOld.contains(regionCollectionNewRegion)) {
                    Pais oldPaisOfRegionCollectionNewRegion = regionCollectionNewRegion.getPais();
                    regionCollectionNewRegion.setPais(pais);
                    regionCollectionNewRegion = em.merge(regionCollectionNewRegion);
                    if (oldPaisOfRegionCollectionNewRegion != null && !oldPaisOfRegionCollectionNewRegion.equals(pais)) {
                        oldPaisOfRegionCollectionNewRegion.getRegionCollection().remove(regionCollectionNewRegion);
                        oldPaisOfRegionCollectionNewRegion = em.merge(oldPaisOfRegionCollectionNewRegion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pais.getIdpais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdpais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Region> regionCollectionOrphanCheck = pais.getRegionCollection();
            for (Region regionCollectionOrphanCheckRegion : regionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Region " + regionCollectionOrphanCheckRegion + " in its regionCollection field has a non-nullable pais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
