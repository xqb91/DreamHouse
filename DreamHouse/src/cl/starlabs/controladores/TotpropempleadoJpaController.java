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
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Totpropempleado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class TotpropempleadoJpaController implements Serializable {

    public TotpropempleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Totpropempleado totpropempleado) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Empleado empleadoOrphanCheck = totpropempleado.getEmpleado();
        if (empleadoOrphanCheck != null) {
            Totpropempleado oldTotpropempleadoOfEmpleado = empleadoOrphanCheck.getTotpropempleado();
            if (oldTotpropempleadoOfEmpleado != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Empleado " + empleadoOrphanCheck + " already has an item of type Totpropempleado whose empleado column cannot be null. Please make another selection for the empleado field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = totpropempleado.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId());
                totpropempleado.setEmpleado(empleado);
            }
            em.persist(totpropempleado);
            if (empleado != null) {
                empleado.setTotpropempleado(totpropempleado);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTotpropempleado(totpropempleado.getNumempleado()) != null) {
                throw new PreexistingEntityException("Totpropempleado " + totpropempleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Totpropempleado totpropempleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Totpropempleado persistentTotpropempleado = em.find(Totpropempleado.class, totpropempleado.getNumempleado());
            Empleado empleadoOld = persistentTotpropempleado.getEmpleado();
            Empleado empleadoNew = totpropempleado.getEmpleado();
            List<String> illegalOrphanMessages = null;
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                Totpropempleado oldTotpropempleadoOfEmpleado = empleadoNew.getTotpropempleado();
                if (oldTotpropempleadoOfEmpleado != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Empleado " + empleadoNew + " already has an item of type Totpropempleado whose empleado column cannot be null. Please make another selection for the empleado field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId());
                totpropempleado.setEmpleado(empleadoNew);
            }
            totpropempleado = em.merge(totpropempleado);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.setTotpropempleado(null);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.setTotpropempleado(totpropempleado);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = totpropempleado.getNumempleado();
                if (findTotpropempleado(id) == null) {
                    throw new NonexistentEntityException("The totpropempleado with id " + id + " no longer exists.");
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
            Totpropempleado totpropempleado;
            try {
                totpropempleado = em.getReference(Totpropempleado.class, id);
                totpropempleado.getNumempleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The totpropempleado with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = totpropempleado.getEmpleado();
            if (empleado != null) {
                empleado.setTotpropempleado(null);
                empleado = em.merge(empleado);
            }
            em.remove(totpropempleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Totpropempleado> findTotpropempleadoEntities() {
        return findTotpropempleadoEntities(true, -1, -1);
    }

    public List<Totpropempleado> findTotpropempleadoEntities(int maxResults, int firstResult) {
        return findTotpropempleadoEntities(false, maxResults, firstResult);
    }

    private List<Totpropempleado> findTotpropempleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Totpropempleado.class));
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

    public Totpropempleado findTotpropempleado(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Totpropempleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTotpropempleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Totpropempleado> rt = cq.from(Totpropempleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
