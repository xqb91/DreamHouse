/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Agenda;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Propiedad;
import cl.starlabs.modelo.Visita;
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
public class AgendaJpaController implements Serializable {

    public AgendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agenda agenda) throws PreexistingEntityException, Exception {
        if (agenda.getVisitaCollection() == null) {
            agenda.setVisitaCollection(new ArrayList<Visita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = agenda.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getNumcliente());
                agenda.setCliente(cliente);
            }
            Empleado empleado = agenda.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId());
                agenda.setEmpleado(empleado);
            }
            Propiedad propiedad = agenda.getPropiedad();
            if (propiedad != null) {
                propiedad = em.getReference(propiedad.getClass(), propiedad.getNumpropiedad());
                agenda.setPropiedad(propiedad);
            }
            Collection<Visita> attachedVisitaCollection = new ArrayList<Visita>();
            for (Visita visitaCollectionVisitaToAttach : agenda.getVisitaCollection()) {
                visitaCollectionVisitaToAttach = em.getReference(visitaCollectionVisitaToAttach.getClass(), visitaCollectionVisitaToAttach.getVisitaPK());
                attachedVisitaCollection.add(visitaCollectionVisitaToAttach);
            }
            agenda.setVisitaCollection(attachedVisitaCollection);
            em.persist(agenda);
            if (cliente != null) {
                cliente.getAgendaCollection().add(agenda);
                cliente = em.merge(cliente);
            }
            if (empleado != null) {
                empleado.getAgendaCollection().add(agenda);
                empleado = em.merge(empleado);
            }
            if (propiedad != null) {
                propiedad.getAgendaCollection().add(agenda);
                propiedad = em.merge(propiedad);
            }
            for (Visita visitaCollectionVisita : agenda.getVisitaCollection()) {
                Agenda oldEventoagendaOfVisitaCollectionVisita = visitaCollectionVisita.getEventoagenda();
                visitaCollectionVisita.setEventoagenda(agenda);
                visitaCollectionVisita = em.merge(visitaCollectionVisita);
                if (oldEventoagendaOfVisitaCollectionVisita != null) {
                    oldEventoagendaOfVisitaCollectionVisita.getVisitaCollection().remove(visitaCollectionVisita);
                    oldEventoagendaOfVisitaCollectionVisita = em.merge(oldEventoagendaOfVisitaCollectionVisita);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAgenda(agenda.getId()) != null) {
                throw new PreexistingEntityException("Agenda " + agenda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agenda agenda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda persistentAgenda = em.find(Agenda.class, agenda.getId());
            Cliente clienteOld = persistentAgenda.getCliente();
            Cliente clienteNew = agenda.getCliente();
            Empleado empleadoOld = persistentAgenda.getEmpleado();
            Empleado empleadoNew = agenda.getEmpleado();
            Propiedad propiedadOld = persistentAgenda.getPropiedad();
            Propiedad propiedadNew = agenda.getPropiedad();
            Collection<Visita> visitaCollectionOld = persistentAgenda.getVisitaCollection();
            Collection<Visita> visitaCollectionNew = agenda.getVisitaCollection();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getNumcliente());
                agenda.setCliente(clienteNew);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId());
                agenda.setEmpleado(empleadoNew);
            }
            if (propiedadNew != null) {
                propiedadNew = em.getReference(propiedadNew.getClass(), propiedadNew.getNumpropiedad());
                agenda.setPropiedad(propiedadNew);
            }
            Collection<Visita> attachedVisitaCollectionNew = new ArrayList<Visita>();
            for (Visita visitaCollectionNewVisitaToAttach : visitaCollectionNew) {
                visitaCollectionNewVisitaToAttach = em.getReference(visitaCollectionNewVisitaToAttach.getClass(), visitaCollectionNewVisitaToAttach.getVisitaPK());
                attachedVisitaCollectionNew.add(visitaCollectionNewVisitaToAttach);
            }
            visitaCollectionNew = attachedVisitaCollectionNew;
            agenda.setVisitaCollection(visitaCollectionNew);
            agenda = em.merge(agenda);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getAgendaCollection().remove(agenda);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getAgendaCollection().add(agenda);
                clienteNew = em.merge(clienteNew);
            }
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getAgendaCollection().remove(agenda);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getAgendaCollection().add(agenda);
                empleadoNew = em.merge(empleadoNew);
            }
            if (propiedadOld != null && !propiedadOld.equals(propiedadNew)) {
                propiedadOld.getAgendaCollection().remove(agenda);
                propiedadOld = em.merge(propiedadOld);
            }
            if (propiedadNew != null && !propiedadNew.equals(propiedadOld)) {
                propiedadNew.getAgendaCollection().add(agenda);
                propiedadNew = em.merge(propiedadNew);
            }
            for (Visita visitaCollectionOldVisita : visitaCollectionOld) {
                if (!visitaCollectionNew.contains(visitaCollectionOldVisita)) {
                    visitaCollectionOldVisita.setEventoagenda(null);
                    visitaCollectionOldVisita = em.merge(visitaCollectionOldVisita);
                }
            }
            for (Visita visitaCollectionNewVisita : visitaCollectionNew) {
                if (!visitaCollectionOld.contains(visitaCollectionNewVisita)) {
                    Agenda oldEventoagendaOfVisitaCollectionNewVisita = visitaCollectionNewVisita.getEventoagenda();
                    visitaCollectionNewVisita.setEventoagenda(agenda);
                    visitaCollectionNewVisita = em.merge(visitaCollectionNewVisita);
                    if (oldEventoagendaOfVisitaCollectionNewVisita != null && !oldEventoagendaOfVisitaCollectionNewVisita.equals(agenda)) {
                        oldEventoagendaOfVisitaCollectionNewVisita.getVisitaCollection().remove(visitaCollectionNewVisita);
                        oldEventoagendaOfVisitaCollectionNewVisita = em.merge(oldEventoagendaOfVisitaCollectionNewVisita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = agenda.getId();
                if (findAgenda(id) == null) {
                    throw new NonexistentEntityException("The agenda with id " + id + " no longer exists.");
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
            Agenda agenda;
            try {
                agenda = em.getReference(Agenda.class, id);
                agenda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agenda with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = agenda.getCliente();
            if (cliente != null) {
                cliente.getAgendaCollection().remove(agenda);
                cliente = em.merge(cliente);
            }
            Empleado empleado = agenda.getEmpleado();
            if (empleado != null) {
                empleado.getAgendaCollection().remove(agenda);
                empleado = em.merge(empleado);
            }
            Propiedad propiedad = agenda.getPropiedad();
            if (propiedad != null) {
                propiedad.getAgendaCollection().remove(agenda);
                propiedad = em.merge(propiedad);
            }
            Collection<Visita> visitaCollection = agenda.getVisitaCollection();
            for (Visita visitaCollectionVisita : visitaCollection) {
                visitaCollectionVisita.setEventoagenda(null);
                visitaCollectionVisita = em.merge(visitaCollectionVisita);
            }
            em.remove(agenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agenda> findAgendaEntities() {
        return findAgendaEntities(true, -1, -1);
    }

    public List<Agenda> findAgendaEntities(int maxResults, int firstResult) {
        return findAgendaEntities(false, maxResults, firstResult);
    }

    private List<Agenda> findAgendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agenda.class));
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

    public Agenda findAgenda(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agenda> rt = cq.from(Agenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
