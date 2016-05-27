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
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Tipopropiedades;
import cl.starlabs.modelo.Arriendo;
import java.util.ArrayList;
import java.util.Collection;
import cl.starlabs.modelo.Historialbusqueda;
import cl.starlabs.modelo.Comision;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class PropiedadJpaController implements Serializable {

    public PropiedadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propiedad propiedad) throws PreexistingEntityException, Exception {
        if (propiedad.getArriendoCollection() == null) {
            propiedad.setArriendoCollection(new ArrayList<Arriendo>());
        }
        if (propiedad.getHistorialbusquedaCollection() == null) {
            propiedad.setHistorialbusquedaCollection(new ArrayList<Historialbusqueda>());
        }
        if (propiedad.getComisionCollection() == null) {
            propiedad.setComisionCollection(new ArrayList<Comision>());
        }
        if (propiedad.getAgendaCollection() == null) {
            propiedad.setAgendaCollection(new ArrayList<Agenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado numempleado = propiedad.getNumempleado();
            if (numempleado != null) {
                numempleado = em.getReference(numempleado.getClass(), numempleado.getId());
                propiedad.setNumempleado(numempleado);
            }
            Propietario numpropietario = propiedad.getNumpropietario();
            if (numpropietario != null) {
                numpropietario = em.getReference(numpropietario.getClass(), numpropietario.getNumpropietario());
                propiedad.setNumpropietario(numpropietario);
            }
            Tipopropiedades tipo = propiedad.getTipo();
            if (tipo != null) {
                tipo = em.getReference(tipo.getClass(), tipo.getId());
                propiedad.setTipo(tipo);
            }
            Collection<Arriendo> attachedArriendoCollection = new ArrayList<Arriendo>();
            for (Arriendo arriendoCollectionArriendoToAttach : propiedad.getArriendoCollection()) {
                arriendoCollectionArriendoToAttach = em.getReference(arriendoCollectionArriendoToAttach.getClass(), arriendoCollectionArriendoToAttach.getNumarriendo());
                attachedArriendoCollection.add(arriendoCollectionArriendoToAttach);
            }
            propiedad.setArriendoCollection(attachedArriendoCollection);
            Collection<Historialbusqueda> attachedHistorialbusquedaCollection = new ArrayList<Historialbusqueda>();
            for (Historialbusqueda historialbusquedaCollectionHistorialbusquedaToAttach : propiedad.getHistorialbusquedaCollection()) {
                historialbusquedaCollectionHistorialbusquedaToAttach = em.getReference(historialbusquedaCollectionHistorialbusquedaToAttach.getClass(), historialbusquedaCollectionHistorialbusquedaToAttach.getId());
                attachedHistorialbusquedaCollection.add(historialbusquedaCollectionHistorialbusquedaToAttach);
            }
            propiedad.setHistorialbusquedaCollection(attachedHistorialbusquedaCollection);
            Collection<Comision> attachedComisionCollection = new ArrayList<Comision>();
            for (Comision comisionCollectionComisionToAttach : propiedad.getComisionCollection()) {
                comisionCollectionComisionToAttach = em.getReference(comisionCollectionComisionToAttach.getClass(), comisionCollectionComisionToAttach.getId());
                attachedComisionCollection.add(comisionCollectionComisionToAttach);
            }
            propiedad.setComisionCollection(attachedComisionCollection);
            Collection<Agenda> attachedAgendaCollection = new ArrayList<Agenda>();
            for (Agenda agendaCollectionAgendaToAttach : propiedad.getAgendaCollection()) {
                agendaCollectionAgendaToAttach = em.getReference(agendaCollectionAgendaToAttach.getClass(), agendaCollectionAgendaToAttach.getId());
                attachedAgendaCollection.add(agendaCollectionAgendaToAttach);
            }
            propiedad.setAgendaCollection(attachedAgendaCollection);
            em.persist(propiedad);
            if (numempleado != null) {
                numempleado.getPropiedadCollection().add(propiedad);
                numempleado = em.merge(numempleado);
            }
            if (numpropietario != null) {
                numpropietario.getPropiedadCollection().add(propiedad);
                numpropietario = em.merge(numpropietario);
            }
            if (tipo != null) {
                tipo.getPropiedadCollection().add(propiedad);
                tipo = em.merge(tipo);
            }
            for (Arriendo arriendoCollectionArriendo : propiedad.getArriendoCollection()) {
                Propiedad oldNumpropiedadOfArriendoCollectionArriendo = arriendoCollectionArriendo.getNumpropiedad();
                arriendoCollectionArriendo.setNumpropiedad(propiedad);
                arriendoCollectionArriendo = em.merge(arriendoCollectionArriendo);
                if (oldNumpropiedadOfArriendoCollectionArriendo != null) {
                    oldNumpropiedadOfArriendoCollectionArriendo.getArriendoCollection().remove(arriendoCollectionArriendo);
                    oldNumpropiedadOfArriendoCollectionArriendo = em.merge(oldNumpropiedadOfArriendoCollectionArriendo);
                }
            }
            for (Historialbusqueda historialbusquedaCollectionHistorialbusqueda : propiedad.getHistorialbusquedaCollection()) {
                Propiedad oldPropiedadOfHistorialbusquedaCollectionHistorialbusqueda = historialbusquedaCollectionHistorialbusqueda.getPropiedad();
                historialbusquedaCollectionHistorialbusqueda.setPropiedad(propiedad);
                historialbusquedaCollectionHistorialbusqueda = em.merge(historialbusquedaCollectionHistorialbusqueda);
                if (oldPropiedadOfHistorialbusquedaCollectionHistorialbusqueda != null) {
                    oldPropiedadOfHistorialbusquedaCollectionHistorialbusqueda.getHistorialbusquedaCollection().remove(historialbusquedaCollectionHistorialbusqueda);
                    oldPropiedadOfHistorialbusquedaCollectionHistorialbusqueda = em.merge(oldPropiedadOfHistorialbusquedaCollectionHistorialbusqueda);
                }
            }
            for (Comision comisionCollectionComision : propiedad.getComisionCollection()) {
                Propiedad oldPropiedadOfComisionCollectionComision = comisionCollectionComision.getPropiedad();
                comisionCollectionComision.setPropiedad(propiedad);
                comisionCollectionComision = em.merge(comisionCollectionComision);
                if (oldPropiedadOfComisionCollectionComision != null) {
                    oldPropiedadOfComisionCollectionComision.getComisionCollection().remove(comisionCollectionComision);
                    oldPropiedadOfComisionCollectionComision = em.merge(oldPropiedadOfComisionCollectionComision);
                }
            }
            for (Agenda agendaCollectionAgenda : propiedad.getAgendaCollection()) {
                Propiedad oldPropiedadOfAgendaCollectionAgenda = agendaCollectionAgenda.getPropiedad();
                agendaCollectionAgenda.setPropiedad(propiedad);
                agendaCollectionAgenda = em.merge(agendaCollectionAgenda);
                if (oldPropiedadOfAgendaCollectionAgenda != null) {
                    oldPropiedadOfAgendaCollectionAgenda.getAgendaCollection().remove(agendaCollectionAgenda);
                    oldPropiedadOfAgendaCollectionAgenda = em.merge(oldPropiedadOfAgendaCollectionAgenda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropiedad(propiedad.getNumpropiedad()) != null) {
                throw new PreexistingEntityException("Propiedad " + propiedad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propiedad propiedad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propiedad persistentPropiedad = em.find(Propiedad.class, propiedad.getNumpropiedad());
            Empleado numempleadoOld = persistentPropiedad.getNumempleado();
            Empleado numempleadoNew = propiedad.getNumempleado();
            Propietario numpropietarioOld = persistentPropiedad.getNumpropietario();
            Propietario numpropietarioNew = propiedad.getNumpropietario();
            Tipopropiedades tipoOld = persistentPropiedad.getTipo();
            Tipopropiedades tipoNew = propiedad.getTipo();
            Collection<Arriendo> arriendoCollectionOld = persistentPropiedad.getArriendoCollection();
            Collection<Arriendo> arriendoCollectionNew = propiedad.getArriendoCollection();
            Collection<Historialbusqueda> historialbusquedaCollectionOld = persistentPropiedad.getHistorialbusquedaCollection();
            Collection<Historialbusqueda> historialbusquedaCollectionNew = propiedad.getHistorialbusquedaCollection();
            Collection<Comision> comisionCollectionOld = persistentPropiedad.getComisionCollection();
            Collection<Comision> comisionCollectionNew = propiedad.getComisionCollection();
            Collection<Agenda> agendaCollectionOld = persistentPropiedad.getAgendaCollection();
            Collection<Agenda> agendaCollectionNew = propiedad.getAgendaCollection();
            List<String> illegalOrphanMessages = null;
            for (Arriendo arriendoCollectionOldArriendo : arriendoCollectionOld) {
                if (!arriendoCollectionNew.contains(arriendoCollectionOldArriendo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Arriendo " + arriendoCollectionOldArriendo + " since its numpropiedad field is not nullable.");
                }
            }
            for (Historialbusqueda historialbusquedaCollectionOldHistorialbusqueda : historialbusquedaCollectionOld) {
                if (!historialbusquedaCollectionNew.contains(historialbusquedaCollectionOldHistorialbusqueda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialbusqueda " + historialbusquedaCollectionOldHistorialbusqueda + " since its propiedad field is not nullable.");
                }
            }
            for (Comision comisionCollectionOldComision : comisionCollectionOld) {
                if (!comisionCollectionNew.contains(comisionCollectionOldComision)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comision " + comisionCollectionOldComision + " since its propiedad field is not nullable.");
                }
            }
            for (Agenda agendaCollectionOldAgenda : agendaCollectionOld) {
                if (!agendaCollectionNew.contains(agendaCollectionOldAgenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agenda " + agendaCollectionOldAgenda + " since its propiedad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (numempleadoNew != null) {
                numempleadoNew = em.getReference(numempleadoNew.getClass(), numempleadoNew.getId());
                propiedad.setNumempleado(numempleadoNew);
            }
            if (numpropietarioNew != null) {
                numpropietarioNew = em.getReference(numpropietarioNew.getClass(), numpropietarioNew.getNumpropietario());
                propiedad.setNumpropietario(numpropietarioNew);
            }
            if (tipoNew != null) {
                tipoNew = em.getReference(tipoNew.getClass(), tipoNew.getId());
                propiedad.setTipo(tipoNew);
            }
            Collection<Arriendo> attachedArriendoCollectionNew = new ArrayList<Arriendo>();
            for (Arriendo arriendoCollectionNewArriendoToAttach : arriendoCollectionNew) {
                arriendoCollectionNewArriendoToAttach = em.getReference(arriendoCollectionNewArriendoToAttach.getClass(), arriendoCollectionNewArriendoToAttach.getNumarriendo());
                attachedArriendoCollectionNew.add(arriendoCollectionNewArriendoToAttach);
            }
            arriendoCollectionNew = attachedArriendoCollectionNew;
            propiedad.setArriendoCollection(arriendoCollectionNew);
            Collection<Historialbusqueda> attachedHistorialbusquedaCollectionNew = new ArrayList<Historialbusqueda>();
            for (Historialbusqueda historialbusquedaCollectionNewHistorialbusquedaToAttach : historialbusquedaCollectionNew) {
                historialbusquedaCollectionNewHistorialbusquedaToAttach = em.getReference(historialbusquedaCollectionNewHistorialbusquedaToAttach.getClass(), historialbusquedaCollectionNewHistorialbusquedaToAttach.getId());
                attachedHistorialbusquedaCollectionNew.add(historialbusquedaCollectionNewHistorialbusquedaToAttach);
            }
            historialbusquedaCollectionNew = attachedHistorialbusquedaCollectionNew;
            propiedad.setHistorialbusquedaCollection(historialbusquedaCollectionNew);
            Collection<Comision> attachedComisionCollectionNew = new ArrayList<Comision>();
            for (Comision comisionCollectionNewComisionToAttach : comisionCollectionNew) {
                comisionCollectionNewComisionToAttach = em.getReference(comisionCollectionNewComisionToAttach.getClass(), comisionCollectionNewComisionToAttach.getId());
                attachedComisionCollectionNew.add(comisionCollectionNewComisionToAttach);
            }
            comisionCollectionNew = attachedComisionCollectionNew;
            propiedad.setComisionCollection(comisionCollectionNew);
            Collection<Agenda> attachedAgendaCollectionNew = new ArrayList<Agenda>();
            for (Agenda agendaCollectionNewAgendaToAttach : agendaCollectionNew) {
                agendaCollectionNewAgendaToAttach = em.getReference(agendaCollectionNewAgendaToAttach.getClass(), agendaCollectionNewAgendaToAttach.getId());
                attachedAgendaCollectionNew.add(agendaCollectionNewAgendaToAttach);
            }
            agendaCollectionNew = attachedAgendaCollectionNew;
            propiedad.setAgendaCollection(agendaCollectionNew);
            propiedad = em.merge(propiedad);
            if (numempleadoOld != null && !numempleadoOld.equals(numempleadoNew)) {
                numempleadoOld.getPropiedadCollection().remove(propiedad);
                numempleadoOld = em.merge(numempleadoOld);
            }
            if (numempleadoNew != null && !numempleadoNew.equals(numempleadoOld)) {
                numempleadoNew.getPropiedadCollection().add(propiedad);
                numempleadoNew = em.merge(numempleadoNew);
            }
            if (numpropietarioOld != null && !numpropietarioOld.equals(numpropietarioNew)) {
                numpropietarioOld.getPropiedadCollection().remove(propiedad);
                numpropietarioOld = em.merge(numpropietarioOld);
            }
            if (numpropietarioNew != null && !numpropietarioNew.equals(numpropietarioOld)) {
                numpropietarioNew.getPropiedadCollection().add(propiedad);
                numpropietarioNew = em.merge(numpropietarioNew);
            }
            if (tipoOld != null && !tipoOld.equals(tipoNew)) {
                tipoOld.getPropiedadCollection().remove(propiedad);
                tipoOld = em.merge(tipoOld);
            }
            if (tipoNew != null && !tipoNew.equals(tipoOld)) {
                tipoNew.getPropiedadCollection().add(propiedad);
                tipoNew = em.merge(tipoNew);
            }
            for (Arriendo arriendoCollectionNewArriendo : arriendoCollectionNew) {
                if (!arriendoCollectionOld.contains(arriendoCollectionNewArriendo)) {
                    Propiedad oldNumpropiedadOfArriendoCollectionNewArriendo = arriendoCollectionNewArriendo.getNumpropiedad();
                    arriendoCollectionNewArriendo.setNumpropiedad(propiedad);
                    arriendoCollectionNewArriendo = em.merge(arriendoCollectionNewArriendo);
                    if (oldNumpropiedadOfArriendoCollectionNewArriendo != null && !oldNumpropiedadOfArriendoCollectionNewArriendo.equals(propiedad)) {
                        oldNumpropiedadOfArriendoCollectionNewArriendo.getArriendoCollection().remove(arriendoCollectionNewArriendo);
                        oldNumpropiedadOfArriendoCollectionNewArriendo = em.merge(oldNumpropiedadOfArriendoCollectionNewArriendo);
                    }
                }
            }
            for (Historialbusqueda historialbusquedaCollectionNewHistorialbusqueda : historialbusquedaCollectionNew) {
                if (!historialbusquedaCollectionOld.contains(historialbusquedaCollectionNewHistorialbusqueda)) {
                    Propiedad oldPropiedadOfHistorialbusquedaCollectionNewHistorialbusqueda = historialbusquedaCollectionNewHistorialbusqueda.getPropiedad();
                    historialbusquedaCollectionNewHistorialbusqueda.setPropiedad(propiedad);
                    historialbusquedaCollectionNewHistorialbusqueda = em.merge(historialbusquedaCollectionNewHistorialbusqueda);
                    if (oldPropiedadOfHistorialbusquedaCollectionNewHistorialbusqueda != null && !oldPropiedadOfHistorialbusquedaCollectionNewHistorialbusqueda.equals(propiedad)) {
                        oldPropiedadOfHistorialbusquedaCollectionNewHistorialbusqueda.getHistorialbusquedaCollection().remove(historialbusquedaCollectionNewHistorialbusqueda);
                        oldPropiedadOfHistorialbusquedaCollectionNewHistorialbusqueda = em.merge(oldPropiedadOfHistorialbusquedaCollectionNewHistorialbusqueda);
                    }
                }
            }
            for (Comision comisionCollectionNewComision : comisionCollectionNew) {
                if (!comisionCollectionOld.contains(comisionCollectionNewComision)) {
                    Propiedad oldPropiedadOfComisionCollectionNewComision = comisionCollectionNewComision.getPropiedad();
                    comisionCollectionNewComision.setPropiedad(propiedad);
                    comisionCollectionNewComision = em.merge(comisionCollectionNewComision);
                    if (oldPropiedadOfComisionCollectionNewComision != null && !oldPropiedadOfComisionCollectionNewComision.equals(propiedad)) {
                        oldPropiedadOfComisionCollectionNewComision.getComisionCollection().remove(comisionCollectionNewComision);
                        oldPropiedadOfComisionCollectionNewComision = em.merge(oldPropiedadOfComisionCollectionNewComision);
                    }
                }
            }
            for (Agenda agendaCollectionNewAgenda : agendaCollectionNew) {
                if (!agendaCollectionOld.contains(agendaCollectionNewAgenda)) {
                    Propiedad oldPropiedadOfAgendaCollectionNewAgenda = agendaCollectionNewAgenda.getPropiedad();
                    agendaCollectionNewAgenda.setPropiedad(propiedad);
                    agendaCollectionNewAgenda = em.merge(agendaCollectionNewAgenda);
                    if (oldPropiedadOfAgendaCollectionNewAgenda != null && !oldPropiedadOfAgendaCollectionNewAgenda.equals(propiedad)) {
                        oldPropiedadOfAgendaCollectionNewAgenda.getAgendaCollection().remove(agendaCollectionNewAgenda);
                        oldPropiedadOfAgendaCollectionNewAgenda = em.merge(oldPropiedadOfAgendaCollectionNewAgenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = propiedad.getNumpropiedad();
                if (findPropiedad(id) == null) {
                    throw new NonexistentEntityException("The propiedad with id " + id + " no longer exists.");
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
            Propiedad propiedad;
            try {
                propiedad = em.getReference(Propiedad.class, id);
                propiedad.getNumpropiedad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propiedad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Arriendo> arriendoCollectionOrphanCheck = propiedad.getArriendoCollection();
            for (Arriendo arriendoCollectionOrphanCheckArriendo : arriendoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Arriendo " + arriendoCollectionOrphanCheckArriendo + " in its arriendoCollection field has a non-nullable numpropiedad field.");
            }
            Collection<Historialbusqueda> historialbusquedaCollectionOrphanCheck = propiedad.getHistorialbusquedaCollection();
            for (Historialbusqueda historialbusquedaCollectionOrphanCheckHistorialbusqueda : historialbusquedaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Historialbusqueda " + historialbusquedaCollectionOrphanCheckHistorialbusqueda + " in its historialbusquedaCollection field has a non-nullable propiedad field.");
            }
            Collection<Comision> comisionCollectionOrphanCheck = propiedad.getComisionCollection();
            for (Comision comisionCollectionOrphanCheckComision : comisionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Comision " + comisionCollectionOrphanCheckComision + " in its comisionCollection field has a non-nullable propiedad field.");
            }
            Collection<Agenda> agendaCollectionOrphanCheck = propiedad.getAgendaCollection();
            for (Agenda agendaCollectionOrphanCheckAgenda : agendaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propiedad (" + propiedad + ") cannot be destroyed since the Agenda " + agendaCollectionOrphanCheckAgenda + " in its agendaCollection field has a non-nullable propiedad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleado numempleado = propiedad.getNumempleado();
            if (numempleado != null) {
                numempleado.getPropiedadCollection().remove(propiedad);
                numempleado = em.merge(numempleado);
            }
            Propietario numpropietario = propiedad.getNumpropietario();
            if (numpropietario != null) {
                numpropietario.getPropiedadCollection().remove(propiedad);
                numpropietario = em.merge(numpropietario);
            }
            Tipopropiedades tipo = propiedad.getTipo();
            if (tipo != null) {
                tipo.getPropiedadCollection().remove(propiedad);
                tipo = em.merge(tipo);
            }
            em.remove(propiedad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propiedad> findPropiedadEntities() {
        return findPropiedadEntities(true, -1, -1);
    }

    public List<Propiedad> findPropiedadEntities(int maxResults, int firstResult) {
        return findPropiedadEntities(false, maxResults, firstResult);
    }

    private List<Propiedad> findPropiedadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propiedad.class));
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

    public Propiedad findPropiedad(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propiedad.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropiedadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propiedad> rt = cq.from(Propiedad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Propiedad> listarPorCiudad(BigInteger ciudad) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Propiedad.findByCiudad");
            consulta.setParameter("ciudad", ciudad);
            return consulta.getResultList();
        }catch(Exception e) {
            return null;
        }
    }
    
    public Propiedad buscarPropiedadPorNumero(BigInteger numero) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Propiedad.findByNumpropiedad");
            consulta.setParameter("numpropiedad", numero);
            consulta.setMaxResults(1);
            return (Propiedad)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Propiedad> listar() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Propiedad.findAll");
            return consulta.getResultList();
        }catch(Exception e)
        {
            return null;
        }
    }
    
    public List<Propiedad> listarPropiedadPorRutPropietario(String rut) {
        try {
            Propietario pro = new PropietarioJpaController(emf).buscarPropietarioPorRut(rut.split("-")[0]);
            if(pro == null)
            {
                return null;
            }else{
                Query consulta = getEntityManager().createNamedQuery("Propiedad.buscarPorPropietario");
                consulta.setParameter("propietario", pro);
                return consulta.getResultList();
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    public BigDecimal ultimoId()
    {
        try {
            Query consulta = getEntityManager().createNamedQuery("Propiedad.ultimo");
            consulta.setMaxResults(1);
            int numero = Integer.parseInt(((Propiedad)consulta.getSingleResult()).getNumpropiedad().toString());
            numero++;
            return new BigDecimal(numero);
        } catch (Exception e) {
            return null;
        }
    }
    
}
