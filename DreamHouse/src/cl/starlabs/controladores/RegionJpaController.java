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
import cl.starlabs.modelo.Pais;
import cl.starlabs.modelo.Provincia;
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
public class RegionJpaController implements Serializable {

    public RegionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Region region) throws PreexistingEntityException, Exception {
        if (region.getProvinciaCollection() == null) {
            region.setProvinciaCollection(new ArrayList<Provincia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais pais = region.getPais();
            if (pais != null) {
                pais = em.getReference(pais.getClass(), pais.getIdpais());
                region.setPais(pais);
            }
            Collection<Provincia> attachedProvinciaCollection = new ArrayList<Provincia>();
            for (Provincia provinciaCollectionProvinciaToAttach : region.getProvinciaCollection()) {
                provinciaCollectionProvinciaToAttach = em.getReference(provinciaCollectionProvinciaToAttach.getClass(), provinciaCollectionProvinciaToAttach.getIdprovincia());
                attachedProvinciaCollection.add(provinciaCollectionProvinciaToAttach);
            }
            region.setProvinciaCollection(attachedProvinciaCollection);
            em.persist(region);
            if (pais != null) {
                pais.getRegionCollection().add(region);
                pais = em.merge(pais);
            }
            for (Provincia provinciaCollectionProvincia : region.getProvinciaCollection()) {
                Region oldRegionOfProvinciaCollectionProvincia = provinciaCollectionProvincia.getRegion();
                provinciaCollectionProvincia.setRegion(region);
                provinciaCollectionProvincia = em.merge(provinciaCollectionProvincia);
                if (oldRegionOfProvinciaCollectionProvincia != null) {
                    oldRegionOfProvinciaCollectionProvincia.getProvinciaCollection().remove(provinciaCollectionProvincia);
                    oldRegionOfProvinciaCollectionProvincia = em.merge(oldRegionOfProvinciaCollectionProvincia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegion(region.getIdregion()) != null) {
                throw new PreexistingEntityException("Region " + region + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Region region) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region persistentRegion = em.find(Region.class, region.getIdregion());
            Pais paisOld = persistentRegion.getPais();
            Pais paisNew = region.getPais();
            Collection<Provincia> provinciaCollectionOld = persistentRegion.getProvinciaCollection();
            Collection<Provincia> provinciaCollectionNew = region.getProvinciaCollection();
            List<String> illegalOrphanMessages = null;
            for (Provincia provinciaCollectionOldProvincia : provinciaCollectionOld) {
                if (!provinciaCollectionNew.contains(provinciaCollectionOldProvincia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Provincia " + provinciaCollectionOldProvincia + " since its region field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisNew != null) {
                paisNew = em.getReference(paisNew.getClass(), paisNew.getIdpais());
                region.setPais(paisNew);
            }
            Collection<Provincia> attachedProvinciaCollectionNew = new ArrayList<Provincia>();
            for (Provincia provinciaCollectionNewProvinciaToAttach : provinciaCollectionNew) {
                provinciaCollectionNewProvinciaToAttach = em.getReference(provinciaCollectionNewProvinciaToAttach.getClass(), provinciaCollectionNewProvinciaToAttach.getIdprovincia());
                attachedProvinciaCollectionNew.add(provinciaCollectionNewProvinciaToAttach);
            }
            provinciaCollectionNew = attachedProvinciaCollectionNew;
            region.setProvinciaCollection(provinciaCollectionNew);
            region = em.merge(region);
            if (paisOld != null && !paisOld.equals(paisNew)) {
                paisOld.getRegionCollection().remove(region);
                paisOld = em.merge(paisOld);
            }
            if (paisNew != null && !paisNew.equals(paisOld)) {
                paisNew.getRegionCollection().add(region);
                paisNew = em.merge(paisNew);
            }
            for (Provincia provinciaCollectionNewProvincia : provinciaCollectionNew) {
                if (!provinciaCollectionOld.contains(provinciaCollectionNewProvincia)) {
                    Region oldRegionOfProvinciaCollectionNewProvincia = provinciaCollectionNewProvincia.getRegion();
                    provinciaCollectionNewProvincia.setRegion(region);
                    provinciaCollectionNewProvincia = em.merge(provinciaCollectionNewProvincia);
                    if (oldRegionOfProvinciaCollectionNewProvincia != null && !oldRegionOfProvinciaCollectionNewProvincia.equals(region)) {
                        oldRegionOfProvinciaCollectionNewProvincia.getProvinciaCollection().remove(provinciaCollectionNewProvincia);
                        oldRegionOfProvinciaCollectionNewProvincia = em.merge(oldRegionOfProvinciaCollectionNewProvincia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = region.getIdregion();
                if (findRegion(id) == null) {
                    throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
            Region region;
            try {
                region = em.getReference(Region.class, id);
                region.getIdregion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Provincia> provinciaCollectionOrphanCheck = region.getProvinciaCollection();
            for (Provincia provinciaCollectionOrphanCheckProvincia : provinciaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Region (" + region + ") cannot be destroyed since the Provincia " + provinciaCollectionOrphanCheckProvincia + " in its provinciaCollection field has a non-nullable region field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais pais = region.getPais();
            if (pais != null) {
                pais.getRegionCollection().remove(region);
                pais = em.merge(pais);
            }
            em.remove(region);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Region> findRegionEntities() {
        return findRegionEntities(true, -1, -1);
    }

    public List<Region> findRegionEntities(int maxResults, int firstResult) {
        return findRegionEntities(false, maxResults, firstResult);
    }

    private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Region.class));
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

    public Region findRegion(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Region.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Region> rt = cq.from(Region.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
