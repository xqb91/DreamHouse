/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c ORDER BY c.apaterno ASC"),
    @NamedQuery(name = "Cliente.ultimo", query = "SELECT c FROM Cliente c ORDER BY c.numcliente DESC"),
    @NamedQuery(name = "Cliente.findByNumcliente", query = "SELECT c FROM Cliente c WHERE c.numcliente = :numcliente"),
    @NamedQuery(name = "Cliente.findByRut", query = "SELECT c FROM Cliente c WHERE c.rut LIKE :rut"),
    @NamedQuery(name = "Cliente.findByDv", query = "SELECT c FROM Cliente c WHERE c.dv = :dv"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByApaterno", query = "SELECT c FROM Cliente c WHERE c.apaterno = :apaterno"),
    @NamedQuery(name = "Cliente.findByAmaterno", query = "SELECT c FROM Cliente c WHERE c.amaterno = :amaterno"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByTipopref", query = "SELECT c FROM Cliente c WHERE c.tipopref = :tipopref"),
    @NamedQuery(name = "Cliente.findByMaxrent", query = "SELECT c FROM Cliente c WHERE c.maxrent = :maxrent")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUMCLIENTE")
    private BigDecimal numcliente;
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
    @Basic(optional = false)
    @Column(name = "TIPOPREF")
    private BigInteger tipopref;
    @Basic(optional = false)
    @Column(name = "MAXRENT")
    private double maxrent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numcliente")
    private Collection<Arriendo> arriendoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Collection<Historialbusqueda> historialbusquedaCollection;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "IDCIUDAD")
    @ManyToOne(optional = false)
    private Ciudad ciudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Collection<Agenda> agendaCollection;

    public Cliente() {
    }

    public Cliente(BigDecimal numcliente) {
        this.numcliente = numcliente;
    }

    public Cliente(BigDecimal numcliente, int rut, Character dv, String nombre, String apaterno, String amaterno, String direccion, Ciudad c, String mail, String telefono, BigInteger tipopref, double maxrent) {
        this.numcliente = numcliente;
        this.rut = rut;
        this.dv = dv;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.ciudad = c;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = mail;
        this.tipopref = tipopref;
        this.maxrent = maxrent;
    }
    
    public Cliente(int rut, Character dv, String nombre, String apaterno, String amaterno, String direccion, Ciudad c, String email, String telefono, BigInteger tipopref, double maxrent) {
        this.numcliente = new BigDecimal(0);
        this.rut = rut;
        this.dv = dv;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.direccion = direccion;
        this.ciudad = c;
        this.email = email;
        this.telefono = telefono;
        this.tipopref = tipopref;
        this.maxrent = maxrent;
    }

    public BigDecimal getNumcliente() {
        return numcliente;
    }

    public void setNumcliente(BigDecimal numcliente) {
        this.numcliente = numcliente;
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

    public BigInteger getTipopref() {
        return tipopref;
    }

    public void setTipopref(BigInteger tipopref) {
        this.tipopref = tipopref;
    }

    public double getMaxrent() {
        return maxrent;
    }

    public void setMaxrent(double maxrent) {
        this.maxrent = maxrent;
    }

    public String nombreCompleto() {
        return this.nombre.toUpperCase().charAt(0)+this.nombre.substring(1, (this.nombre.length())).toLowerCase()+" "+this.apaterno.toUpperCase().charAt(0)+this.apaterno.substring(1, (this.apaterno.length())).toLowerCase()+" "+this.amaterno.toUpperCase().charAt(0)+this.amaterno.substring(1, (this.amaterno.length())).toLowerCase();
    }
    
    @XmlTransient
    public Collection<Arriendo> getArriendoCollection() {
        return arriendoCollection;
    }

    public void setArriendoCollection(Collection<Arriendo> arriendoCollection) {
        this.arriendoCollection = arriendoCollection;
    }

    @XmlTransient
    public Collection<Historialbusqueda> getHistorialbusquedaCollection() {
        return historialbusquedaCollection;
    }

    public void setHistorialbusquedaCollection(Collection<Historialbusqueda> historialbusquedaCollection) {
        this.historialbusquedaCollection = historialbusquedaCollection;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @XmlTransient
    public Collection<Agenda> getAgendaCollection() {
        return agendaCollection;
    }

    public void setAgendaCollection(Collection<Agenda> agendaCollection) {
        this.agendaCollection = agendaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numcliente != null ? numcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.numcliente == null && other.numcliente != null) || (this.numcliente != null && !this.numcliente.equals(other.numcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Cliente[ numcliente=" + numcliente + " ]";
    }
    
}
