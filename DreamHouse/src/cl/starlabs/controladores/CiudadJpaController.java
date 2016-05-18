/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Ciudad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Provincia;
import cl.starlabs.modelo.Oficina;
import java.util.ArrayList;
import java.util.Collection;
import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Propietario;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Victor
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) throws PreexistingEntityException, Exception {
        if (ciudad.getOficinaCollection() == null) {
            ciudad.setOficinaCollection(new ArrayList<Oficina>());
        }
        if (ciudad.getClienteCollection() == null) {
            ciudad.setClienteCollection(new ArrayList<Cliente>());
        }
        if (ciudad.getPropietarioCollection() == null) {
            ciudad.setPropietarioCollection(new ArrayList<Propietario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia provincia = ciudad.getProvincia();
            if (provincia != null) {
                provincia = em.getReference(provincia.getClass(), provincia.getIdprovincia());
                ciudad.setProvincia(provincia);
            }
            Collection<Oficina> attachedOficinaCollection = new ArrayList<Oficina>();
            for (Oficina oficinaCollectionOficinaToAttach : ciudad.getOficinaCollection()) {
                oficinaCollectionOficinaToAttach = em.getReference(oficinaCollectionOficinaToAttach.getClass(), oficinaCollectionOficinaToAttach.getId());
                attachedOficinaCollection.add(oficinaCollectionOficinaToAttach);
            }
            ciudad.setOficinaCollection(attachedOficinaCollection);
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : ciudad.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getNumcliente());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            ciudad.setClienteCollection(attachedClienteCollection);
            Collection<Propietario> attachedPropietarioCollection = new ArrayList<Propietario>();
            for (Propietario propietarioCollectionPropietarioToAttach : ciudad.getPropietarioCollection()) {
                propietarioCollectionPropietarioToAttach = em.getReference(propietarioCollectionPropietarioToAttach.getClass(), propietarioCollectionPropietarioToAttach.getNumpropietario());
                attachedPropietarioCollection.add(propietarioCollectionPropietarioToAttach);
            }
            ciudad.setPropietarioCollection(attachedPropietarioCollection);
            em.persist(ciudad);
            if (provincia != null) {
                provincia.getCiudadCollection().add(ciudad);
                provincia = em.merge(provincia);
            }
            for (Oficina oficinaCollectionOficina : ciudad.getOficinaCollection()) {
                Ciudad oldCiudadOfOficinaCollectionOficina = oficinaCollectionOficina.getCiudad();
                oficinaCollectionOficina.setCiudad(ciudad);
                oficinaCollectionOficina = em.merge(oficinaCollectionOficina);
                if (oldCiudadOfOficinaCollectionOficina != null) {
                    oldCiudadOfOficinaCollectionOficina.getOficinaCollection().remove(oficinaCollectionOficina);
                    oldCiudadOfOficinaCollectionOficina = em.merge(oldCiudadOfOficinaCollectionOficina);
                }
            }
            for (Cliente clienteCollectionCliente : ciudad.getClienteCollection()) {
                Ciudad oldCiudadOfClienteCollectionCliente = clienteCollectionCliente.getCiudad();
                clienteCollectionCliente.setCiudad(ciudad);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldCiudadOfClienteCollectionCliente != null) {
                    oldCiudadOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldCiudadOfClienteCollectionCliente = em.merge(oldCiudadOfClienteCollectionCliente);
                }
            }
            for (Propietario propietarioCollectionPropietario : ciudad.getPropietarioCollection()) {
                Ciudad oldCiudadOfPropietarioCollectionPropietario = propietarioCollectionPropietario.getCiudad();
                propietarioCollectionPropietario.setCiudad(ciudad);
                propietarioCollectionPropietario = em.merge(propietarioCollectionPropietario);
                if (oldCiudadOfPropietarioCollectionPropietario != null) {
                    oldCiudadOfPropietarioCollectionPropietario.getPropietarioCollection().remove(propietarioCollectionPropietario);
                    oldCiudadOfPropietarioCollectionPropietario = em.merge(oldCiudadOfPropietarioCollectionPropietario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCiudad(ciudad.getIdciudad()) != null) {
                throw new PreexistingEntityException("Ciudad " + ciudad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getIdciudad());
            Provincia provinciaOld = persistentCiudad.getProvincia();
            Provincia provinciaNew = ciudad.getProvincia();
            Collection<Oficina> oficinaCollectionOld = persistentCiudad.getOficinaCollection();
            Collection<Oficina> oficinaCollectionNew = ciudad.getOficinaCollection();
            Collection<Cliente> clienteCollectionOld = persistentCiudad.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = ciudad.getClienteCollection();
            Collection<Propietario> propietarioCollectionOld = persistentCiudad.getPropietarioCollection();
            Collection<Propietario> propietarioCollectionNew = ciudad.getPropietarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Oficina oficinaCollectionOldOficina : oficinaCollectionOld) {
                if (!oficinaCollectionNew.contains(oficinaCollectionOldOficina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oficina " + oficinaCollectionOldOficina + " since its ciudad field is not nullable.");
                }
            }
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteCollectionOldCliente + " since its ciudad field is not nullable.");
                }
            }
            for (Propietario propietarioCollectionOldPropietario : propietarioCollectionOld) {
                if (!propietarioCollectionNew.contains(propietarioCollectionOldPropietario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propietario " + propietarioCollectionOldPropietario + " since its ciudad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (provinciaNew != null) {
                provinciaNew = em.getReference(provinciaNew.getClass(), provinciaNew.getIdprovincia());
                ciudad.setProvincia(provinciaNew);
            }
            Collection<Oficina> attachedOficinaCollectionNew = new ArrayList<Oficina>();
            for (Oficina oficinaCollectionNewOficinaToAttach : oficinaCollectionNew) {
                oficinaCollectionNewOficinaToAttach = em.getReference(oficinaCollectionNewOficinaToAttach.getClass(), oficinaCollectionNewOficinaToAttach.getId());
                attachedOficinaCollectionNew.add(oficinaCollectionNewOficinaToAttach);
            }
            oficinaCollectionNew = attachedOficinaCollectionNew;
            ciudad.setOficinaCollection(oficinaCollectionNew);
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getNumcliente());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            ciudad.setClienteCollection(clienteCollectionNew);
            Collection<Propietario> attachedPropietarioCollectionNew = new ArrayList<Propietario>();
            for (Propietario propietarioCollectionNewPropietarioToAttach : propietarioCollectionNew) {
                propietarioCollectionNewPropietarioToAttach = em.getReference(propietarioCollectionNewPropietarioToAttach.getClass(), propietarioCollectionNewPropietarioToAttach.getNumpropietario());
                attachedPropietarioCollectionNew.add(propietarioCollectionNewPropietarioToAttach);
            }
            propietarioCollectionNew = attachedPropietarioCollectionNew;
            ciudad.setPropietarioCollection(propietarioCollectionNew);
            ciudad = em.merge(ciudad);
            if (provinciaOld != null && !provinciaOld.equals(provinciaNew)) {
                provinciaOld.getCiudadCollection().remove(ciudad);
                provinciaOld = em.merge(provinciaOld);
            }
            if (provinciaNew != null && !provinciaNew.equals(provinciaOld)) {
                provinciaNew.getCiudadCollection().add(ciudad);
                provinciaNew = em.merge(provinciaNew);
            }
            for (Oficina oficinaCollectionNewOficina : oficinaCollectionNew) {
                if (!oficinaCollectionOld.contains(oficinaCollectionNewOficina)) {
                    Ciudad oldCiudadOfOficinaCollectionNewOficina = oficinaCollectionNewOficina.getCiudad();
                    oficinaCollectionNewOficina.setCiudad(ciudad);
                    oficinaCollectionNewOficina = em.merge(oficinaCollectionNewOficina);
                    if (oldCiudadOfOficinaCollectionNewOficina != null && !oldCiudadOfOficinaCollectionNewOficina.equals(ciudad)) {
                        oldCiudadOfOficinaCollectionNewOficina.getOficinaCollection().remove(oficinaCollectionNewOficina);
                        oldCiudadOfOficinaCollectionNewOficina = em.merge(oldCiudadOfOficinaCollectionNewOficina);
                    }
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Ciudad oldCiudadOfClienteCollectionNewCliente = clienteCollectionNewCliente.getCiudad();
                    clienteCollectionNewCliente.setCiudad(ciudad);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldCiudadOfClienteCollectionNewCliente != null && !oldCiudadOfClienteCollectionNewCliente.equals(ciudad)) {
                        oldCiudadOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldCiudadOfClienteCollectionNewCliente = em.merge(oldCiudadOfClienteCollectionNewCliente);
                    }
                }
            }
            for (Propietario propietarioCollectionNewPropietario : propietarioCollectionNew) {
                if (!propietarioCollectionOld.contains(propietarioCollectionNewPropietario)) {
                    Ciudad oldCiudadOfPropietarioCollectionNewPropietario = propietarioCollectionNewPropietario.getCiudad();
                    propietarioCollectionNewPropietario.setCiudad(ciudad);
                    propietarioCollectionNewPropietario = em.merge(propietarioCollectionNewPropietario);
                    if (oldCiudadOfPropietarioCollectionNewPropietario != null && !oldCiudadOfPropietarioCollectionNewPropietario.equals(ciudad)) {
                        oldCiudadOfPropietarioCollectionNewPropietario.getPropietarioCollection().remove(propietarioCollectionNewPropietario);
                        oldCiudadOfPropietarioCollectionNewPropietario = em.merge(oldCiudadOfPropietarioCollectionNewPropietario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = ciudad.getIdciudad();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getIdciudad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Oficina> oficinaCollectionOrphanCheck = ciudad.getOficinaCollection();
            for (Oficina oficinaCollectionOrphanCheckOficina : oficinaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Oficina " + oficinaCollectionOrphanCheckOficina + " in its oficinaCollection field has a non-nullable ciudad field.");
            }
            Collection<Cliente> clienteCollectionOrphanCheck = ciudad.getClienteCollection();
            for (Cliente clienteCollectionOrphanCheckCliente : clienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Cliente " + clienteCollectionOrphanCheckCliente + " in its clienteCollection field has a non-nullable ciudad field.");
            }
            Collection<Propietario> propietarioCollectionOrphanCheck = ciudad.getPropietarioCollection();
            for (Propietario propietarioCollectionOrphanCheckPropietario : propietarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Propietario " + propietarioCollectionOrphanCheckPropietario + " in its propietarioCollection field has a non-nullable ciudad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Provincia provincia = ciudad.getProvincia();
            if (provincia != null) {
                provincia.getCiudadCollection().remove(ciudad);
                provincia = em.merge(provincia);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Ciudad> listar() {
        try
        {
            Query consulta = getEntityManager().createNamedQuery("Ciudad.findAll");
            return consulta.getResultList();
        }catch(Exception e)
        {
            return null;
        }
    }
    
    public Ciudad buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Ciudad.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Ciudad)consulta.getSingleResult();
        }catch(Exception e) {
            return null;
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
