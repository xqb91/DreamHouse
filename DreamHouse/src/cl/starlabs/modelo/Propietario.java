/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "PROPIETARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietario.findAll", query = "SELECT p FROM Propietario p"),
    @NamedQuery(name = "Propietario.findByNumpropietario", query = "SELECT p FROM Propietario p WHERE p.numpropietario = :numpropietario"),
    @NamedQuery(name = "Propietario.findByRut", query = "SELECT p FROM Propietario p WHERE p.rut = :rut"),
    @NamedQuery(name = "Propietario.findByDv", query = "SELECT p FROM Propietario p WHERE p.dv = :dv"),
    @NamedQuery(name = "Propietario.findByNombre", query = "SELECT p FROM Propietario p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Propietario.findByApaterno", query = "SELECT p FROM Propietario p WHERE p.apaterno = :apaterno"),
    @NamedQuery(name = "Propietario.findByAmaterno", query = "SELECT p FROM Propietario p WHERE p.amaterno = :amaterno"),
    @NamedQuery(name = "Propietario.findByTelefono", query = "SELECT p FROM Propietario p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Propietario.findByEmail", query = "SELECT p FROM Propietario p WHERE p.email = :email")})
public class Propietario implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUMPROPIETARIO")
    private BigDecimal numpropietario;
    @Basic(optional = false)
    @Column(name = "RUT")
    private int rut;
    @Basic(optional = false)
    @Column(name = "DV")
    private Character dv;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APATERNO")
    private String apaterno;
    @Basic(optional = false)
    @Column(name = "AMATERNO")
    private String amaterno;
    @Basic(optional = false)
    @Lob
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numpropietario")
    private Collection<Propiedad> propiedadCollection;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "IDCIUDAD")
    @ManyToOne(optional = false)
    private Ciudad ciudad;

    public Propietario() {
    }

    public Propietario(BigDecimal numpropietario) {
        this.numpropietario = numpropietario;
    }

    public Propietario(BigDecimal numpropietario, int rut, Character dv, String nombre, String apaterno, String amaterno, String direccion, String telefono) {
        this.numpropietario = numpropietario;
        this.rut = rut;
        this.dv = dv;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public BigDecimal getNumpropietario() {
        return numpropietario;
    }

    public void setNumpropietario(BigDecimal numpropietario) {
        this.numpropietario = numpropietario;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public Character getDv() {
        return dv;
    }

    public void setDv(Character dv) {
        this.dv = dv;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Propiedad> getPropiedadCollection() {
        return propiedadCollection;
    }

    public void setPropiedadCollection(Collection<Propiedad> propiedadCollection) {
        this.propiedadCollection = propiedadCollection;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numpropietario != null ? numpropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietario)) {
            return false;
        }
        Propietario other = (Propietario) object;
        if ((this.numpropietario == null && other.numpropietario != null) || (this.numpropietario != null && !this.numpropietario.equals(other.numpropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Propietario[ numpropietario=" + numpropietario + " ]";
    }
    
}
