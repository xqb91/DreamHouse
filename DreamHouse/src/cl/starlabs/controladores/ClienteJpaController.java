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
import cl.starlabs.modelo.Arriendo;
import java.util.ArrayList;
import java.util.Collection;
import cl.starlabs.modelo.Historialbusqueda;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Cliente;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getArriendoCollection() == null) {
            cliente.setArriendoCollection(new ArrayList<Arriendo>());
        }
        if (cliente.getHistorialbusquedaCollection() == null) {
            cliente.setHistorialbusquedaCollection(new ArrayList<Historialbusqueda>());
        }
        if (cliente.getAgendaCollection() == null) {
            cliente.setAgendaCollection(new ArrayList<Agenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad = cliente.getCiudad();
            if (ciudad != null) {
                ciudad = em.getReference(ciudad.getClass(), ciudad.getIdciudad());
                cliente.setCiudad(ciudad);
            }
            Collection<Arriendo> attachedArriendoCollection = new ArrayList<Arriendo>();
            for (Arriendo arriendoCollectionArriendoToAttach : cliente.getArriendoCollection()) {
                arriendoCollectionArriendoToAttach = em.getReference(arriendoCollectionArriendoToAttach.getClass(), arriendoCollectionArriendoToAttach.getNumarriendo());
                attachedArriendoCollection.add(arriendoCollectionArriendoToAttach);
            }
            cliente.setArriendoCollection(attachedArriendoCollection);
            Collection<Historialbusqueda> attachedHistorialbusquedaCollection = new ArrayList<Historialbusqueda>();
            for (Historialbusqueda historialbusquedaCollectionHistorialbusquedaToAttach : cliente.getHistorialbusquedaCollection()) {
                historialbusquedaCollectionHistorialbusquedaToAttach = em.getReference(historialbusquedaCollectionHistorialbusquedaToAttach.getClass(), historialbusquedaCollectionHistorialbusquedaToAttach.getId());
                attachedHistorialbusquedaCollection.add(historialbusquedaCollectionHistorialbusquedaToAttach);
            }
            cliente.setHistorialbusquedaCollection(attachedHistorialbusquedaCollection);
            Collection<Agenda> attachedAgendaCollection = new ArrayList<Agenda>();
            for (Agenda agendaCollectionAgendaToAttach : cliente.getAgendaCollection()) {
                agendaCollectionAgendaToAttach = em.getReference(agendaCollectionAgendaToAttach.getClass(), agendaCollectionAgendaToAttach.getId());
                attachedAgendaCollection.add(agendaCollectionAgendaToAttach);
            }
            cliente.setAgendaCollection(attachedAgendaCollection);
            em.persist(cliente);
            if (ciudad != null) {
                ciudad.getClienteCollection().add(cliente);
                ciudad = em.merge(ciudad);
            }
            for (Arriendo arriendoCollectionArriendo : cliente.getArriendoCollection()) {
                Cliente oldNumclienteOfArriendoCollectionArriendo = arriendoCollectionArriendo.getNumcliente();
                arriendoCollectionArriendo.setNumcliente(cliente);
                arriendoCollectionArriendo = em.merge(arriendoCollectionArriendo);
                if (oldNumclienteOfArriendoCollectionArriendo != null) {
                    oldNumclienteOfArriendoCollectionArriendo.getArriendoCollection().remove(arriendoCollectionArriendo);
                    oldNumclienteOfArriendoCollectionArriendo = em.merge(oldNumclienteOfArriendoCollectionArriendo);
                }
            }
            for (Historialbusqueda historialbusquedaCollectionHistorialbusqueda : cliente.getHistorialbusquedaCollection()) {
                Cliente oldClienteOfHistorialbusquedaCollectionHistorialbusqueda = historialbusquedaCollectionHistorialbusqueda.getCliente();
                historialbusquedaCollectionHistorialbusqueda.setCliente(cliente);
                historialbusquedaCollectionHistorialbusqueda = em.merge(historialbusquedaCollectionHistorialbusqueda);
                if (oldClienteOfHistorialbusquedaCollectionHistorialbusqueda != null) {
                    oldClienteOfHistorialbusquedaCollectionHistorialbusqueda.getHistorialbusquedaCollection().remove(historialbusquedaCollectionHistorialbusqueda);
                    oldClienteOfHistorialbusquedaCollectionHistorialbusqueda = em.merge(oldClienteOfHistorialbusquedaCollectionHistorialbusqueda);
                }
            }
            for (Agenda agendaCollectionAgenda : cliente.getAgendaCollection()) {
                Cliente oldClienteOfAgendaCollectionAgenda = agendaCollectionAgenda.getCliente();
                agendaCollectionAgenda.setCliente(cliente);
                agendaCollectionAgenda = em.merge(agendaCollectionAgenda);
                if (oldClienteOfAgendaCollectionAgenda != null) {
                    oldClienteOfAgendaCollectionAgenda.getAgendaCollection().remove(agendaCollectionAgenda);
                    oldClienteOfAgendaCollectionAgenda = em.merge(oldClienteOfAgendaCollectionAgenda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getNumcliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getNumcliente());
            Ciudad ciudadOld = persistentCliente.getCiudad();
            Ciudad ciudadNew = cliente.getCiudad();
            Collection<Arriendo> arriendoCollectionOld = persistentCliente.getArriendoCollection();
            Collection<Arriendo> arriendoCollectionNew = cliente.getArriendoCollection();
            Collection<Historialbusqueda> historialbusquedaCollectionOld = persistentCliente.getHistorialbusquedaCollection();
            Collection<Historialbusqueda> historialbusquedaCollectionNew = cliente.getHistorialbusquedaCollection();
            Collection<Agenda> agendaCollectionOld = persistentCliente.getAgendaCollection();
            Collection<Agenda> agendaCollectionNew = cliente.getAgendaCollection();
            List<String> illegalOrphanMessages = null;
            for (Arriendo arriendoCollectionOldArriendo : arriendoCollectionOld) {
                if (!arriendoCollectionNew.contains(arriendoCollectionOldArriendo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Arriendo " + arriendoCollectionOldArriendo + " since its numcliente field is not nullable.");
                }
            }
            for (Historialbusqueda historialbusquedaCollectionOldHistorialbusqueda : historialbusquedaCollectionOld) {
                if (!historialbusquedaCollectionNew.contains(historialbusquedaCollectionOldHistorialbusqueda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialbusqueda " + historialbusquedaCollectionOldHistorialbusqueda + " since its cliente field is not nullable.");
                }
            }
            for (Agenda agendaCollectionOldAgenda : agendaCollectionOld) {
                if (!agendaCollectionNew.contains(agendaCollectionOldAgenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agenda " + agendaCollectionOldAgenda + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadNew != null) {
                ciudadNew = em.getReference(ciudadNew.getClass(), ciudadNew.getIdciudad());
                cliente.setCiudad(ciudadNew);
            }
            Collection<Arriendo> attachedArriendoCollectionNew = new ArrayList<Arriendo>();
            for (Arriendo arriendoCollectionNewArriendoToAttach : arriendoCollectionNew) {
                arriendoCollectionNewArriendoToAttach = em.getReference(arriendoCollectionNewArriendoToAttach.getClass(), arriendoCollectionNewArriendoToAttach.getNumarriendo());
                attachedArriendoCollectionNew.add(arriendoCollectionNewArriendoToAttach);
            }
            arriendoCollectionNew = attachedArriendoCollectionNew;
            cliente.setArriendoCollection(arriendoCollectionNew);
            Collection<Historialbusqueda> attachedHistorialbusquedaCollectionNew = new ArrayList<Historialbusqueda>();
            for (Historialbusqueda historialbusquedaCollectionNewHistorialbusquedaToAttach : historialbusquedaCollectionNew) {
                historialbusquedaCollectionNewHistorialbusquedaToAttach = em.getReference(historialbusquedaCollectionNewHistorialbusquedaToAttach.getClass(), historialbusquedaCollectionNewHistorialbusquedaToAttach.getId());
                attachedHistorialbusquedaCollectionNew.add(historialbusquedaCollectionNewHistorialbusquedaToAttach);
            }
            historialbusquedaCollectionNew = attachedHistorialbusquedaCollectionNew;
            cliente.setHistorialbusquedaCollection(historialbusquedaCollectionNew);
            Collection<Agenda> attachedAgendaCollectionNew = new ArrayList<Agenda>();
            for (Agenda agendaCollectionNewAgendaToAttach : agendaCollectionNew) {
                agendaCollectionNewAgendaToAttach = em.getReference(agendaCollectionNewAgendaToAttach.getClass(), agendaCollectionNewAgendaToAttach.getId());
                attachedAgendaCollectionNew.add(agendaCollectionNewAgendaToAttach);
            }
            agendaCollectionNew = attachedAgendaCollectionNew;
            cliente.setAgendaCollection(agendaCollectionNew);
            cliente = em.merge(cliente);
            if (ciudadOld != null && !ciudadOld.equals(ciudadNew)) {
                ciudadOld.getClienteCollection().remove(cliente);
                ciudadOld = em.merge(ciudadOld);
            }
            if (ciudadNew != null && !ciudadNew.equals(ciudadOld)) {
                ciudadNew.getClienteCollection().add(cliente);
                ciudadNew = em.merge(ciudadNew);
            }
            for (Arriendo arriendoCollectionNewArriendo : arriendoCollectionNew) {
                if (!arriendoCollectionOld.contains(arriendoCollectionNewArriendo)) {
                    Cliente oldNumclienteOfArriendoCollectionNewArriendo = arriendoCollectionNewArriendo.getNumcliente();
                    arriendoCollectionNewArriendo.setNumcliente(cliente);
                    arriendoCollectionNewArriendo = em.merge(arriendoCollectionNewArriendo);
                    if (oldNumclienteOfArriendoCollectionNewArriendo != null && !oldNumclienteOfArriendoCollectionNewArriendo.equals(cliente)) {
                        oldNumclienteOfArriendoCollectionNewArriendo.getArriendoCollection().remove(arriendoCollectionNewArriendo);
                        oldNumclienteOfArriendoCollectionNewArriendo = em.merge(oldNumclienteOfArriendoCollectionNewArriendo);
                    }
                }
            }
            for (Historialbusqueda historialbusquedaCollectionNewHistorialbusqueda : historialbusquedaCollectionNew) {
                if (!historialbusquedaCollectionOld.contains(historialbusquedaCollectionNewHistorialbusqueda)) {
                    Cliente oldClienteOfHistorialbusquedaCollectionNewHistorialbusqueda = historialbusquedaCollectionNewHistorialbusqueda.getCliente();
                    historialbusquedaCollectionNewHistorialbusqueda.setCliente(cliente);
                    historialbusquedaCollectionNewHistorialbusqueda = em.merge(historialbusquedaCollectionNewHistorialbusqueda);
                    if (oldClienteOfHistorialbusquedaCollectionNewHistorialbusqueda != null && !oldClienteOfHistorialbusquedaCollectionNewHistorialbusqueda.equals(cliente)) {
                        oldClienteOfHistorialbusquedaCollectionNewHistorialbusqueda.getHistorialbusquedaCollection().remove(historialbusquedaCollectionNewHistorialbusqueda);
                        oldClienteOfHistorialbusquedaCollectionNewHistorialbusqueda = em.merge(oldClienteOfHistorialbusquedaCollectionNewHistorialbusqueda);
                    }
                }
            }
            for (Agenda agendaCollectionNewAgenda : agendaCollectionNew) {
                if (!agendaCollectionOld.contains(agendaCollectionNewAgenda)) {
                    Cliente oldClienteOfAgendaCollectionNewAgenda = agendaCollectionNewAgenda.getCliente();
                    agendaCollectionNewAgenda.setCliente(cliente);
                    agendaCollectionNewAgenda = em.merge(agendaCollectionNewAgenda);
                    if (oldClienteOfAgendaCollectionNewAgenda != null && !oldClienteOfAgendaCollectionNewAgenda.equals(cliente)) {
                        oldClienteOfAgendaCollectionNewAgenda.getAgendaCollection().remove(agendaCollectionNewAgenda);
                        oldClienteOfAgendaCollectionNewAgenda = em.merge(oldClienteOfAgendaCollectionNewAgenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = cliente.getNumcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getNumcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Arriendo> arriendoCollectionOrphanCheck = cliente.getArriendoCollection();
            for (Arriendo arriendoCollectionOrphanCheckArriendo : arriendoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Arriendo " + arriendoCollectionOrphanCheckArriendo + " in its arriendoCollection field has a non-nullable numcliente field.");
            }
            Collection<Historialbusqueda> historialbusquedaCollectionOrphanCheck = cliente.getHistorialbusquedaCollection();
            for (Historialbusqueda historialbusquedaCollectionOrphanCheckHistorialbusqueda : historialbusquedaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Historialbusqueda " + historialbusquedaCollectionOrphanCheckHistorialbusqueda + " in its historialbusquedaCollection field has a non-nullable cliente field.");
            }
            Collection<Agenda> agendaCollectionOrphanCheck = cliente.getAgendaCollection();
            for (Agenda agendaCollectionOrphanCheckAgenda : agendaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Agenda " + agendaCollectionOrphanCheckAgenda + " in its agendaCollection field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudad = cliente.getCiudad();
            if (ciudad != null) {
                ciudad.getClienteCollection().remove(cliente);
                ciudad = em.merge(ciudad);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public List<Cliente> listar() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Cliente.findAll");
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Cliente> buscarClientePorRUT(String rut) {
        try {
            int rutNuevo = Integer.parseInt(rut);
            Query consulta = getEntityManager().createNamedQuery("Cliente.findByRut");
            consulta.setParameter("rut", rutNuevo);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Cliente buscarClientePorRUTExacto(String rut) {
        try {
            
            int rutNuevo = Integer.parseInt(rut.split("-")[0]);
            Query consulta = getEntityManager().createNamedQuery("Cliente.findByRut");
            consulta.setParameter("rut", rutNuevo);
            return (Cliente)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public BigDecimal ultimoCliente() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Cliente.ultimo");
            consulta.setMaxResults(1);
            return new BigDecimal((Integer.parseInt(((Cliente)consulta.getSingleResult()).getNumcliente().toString())+1));
        }catch(Exception e) {
            return null;
        }
    }
    
    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
