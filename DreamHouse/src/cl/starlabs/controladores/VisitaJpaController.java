/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Visita;
import cl.starlabs.modelo.VisitaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class VisitaJpaController implements Serializable {

    public VisitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visita visita) throws PreexistingEntityException, Exception {
        if (visita.getVisitaPK() == null) {
            visita.setVisitaPK(new VisitaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda eventoagenda = visita.getEventoagenda();
            if (eventoagenda != null) {
                eventoagenda = em.getReference(eventoagenda.getClass(), eventoagenda.getId());
                visita.setEventoagenda(eventoagenda);
            }
            em.persist(visita);
            if (eventoagenda != null) {
                eventoagenda.getVisitaCollection().add(visita);
                eventoagenda = em.merge(eventoagenda);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVisita(visita.getVisitaPK()) != null) {
                throw new PreexistingEntityException("Visita " + visita + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visita visita) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visita persistentVisita = em.find(Visita.class, visita.getVisitaPK());
            Agenda eventoagendaOld = persistentVisita.getEventoagenda();
            Agenda eventoagendaNew = visita.getEventoagenda();
            if (eventoagendaNew != null) {
                eventoagendaNew = em.getReference(eventoagendaNew.getClass(), eventoagendaNew.getId());
                visita.setEventoagenda(eventoagendaNew);
            }
            visita = em.merge(visita);
            if (eventoagendaOld != null && !eventoagendaOld.equals(eventoagendaNew)) {
                eventoagendaOld.getVisitaCollection().remove(visita);
                eventoagendaOld = em.merge(eventoagendaOld);
            }
            if (eventoagendaNew != null && !eventoagendaNew.equals(eventoagendaOld)) {
                eventoagendaNew.getVisitaCollection().add(visita);
                eventoagendaNew = em.merge(eventoagendaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                VisitaPK id = visita.getVisitaPK();
                if (findVisita(id) == null) {
                    throw new NonexistentEntityException("The visita with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(VisitaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Visita visita;
            try {
                visita = em.getReference(Visita.class, id);
                visita.getVisitaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visita with id " + id + " no longer exists.", enfe);
            }
            Agenda eventoagenda = visita.getEventoagenda();
            if (eventoagenda != null) {
                eventoagenda.getVisitaCollection().remove(visita);
                eventoagenda = em.merge(eventoagenda);
            }
            em.remove(visita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visita> findVisitaEntities() {
        return findVisitaEntities(true, -1, -1);
    }

    public List<Visita> findVisitaEntities(int maxResults, int firstResult) {
        return findVisitaEntities(false, maxResults, firstResult);
    }

    private List<Visita> findVisitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visita.class));
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

    public Visita findVisita(VisitaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visita.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visita> rt = cq.from(Visita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
