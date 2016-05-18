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
import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Historialbusqueda;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class HistorialbusquedaJpaController implements Serializable {

    public HistorialbusquedaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historialbusqueda historialbusqueda) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = historialbusqueda.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getNumcliente());
                historialbusqueda.setCliente(cliente);
            }
            Propiedad propiedad = historialbusqueda.getPropiedad();
            if (propiedad != null) {
                propiedad = em.getReference(propiedad.getClass(), propiedad.getNumpropiedad());
                historialbusqueda.setPropiedad(propiedad);
            }
            em.persist(historialbusqueda);
            if (cliente != null) {
                cliente.getHistorialbusquedaCollection().add(historialbusqueda);
                cliente = em.merge(cliente);
            }
            if (propiedad != null) {
                propiedad.getHistorialbusquedaCollection().add(historialbusqueda);
                propiedad = em.merge(propiedad);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistorialbusqueda(historialbusqueda.getId()) != null) {
                throw new PreexistingEntityException("Historialbusqueda " + historialbusqueda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historialbusqueda historialbusqueda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historialbusqueda persistentHistorialbusqueda = em.find(Historialbusqueda.class, historialbusqueda.getId());
            Cliente clienteOld = persistentHistorialbusqueda.getCliente();
            Cliente clienteNew = historialbusqueda.getCliente();
            Propiedad propiedadOld = persistentHistorialbusqueda.getPropiedad();
            Propiedad propiedadNew = historialbusqueda.getPropiedad();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getNumcliente());
                historialbusqueda.setCliente(clienteNew);
            }
            if (propiedadNew != null) {
                propiedadNew = em.getReference(propiedadNew.getClass(), propiedadNew.getNumpropiedad());
                historialbusqueda.setPropiedad(propiedadNew);
            }
            historialbusqueda = em.merge(historialbusqueda);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getHistorialbusquedaCollection().remove(historialbusqueda);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getHistorialbusquedaCollection().add(historialbusqueda);
                clienteNew = em.merge(clienteNew);
            }
            if (propiedadOld != null && !propiedadOld.equals(propiedadNew)) {
                propiedadOld.getHistorialbusquedaCollection().remove(historialbusqueda);
                propiedadOld = em.merge(propiedadOld);
            }
            if (propiedadNew != null && !propiedadNew.equals(propiedadOld)) {
                propiedadNew.getHistorialbusquedaCollection().add(historialbusqueda);
                propiedadNew = em.merge(propiedadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = historialbusqueda.getId();
                if (findHistorialbusqueda(id) == null) {
                    throw new NonexistentEntityException("The historialbusqueda with id " + id + " no longer exists.");
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
            Historialbusqueda historialbusqueda;
            try {
                historialbusqueda = em.getReference(Historialbusqueda.class, id);
                historialbusqueda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialbusqueda with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = historialbusqueda.getCliente();
            if (cliente != null) {
                cliente.getHistorialbusquedaCollection().remove(historialbusqueda);
                cliente = em.merge(cliente);
            }
            Propiedad propiedad = historialbusqueda.getPropiedad();
            if (propiedad != null) {
                propiedad.getHistorialbusquedaCollection().remove(historialbusqueda);
                propiedad = em.merge(propiedad);
            }
            em.remove(historialbusqueda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historialbusqueda> findHistorialbusquedaEntities() {
        return findHistorialbusquedaEntities(true, -1, -1);
    }

    public List<Historialbusqueda> findHistorialbusquedaEntities(int maxResults, int firstResult) {
        return findHistorialbusquedaEntities(false, maxResults, firstResult);
    }

    private List<Historialbusqueda> findHistorialbusquedaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historialbusqueda.class));
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

    public Historialbusqueda findHistorialbusqueda(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historialbusqueda.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialbusquedaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historialbusqueda> rt = cq.from(Historialbusqueda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
