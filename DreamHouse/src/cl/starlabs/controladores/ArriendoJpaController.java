/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Arriendo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cetecom
 */
public class ArriendoJpaController implements Serializable {

    public ArriendoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Arriendo arriendo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente numcliente = arriendo.getNumcliente();
            if (numcliente != null) {
                numcliente = em.getReference(numcliente.getClass(), numcliente.getNumcliente());
                arriendo.setNumcliente(numcliente);
            }
            Propiedad numpropiedad = arriendo.getNumpropiedad();
            if (numpropiedad != null) {
                numpropiedad = em.getReference(numpropiedad.getClass(), numpropiedad.getNumpropiedad());
                arriendo.setNumpropiedad(numpropiedad);
            }
            em.persist(arriendo);
            if (numcliente != null) {
                numcliente.getArriendoCollection().add(arriendo);
                numcliente = em.merge(numcliente);
            }
            if (numpropiedad != null) {
                numpropiedad.getArriendoCollection().add(arriendo);
                numpropiedad = em.merge(numpropiedad);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArriendo(arriendo.getNumarriendo()) != null) {
                throw new PreexistingEntityException("Arriendo " + arriendo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Arriendo arriendo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Arriendo persistentArriendo = em.find(Arriendo.class, arriendo.getNumarriendo());
            Cliente numclienteOld = persistentArriendo.getNumcliente();
            Cliente numclienteNew = arriendo.getNumcliente();
            Propiedad numpropiedadOld = persistentArriendo.getNumpropiedad();
            Propiedad numpropiedadNew = arriendo.getNumpropiedad();
            if (numclienteNew != null) {
                numclienteNew = em.getReference(numclienteNew.getClass(), numclienteNew.getNumcliente());
                arriendo.setNumcliente(numclienteNew);
            }
            if (numpropiedadNew != null) {
                numpropiedadNew = em.getReference(numpropiedadNew.getClass(), numpropiedadNew.getNumpropiedad());
                arriendo.setNumpropiedad(numpropiedadNew);
            }
            arriendo = em.merge(arriendo);
            if (numclienteOld != null && !numclienteOld.equals(numclienteNew)) {
                numclienteOld.getArriendoCollection().remove(arriendo);
                numclienteOld = em.merge(numclienteOld);
            }
            if (numclienteNew != null && !numclienteNew.equals(numclienteOld)) {
                numclienteNew.getArriendoCollection().add(arriendo);
                numclienteNew = em.merge(numclienteNew);
            }
            if (numpropiedadOld != null && !numpropiedadOld.equals(numpropiedadNew)) {
                numpropiedadOld.getArriendoCollection().remove(arriendo);
                numpropiedadOld = em.merge(numpropiedadOld);
            }
            if (numpropiedadNew != null && !numpropiedadNew.equals(numpropiedadOld)) {
                numpropiedadNew.getArriendoCollection().add(arriendo);
                numpropiedadNew = em.merge(numpropiedadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = arriendo.getNumarriendo();
                if (findArriendo(id) == null) {
                    throw new NonexistentEntityException("The arriendo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Arriendo arriendo;
            try {
                arriendo = em.getReference(Arriendo.class, id);
                arriendo.getNumarriendo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The arriendo with id " + id + " no longer exists.", enfe);
            }
            Cliente numcliente = arriendo.getNumcliente();
            if (numcliente != null) {
                numcliente.getArriendoCollection().remove(arriendo);
                numcliente = em.merge(numcliente);
            }
            Propiedad numpropiedad = arriendo.getNumpropiedad();
            if (numpropiedad != null) {
                numpropiedad.getArriendoCollection().remove(arriendo);
                numpropiedad = em.merge(numpropiedad);
            }
            em.remove(arriendo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Arriendo> findArriendoEntities() {
        return findArriendoEntities(true, -1, -1);
    }

    public List<Arriendo> findArriendoEntities(int maxResults, int firstResult) {
        return findArriendoEntities(false, maxResults, firstResult);
    }

    private List<Arriendo> findArriendoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Arriendo.class));
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

    public Arriendo findArriendo(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Arriendo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArriendoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Arriendo> rt = cq.from(Arriendo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
