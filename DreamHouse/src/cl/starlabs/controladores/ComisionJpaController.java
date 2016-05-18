/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Comision;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor
 */
public class ComisionJpaController implements Serializable {

    public ComisionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comision comision) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = comision.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId());
                comision.setEmpleado(empleado);
            }
            Propiedad propiedad = comision.getPropiedad();
            if (propiedad != null) {
                propiedad = em.getReference(propiedad.getClass(), propiedad.getNumpropiedad());
                comision.setPropiedad(propiedad);
            }
            em.persist(comision);
            if (empleado != null) {
                empleado.getComisionCollection().add(comision);
                empleado = em.merge(empleado);
            }
            if (propiedad != null) {
                propiedad.getComisionCollection().add(comision);
                propiedad = em.merge(propiedad);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComision(comision.getId()) != null) {
                throw new PreexistingEntityException("Comision " + comision + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comision comision) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comision persistentComision = em.find(Comision.class, comision.getId());
            Empleado empleadoOld = persistentComision.getEmpleado();
            Empleado empleadoNew = comision.getEmpleado();
            Propiedad propiedadOld = persistentComision.getPropiedad();
            Propiedad propiedadNew = comision.getPropiedad();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId());
                comision.setEmpleado(empleadoNew);
            }
            if (propiedadNew != null) {
                propiedadNew = em.getReference(propiedadNew.getClass(), propiedadNew.getNumpropiedad());
                comision.setPropiedad(propiedadNew);
            }
            comision = em.merge(comision);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getComisionCollection().remove(comision);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getComisionCollection().add(comision);
                empleadoNew = em.merge(empleadoNew);
            }
            if (propiedadOld != null && !propiedadOld.equals(propiedadNew)) {
                propiedadOld.getComisionCollection().remove(comision);
                propiedadOld = em.merge(propiedadOld);
            }
            if (propiedadNew != null && !propiedadNew.equals(propiedadOld)) {
                propiedadNew.getComisionCollection().add(comision);
                propiedadNew = em.merge(propiedadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = comision.getId();
                if (findComision(id) == null) {
                    throw new NonexistentEntityException("The comision with id " + id + " no longer exists.");
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
            Comision comision;
            try {
                comision = em.getReference(Comision.class, id);
                comision.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comision with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = comision.getEmpleado();
            if (empleado != null) {
                empleado.getComisionCollection().remove(comision);
                empleado = em.merge(empleado);
            }
            Propiedad propiedad = comision.getPropiedad();
            if (propiedad != null) {
                propiedad.getComisionCollection().remove(comision);
                propiedad = em.merge(propiedad);
            }
            em.remove(comision);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comision> findComisionEntities() {
        return findComisionEntities(true, -1, -1);
    }

    public List<Comision> findComisionEntities(int maxResults, int firstResult) {
        return findComisionEntities(false, maxResults, firstResult);
    }

    private List<Comision> findComisionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comision.class));
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

    public Comision findComision(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comision.class, id);
        } finally {
            em.close();
        }
    }

    public int getComisionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comision> rt = cq.from(Comision.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public int totalComisionEmpleado(BigDecimal empleado) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Comision.comisionPorEmpleadoYMes");
            //Generando codigo para determinar el primer y ultimo dia del mes
            Calendar cal = Calendar.getInstance();
            //Creando las fechas
            Date primero = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date segunda  = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            //Enviando parametros a la consulta
            consulta.setParameter("primeraFecha", primero);
            consulta.setParameter("segundaFecha", segunda);
            consulta.setParameter("empleado", empleado);
            return (Integer)consulta.getSingleResult();
        }catch(Exception e){
            return -1;
        }
    }
    
}
