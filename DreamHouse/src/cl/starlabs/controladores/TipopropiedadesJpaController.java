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
import cl.starlabs.modelo.Propiedad;
import cl.starlabs.modelo.Tipopropiedades;
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
public class TipopropiedadesJpaController implements Serializable {

    public TipopropiedadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipopropiedades tipopropiedades) throws PreexistingEntityException, Exception {
        if (tipopropiedades.getPropiedadCollection() == null) {
            tipopropiedades.setPropiedadCollection(new ArrayList<Propiedad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Propiedad> attachedPropiedadCollection = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionPropiedadToAttach : tipopropiedades.getPropiedadCollection()) {
                propiedadCollectionPropiedadToAttach = em.getReference(propiedadCollectionPropiedadToAttach.getClass(), propiedadCollectionPropiedadToAttach.getNumpropiedad());
                attachedPropiedadCollection.add(propiedadCollectionPropiedadToAttach);
            }
            tipopropiedades.setPropiedadCollection(attachedPropiedadCollection);
            em.persist(tipopropiedades);
            for (Propiedad propiedadCollectionPropiedad : tipopropiedades.getPropiedadCollection()) {
                Tipopropiedades oldTipoOfPropiedadCollectionPropiedad = propiedadCollectionPropiedad.getTipo();
                propiedadCollectionPropiedad.setTipo(tipopropiedades);
                propiedadCollectionPropiedad = em.merge(propiedadCollectionPropiedad);
                if (oldTipoOfPropiedadCollectionPropiedad != null) {
                    oldTipoOfPropiedadCollectionPropiedad.getPropiedadCollection().remove(propiedadCollectionPropiedad);
                    oldTipoOfPropiedadCollectionPropiedad = em.merge(oldTipoOfPropiedadCollectionPropiedad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipopropiedades(tipopropiedades.getId()) != null) {
                throw new PreexistingEntityException("Tipopropiedades " + tipopropiedades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipopropiedades tipopropiedades) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipopropiedades persistentTipopropiedades = em.find(Tipopropiedades.class, tipopropiedades.getId());
            Collection<Propiedad> propiedadCollectionOld = persistentTipopropiedades.getPropiedadCollection();
            Collection<Propiedad> propiedadCollectionNew = tipopropiedades.getPropiedadCollection();
            List<String> illegalOrphanMessages = null;
            for (Propiedad propiedadCollectionOldPropiedad : propiedadCollectionOld) {
                if (!propiedadCollectionNew.contains(propiedadCollectionOldPropiedad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propiedad " + propiedadCollectionOldPropiedad + " since its tipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Propiedad> attachedPropiedadCollectionNew = new ArrayList<Propiedad>();
            for (Propiedad propiedadCollectionNewPropiedadToAttach : propiedadCollectionNew) {
                propiedadCollectionNewPropiedadToAttach = em.getReference(propiedadCollectionNewPropiedadToAttach.getClass(), propiedadCollectionNewPropiedadToAttach.getNumpropiedad());
                attachedPropiedadCollectionNew.add(propiedadCollectionNewPropiedadToAttach);
            }
            propiedadCollectionNew = attachedPropiedadCollectionNew;
            tipopropiedades.setPropiedadCollection(propiedadCollectionNew);
            tipopropiedades = em.merge(tipopropiedades);
            for (Propiedad propiedadCollectionNewPropiedad : propiedadCollectionNew) {
                if (!propiedadCollectionOld.contains(propiedadCollectionNewPropiedad)) {
                    Tipopropiedades oldTipoOfPropiedadCollectionNewPropiedad = propiedadCollectionNewPropiedad.getTipo();
                    propiedadCollectionNewPropiedad.setTipo(tipopropiedades);
                    propiedadCollectionNewPropiedad = em.merge(propiedadCollectionNewPropiedad);
                    if (oldTipoOfPropiedadCollectionNewPropiedad != null && !oldTipoOfPropiedadCollectionNewPropiedad.equals(tipopropiedades)) {
                        oldTipoOfPropiedadCollectionNewPropiedad.getPropiedadCollection().remove(propiedadCollectionNewPropiedad);
                        oldTipoOfPropiedadCollectionNewPropiedad = em.merge(oldTipoOfPropiedadCollectionNewPropiedad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tipopropiedades.getId();
                if (findTipopropiedades(id) == null) {
                    throw new NonexistentEntityException("The tipopropiedades with id " + id + " no longer exists.");
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
            Tipopropiedades tipopropiedades;
            try {
                tipopropiedades = em.getReference(Tipopropiedades.class, id);
                tipopropiedades.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipopropiedades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Propiedad> propiedadCollectionOrphanCheck = tipopropiedades.getPropiedadCollection();
            for (Propiedad propiedadCollectionOrphanCheckPropiedad : propiedadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipopropiedades (" + tipopropiedades + ") cannot be destroyed since the Propiedad " + propiedadCollectionOrphanCheckPropiedad + " in its propiedadCollection field has a non-nullable tipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipopropiedades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipopropiedades> findTipopropiedadesEntities() {
        return findTipopropiedadesEntities(true, -1, -1);
    }

    public List<Tipopropiedades> findTipopropiedadesEntities(int maxResults, int firstResult) {
        return findTipopropiedadesEntities(false, maxResults, firstResult);
    }

    private List<Tipopropiedades> findTipopropiedadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipopropiedades.class));
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

    public List<Tipopropiedades> listar() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Tipopropiedades.findAll");
            return consulta.getResultList();
        }catch(Exception e)
        {
            return null;
        }
    }
    
    public Tipopropiedades buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Tipopropiedades.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Tipopropiedades)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Tipopropiedades findTipopropiedades(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipopropiedades.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipopropiedadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipopropiedades> rt = cq.from(Tipopropiedades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
