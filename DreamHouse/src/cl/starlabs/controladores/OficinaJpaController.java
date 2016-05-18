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
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Oficina;
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
public class OficinaJpaController implements Serializable {

    public OficinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Oficina oficina) throws PreexistingEntityException, Exception {
        if (oficina.getEmpleadoCollection() == null) {
            oficina.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudad = oficina.getCiudad();
            if (ciudad != null) {
                ciudad = em.getReference(ciudad.getClass(), ciudad.getIdciudad());
                oficina.setCiudad(ciudad);
            }
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : oficina.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getId());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            oficina.setEmpleadoCollection(attachedEmpleadoCollection);
            em.persist(oficina);
            if (ciudad != null) {
                ciudad.getOficinaCollection().add(oficina);
                ciudad = em.merge(ciudad);
            }
            for (Empleado empleadoCollectionEmpleado : oficina.getEmpleadoCollection()) {
                Oficina oldNumoficinaOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getNumoficina();
                empleadoCollectionEmpleado.setNumoficina(oficina);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldNumoficinaOfEmpleadoCollectionEmpleado != null) {
                    oldNumoficinaOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldNumoficinaOfEmpleadoCollectionEmpleado = em.merge(oldNumoficinaOfEmpleadoCollectionEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOficina(oficina.getId()) != null) {
                throw new PreexistingEntityException("Oficina " + oficina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Oficina oficina) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficina persistentOficina = em.find(Oficina.class, oficina.getId());
            Ciudad ciudadOld = persistentOficina.getCiudad();
            Ciudad ciudadNew = oficina.getCiudad();
            Collection<Empleado> empleadoCollectionOld = persistentOficina.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = oficina.getEmpleadoCollection();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoCollectionOldEmpleado + " since its numoficina field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadNew != null) {
                ciudadNew = em.getReference(ciudadNew.getClass(), ciudadNew.getIdciudad());
                oficina.setCiudad(ciudadNew);
            }
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getId());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            oficina.setEmpleadoCollection(empleadoCollectionNew);
            oficina = em.merge(oficina);
            if (ciudadOld != null && !ciudadOld.equals(ciudadNew)) {
                ciudadOld.getOficinaCollection().remove(oficina);
                ciudadOld = em.merge(ciudadOld);
            }
            if (ciudadNew != null && !ciudadNew.equals(ciudadOld)) {
                ciudadNew.getOficinaCollection().add(oficina);
                ciudadNew = em.merge(ciudadNew);
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    Oficina oldNumoficinaOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getNumoficina();
                    empleadoCollectionNewEmpleado.setNumoficina(oficina);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldNumoficinaOfEmpleadoCollectionNewEmpleado != null && !oldNumoficinaOfEmpleadoCollectionNewEmpleado.equals(oficina)) {
                        oldNumoficinaOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldNumoficinaOfEmpleadoCollectionNewEmpleado = em.merge(oldNumoficinaOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = oficina.getId();
                if (findOficina(id) == null) {
                    throw new NonexistentEntityException("The oficina with id " + id + " no longer exists.");
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
            Oficina oficina;
            try {
                oficina = em.getReference(Oficina.class, id);
                oficina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oficina with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Empleado> empleadoCollectionOrphanCheck = oficina.getEmpleadoCollection();
            for (Empleado empleadoCollectionOrphanCheckEmpleado : empleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Oficina (" + oficina + ") cannot be destroyed since the Empleado " + empleadoCollectionOrphanCheckEmpleado + " in its empleadoCollection field has a non-nullable numoficina field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudad = oficina.getCiudad();
            if (ciudad != null) {
                ciudad.getOficinaCollection().remove(oficina);
                ciudad = em.merge(ciudad);
            }
            em.remove(oficina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Oficina> findOficinaEntities() {
        return findOficinaEntities(true, -1, -1);
    }

    public List<Oficina> findOficinaEntities(int maxResults, int firstResult) {
        return findOficinaEntities(false, maxResults, firstResult);
    }

    private List<Oficina> findOficinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Oficina.class));
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

    public Oficina findOficina(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Oficina.class, id);
        } finally {
            em.close();
        }
    }

    public int getOficinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Oficina> rt = cq.from(Oficina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Oficina> listar() 
    {
        try {
            Query consulta = getEntityManager().createNamedQuery("Oficina.findAll");
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
