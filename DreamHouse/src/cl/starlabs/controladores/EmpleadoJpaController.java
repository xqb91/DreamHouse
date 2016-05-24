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
import cl.starlabs.modelo.Totpropempleado;
import cl.starlabs.modelo.Cargo;
import cl.starlabs.modelo.Oficina;
import cl.starlabs.modelo.Propiedad;
import java.util.ArrayList;
import java.util.Collection;
import cl.starlabs.modelo.Comision;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Empleado;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        if (empleado.getPropiedadCollection() == null) {
            empleado.setPropiedadCollection(new ArrayList<Propiedad>());
        }
        if (empleado.getComisionCollection() == null) {
            empleado.setComisionCollection(new ArrayList<Comision>());
        }
        if (empleado.getAgendaCollection() == null) {
            empleado.setAgendaCollection(new ArrayList<Agenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Totpropempleado totpropempleado = empleado.getTotpropempleado();
            if (totpropempleado != null) {
                totpropempleado = em.getReference(totpropempleado.getClass(), totpropempleado.getNumempleado());
                empleado.setTotpropempleado(totpropempleado);
            }
            Cargo cargo = empleado.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getId());
                empleado.setCargo(cargo);
            }
            Oficina numoficina = empleado.getNumoficina();
            if (numoficina != null) {
                numoficina = em.getReference(numoficina.getClass(), numoficina.getId());
                empleado.setNumoficina(numoficina);
            }
            Collection<Propiedad> attachedPropiedadCollection = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionPropiedadToAttach : empleado.getPropiedadCollection()) {
                propiedadCollectionPropiedadToAttach = em.getReference(propiedadCollectionPropiedadToAttach.getClass(), propiedadCollectionPropiedadToAttach.getNumpropiedad());
                attachedPropiedadCollection.add(propiedadCollectionPropiedadToAttach);
            }
            empleado.setPropiedadCollection(attachedPropiedadCollection);
            Collection<Comision> attachedComisionCollection = new ArrayList<Comision>();
            for (Comision comisionCollectionComisionToAttach : empleado.getComisionCollection()) {
                comisionCollectionComisionToAttach = em.getReference(comisionCollectionComisionToAttach.getClass(), comisionCollectionComisionToAttach.getId());
                attachedComisionCollection.add(comisionCollectionComisionToAttach);
            }
            empleado.setComisionCollection(attachedComisionCollection);
            Collection<Agenda> attachedAgendaCollection = new ArrayList<Agenda>();
            for (Agenda agendaCollectionAgendaToAttach : empleado.getAgendaCollection()) {
                agendaCollectionAgendaToAttach = em.getReference(agendaCollectionAgendaToAttach.getClass(), agendaCollectionAgendaToAttach.getId());
                attachedAgendaCollection.add(agendaCollectionAgendaToAttach);
            }
            empleado.setAgendaCollection(attachedAgendaCollection);
            em.persist(empleado);
            if (totpropempleado != null) {
                Empleado oldEmpleadoOfTotpropempleado = totpropempleado.getEmpleado();
                if (oldEmpleadoOfTotpropempleado != null) {
                    oldEmpleadoOfTotpropempleado.setTotpropempleado(null);
                    oldEmpleadoOfTotpropempleado = em.merge(oldEmpleadoOfTotpropempleado);
                }
                totpropempleado.setEmpleado(empleado);
                totpropempleado = em.merge(totpropempleado);
            }
            if (cargo != null) {
                cargo.getEmpleadoCollection().add(empleado);
                cargo = em.merge(cargo);
            }
            if (numoficina != null) {
                numoficina.getEmpleadoCollection().add(empleado);
                numoficina = em.merge(numoficina);
            }
            for (Propiedad propiedadCollectionPropiedad : empleado.getPropiedadCollection()) {
                Empleado oldNumempleadoOfPropiedadCollectionPropiedad = propiedadCollectionPropiedad.getNumempleado();
                propiedadCollectionPropiedad.setNumempleado(empleado);
                propiedadCollectionPropiedad = em.merge(propiedadCollectionPropiedad);
                if (oldNumempleadoOfPropiedadCollectionPropiedad != null) {
                    oldNumempleadoOfPropiedadCollectionPropiedad.getPropiedadCollection().remove(propiedadCollectionPropiedad);
                    oldNumempleadoOfPropiedadCollectionPropiedad = em.merge(oldNumempleadoOfPropiedadCollectionPropiedad);
                }
            }
            for (Comision comisionCollectionComision : empleado.getComisionCollection()) {
                Empleado oldEmpleadoOfComisionCollectionComision = comisionCollectionComision.getEmpleado();
                comisionCollectionComision.setEmpleado(empleado);
                comisionCollectionComision = em.merge(comisionCollectionComision);
                if (oldEmpleadoOfComisionCollectionComision != null) {
                    oldEmpleadoOfComisionCollectionComision.getComisionCollection().remove(comisionCollectionComision);
                    oldEmpleadoOfComisionCollectionComision = em.merge(oldEmpleadoOfComisionCollectionComision);
                }
            }
            for (Agenda agendaCollectionAgenda : empleado.getAgendaCollection()) {
                Empleado oldEmpleadoOfAgendaCollectionAgenda = agendaCollectionAgenda.getEmpleado();
                agendaCollectionAgenda.setEmpleado(empleado);
                agendaCollectionAgenda = em.merge(agendaCollectionAgenda);
                if (oldEmpleadoOfAgendaCollectionAgenda != null) {
                    oldEmpleadoOfAgendaCollectionAgenda.getAgendaCollection().remove(agendaCollectionAgenda);
                    oldEmpleadoOfAgendaCollectionAgenda = em.merge(oldEmpleadoOfAgendaCollectionAgenda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getId()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            Totpropempleado totpropempleadoOld = persistentEmpleado.getTotpropempleado();
            Totpropempleado totpropempleadoNew = empleado.getTotpropempleado();
            Cargo cargoOld = persistentEmpleado.getCargo();
            Cargo cargoNew = empleado.getCargo();
            Oficina numoficinaOld = persistentEmpleado.getNumoficina();
            Oficina numoficinaNew = empleado.getNumoficina();
            Collection<Propiedad> propiedadCollectionOld = persistentEmpleado.getPropiedadCollection();
            Collection<Propiedad> propiedadCollectionNew = empleado.getPropiedadCollection();
            Collection<Comision> comisionCollectionOld = persistentEmpleado.getComisionCollection();
            Collection<Comision> comisionCollectionNew = empleado.getComisionCollection();
            Collection<Agenda> agendaCollectionOld = persistentEmpleado.getAgendaCollection();
            Collection<Agenda> agendaCollectionNew = empleado.getAgendaCollection();
            List<String> illegalOrphanMessages = null;
            if (totpropempleadoOld != null && !totpropempleadoOld.equals(totpropempleadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Totpropempleado " + totpropempleadoOld + " since its empleado field is not nullable.");
            }
            for (Propiedad propiedadCollectionOldPropiedad : propiedadCollectionOld) {
                if (!propiedadCollectionNew.contains(propiedadCollectionOldPropiedad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propiedad " + propiedadCollectionOldPropiedad + " since its numempleado field is not nullable.");
                }
            }
            for (Comision comisionCollectionOldComision : comisionCollectionOld) {
                if (!comisionCollectionNew.contains(comisionCollectionOldComision)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comision " + comisionCollectionOldComision + " since its empleado field is not nullable.");
                }
            }
            for (Agenda agendaCollectionOldAgenda : agendaCollectionOld) {
                if (!agendaCollectionNew.contains(agendaCollectionOldAgenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agenda " + agendaCollectionOldAgenda + " since its empleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (totpropempleadoNew != null) {
                totpropempleadoNew = em.getReference(totpropempleadoNew.getClass(), totpropempleadoNew.getNumempleado());
                empleado.setTotpropempleado(totpropempleadoNew);
            }
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getId());
                empleado.setCargo(cargoNew);
            }
            if (numoficinaNew != null) {
                numoficinaNew = em.getReference(numoficinaNew.getClass(), numoficinaNew.getId());
                empleado.setNumoficina(numoficinaNew);
            }
            Collection<Propiedad> attachedPropiedadCollectionNew = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionNewPropiedadToAttach : propiedadCollectionNew) {
                propiedadCollectionNewPropiedadToAttach = em.getReference(propiedadCollectionNewPropiedadToAttach.getClass(), propiedadCollectionNewPropiedadToAttach.getNumpropiedad());
                attachedPropiedadCollectionNew.add(propiedadCollectionNewPropiedadToAttach);
            }
            propiedadCollectionNew = attachedPropiedadCollectionNew;
            empleado.setPropiedadCollection(propiedadCollectionNew);
            Collection<Comision> attachedComisionCollectionNew = new ArrayList<Comision>();
            for (Comision comisionCollectionNewComisionToAttach : comisionCollectionNew) {
                comisionCollectionNewComisionToAttach = em.getReference(comisionCollectionNewComisionToAttach.getClass(), comisionCollectionNewComisionToAttach.getId());
                attachedComisionCollectionNew.add(comisionCollectionNewComisionToAttach);
            }
            comisionCollectionNew = attachedComisionCollectionNew;
            empleado.setComisionCollection(comisionCollectionNew);
            Collection<Agenda> attachedAgendaCollectionNew = new ArrayList<Agenda>();
            for (Agenda agendaCollectionNewAgendaToAttach : agendaCollectionNew) {
                agendaCollectionNewAgendaToAttach = em.getReference(agendaCollectionNewAgendaToAttach.getClass(), agendaCollectionNewAgendaToAttach.getId());
                attachedAgendaCollectionNew.add(agendaCollectionNewAgendaToAttach);
            }
            agendaCollectionNew = attachedAgendaCollectionNew;
            empleado.setAgendaCollection(agendaCollectionNew);
            empleado = em.merge(empleado);
            if (totpropempleadoNew != null && !totpropempleadoNew.equals(totpropempleadoOld)) {
                Empleado oldEmpleadoOfTotpropempleado = totpropempleadoNew.getEmpleado();
                if (oldEmpleadoOfTotpropempleado != null) {
                    oldEmpleadoOfTotpropempleado.setTotpropempleado(null);
                    oldEmpleadoOfTotpropempleado = em.merge(oldEmpleadoOfTotpropempleado);
                }
                totpropempleadoNew.setEmpleado(empleado);
                totpropempleadoNew = em.merge(totpropempleadoNew);
            }
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getEmpleadoCollection().remove(empleado);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getEmpleadoCollection().add(empleado);
                cargoNew = em.merge(cargoNew);
            }
            if (numoficinaOld != null && !numoficinaOld.equals(numoficinaNew)) {
                numoficinaOld.getEmpleadoCollection().remove(empleado);
                numoficinaOld = em.merge(numoficinaOld);
            }
            if (numoficinaNew != null && !numoficinaNew.equals(numoficinaOld)) {
                numoficinaNew.getEmpleadoCollection().add(empleado);
                numoficinaNew = em.merge(numoficinaNew);
            }
            for (Propiedad propiedadCollectionNewPropiedad : propiedadCollectionNew) {
                if (!propiedadCollectionOld.contains(propiedadCollectionNewPropiedad)) {
                    Empleado oldNumempleadoOfPropiedadCollectionNewPropiedad = propiedadCollectionNewPropiedad.getNumempleado();
                    propiedadCollectionNewPropiedad.setNumempleado(empleado);
                    propiedadCollectionNewPropiedad = em.merge(propiedadCollectionNewPropiedad);
                    if (oldNumempleadoOfPropiedadCollectionNewPropiedad != null && !oldNumempleadoOfPropiedadCollectionNewPropiedad.equals(empleado)) {
                        oldNumempleadoOfPropiedadCollectionNewPropiedad.getPropiedadCollection().remove(propiedadCollectionNewPropiedad);
                        oldNumempleadoOfPropiedadCollectionNewPropiedad = em.merge(oldNumempleadoOfPropiedadCollectionNewPropiedad);
                    }
                }
            }
            for (Comision comisionCollectionNewComision : comisionCollectionNew) {
                if (!comisionCollectionOld.contains(comisionCollectionNewComision)) {
                    Empleado oldEmpleadoOfComisionCollectionNewComision = comisionCollectionNewComision.getEmpleado();
                    comisionCollectionNewComision.setEmpleado(empleado);
                    comisionCollectionNewComision = em.merge(comisionCollectionNewComision);
                    if (oldEmpleadoOfComisionCollectionNewComision != null && !oldEmpleadoOfComisionCollectionNewComision.equals(empleado)) {
                        oldEmpleadoOfComisionCollectionNewComision.getComisionCollection().remove(comisionCollectionNewComision);
                        oldEmpleadoOfComisionCollectionNewComision = em.merge(oldEmpleadoOfComisionCollectionNewComision);
                    }
                }
            }
            for (Agenda agendaCollectionNewAgenda : agendaCollectionNew) {
                if (!agendaCollectionOld.contains(agendaCollectionNewAgenda)) {
                    Empleado oldEmpleadoOfAgendaCollectionNewAgenda = agendaCollectionNewAgenda.getEmpleado();
                    agendaCollectionNewAgenda.setEmpleado(empleado);
                    agendaCollectionNewAgenda = em.merge(agendaCollectionNewAgenda);
                    if (oldEmpleadoOfAgendaCollectionNewAgenda != null && !oldEmpleadoOfAgendaCollectionNewAgenda.equals(empleado)) {
                        oldEmpleadoOfAgendaCollectionNewAgenda.getAgendaCollection().remove(agendaCollectionNewAgenda);
                        oldEmpleadoOfAgendaCollectionNewAgenda = em.merge(oldEmpleadoOfAgendaCollectionNewAgenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Totpropempleado totpropempleadoOrphanCheck = empleado.getTotpropempleado();
            if (totpropempleadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Totpropempleado " + totpropempleadoOrphanCheck + " in its totpropempleado field has a non-nullable empleado field.");
            }
            Collection<Propiedad> propiedadCollectionOrphanCheck = empleado.getPropiedadCollection();
            for (Propiedad propiedadCollectionOrphanCheckPropiedad : propiedadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Propiedad " + propiedadCollectionOrphanCheckPropiedad + " in its propiedadCollection field has a non-nullable numempleado field.");
            }
            Collection<Comision> comisionCollectionOrphanCheck = empleado.getComisionCollection();
            for (Comision comisionCollectionOrphanCheckComision : comisionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Comision " + comisionCollectionOrphanCheckComision + " in its comisionCollection field has a non-nullable empleado field.");
            }
            Collection<Agenda> agendaCollectionOrphanCheck = empleado.getAgendaCollection();
            for (Agenda agendaCollectionOrphanCheckAgenda : agendaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Agenda " + agendaCollectionOrphanCheckAgenda + " in its agendaCollection field has a non-nullable empleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cargo cargo = empleado.getCargo();
            if (cargo != null) {
                cargo.getEmpleadoCollection().remove(empleado);
                cargo = em.merge(cargo);
            }
            Oficina numoficina = empleado.getNumoficina();
            if (numoficina != null) {
                numoficina.getEmpleadoCollection().remove(empleado);
                numoficina = em.merge(numoficina);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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
    
    public Empleado buscarEmpleado(String numero) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Empleado.findByNumempleado");
            consulta.setParameter("numempleado", Integer.parseInt(numero));
            return (Empleado)consulta.getSingleResult();
        }catch(Exception e)
        {
            return null;
        }
    }
    
    public Empleado findEmpleado(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public BigDecimal buscarUltimo() {
        try {
            Query consulta = this.getEntityManager().createNamedQuery("Empleado.buscarUltimo");
            consulta.setFirstResult(0);
            BigDecimal n = ((Empleado)consulta.getSingleResult()).getId();
            Integer n2 = Integer.parseInt(n.toString());
            n2++;
            return new BigDecimal(n2+"");
        } catch (Exception e) {
            return new BigDecimal("1");
        }
    }
}
