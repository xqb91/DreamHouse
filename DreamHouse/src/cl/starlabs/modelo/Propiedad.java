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
@Table(name = "PROPIEDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propiedad.findAll", query = "SELECT p FROM Propiedad p"),
    @NamedQuery(name = "Propiedad.ultimo", query = "SELECT p FROM Propiedad p ORDER BY p.numpropiedad DESC"),
    @NamedQuery(name = "Propiedad.findByNumpropiedad", query = "SELECT p FROM Propiedad p WHERE p.numpropiedad = :numpropiedad"),
    @NamedQuery(name = "Propiedad.findByCiudad", query = "SELECT p FROM Propiedad p WHERE p.ciudad = :ciudad"),
    @NamedQuery(name = "Propiedad.findByCodigopostal", query = "SELECT p FROM Propiedad p WHERE p.codigopostal = :codigopostal"),
    @NamedQuery(name = "Propiedad.findByHab", query = "SELECT p FROM Propiedad p WHERE p.hab = :hab"),
    @NamedQuery(name = "Propiedad.findByRenta", query = "SELECT p FROM Propiedad p WHERE p.renta = :renta"),
    @NamedQuery(name = "Propiedad.buscarPorPropietario", query = "SELECT p FROM Propiedad p WHERE p.numpropietario = :propietario"),
    @NamedQuery(name = "Propiedad.findByDisponible", query = "SELECT p FROM Propiedad p WHERE p.disponible = :disponible")})
public class Propiedad implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUMPROPIEDAD")
    private BigDecimal numpropiedad;
    @Basic(optional = false)
    @Lob
    @Column(name = "CALLE")
    private String calle;
    @Basic(optional = false)
    @Column(name = "CIUDAD")
    private BigInteger ciudad;
    @Column(name = "CODIGOPOSTAL")
    private String codigopostal;
    @Basic(optional = false)
    @Column(name = "HAB")
    private BigInteger hab;
    @Basic(optional = false)
    @Column(name = "RENTA")
    private double renta;
    @Basic(optional = false)
    @Column(name = "DISPONIBLE")
    private Character disponible;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numpropiedad")
    private Collection<Arriendo> arriendoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad")
    private Collection<Historialbusqueda> historialbusquedaCollection;
    @JoinColumn(name = "NUMEMPLEADO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Empleado numempleado;
    @JoinColumn(name = "NUMPROPIETARIO", referencedColumnName = "NUMPROPIETARIO")
    @ManyToOne(optional = false)
    private Propietario numpropietario;
    @JoinColumn(name = "TIPO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Tipopropiedades tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad")
    private Collection<Comision> comisionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad")
    private Collection<Agenda> agendaCollection;

    public Propiedad() {
    }

    public Propiedad(BigDecimal numpropiedad) {
        this.numpropiedad = numpropiedad;
    }

    public Propiedad(BigDecimal numpropiedad, String calle, BigInteger ciudad, BigInteger hab, double renta, Character disponible) {
        this.numpropiedad = numpropiedad;
        this.calle = calle;
        this.ciudad = ciudad;
        this.hab = hab;
        this.renta = renta;
        this.disponible = disponible;
    }
    
    public Propiedad(BigDecimal numpropiedad, String calle, BigInteger ciudad, String codigoPostal, BigInteger hab, double renta, Character disponible, Empleado empleado, Propietario propietario, Tipopropiedades tipo) {
        this.numpropiedad = numpropiedad;
        this.calle = calle;
        this.codigopostal = codigoPostal;
        this.ciudad = ciudad;
        this.hab = hab;
        this.renta = renta;
        this.disponible = disponible;
        this.numempleado = empleado;
        this.numpropietario = propietario;
        this.tipo = tipo;
    }

    public BigDecimal getNumpropiedad() {
        return numpropiedad;
    }

    public void setNumpropiedad(BigDecimal numpropiedad) {
        this.numpropiedad = numpropiedad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public BigInteger getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigInteger ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public BigInteger getHab() {
        return hab;
    }

    public void setHab(BigInteger hab) {
        this.hab = hab;
    }

    public double getRenta() {
        return renta;
    }

    public void setRenta(double renta) {
        this.renta = renta;
    }

    public Character getDisponible() {
        return disponible;
    }

    public void setDisponible(Character disponible) {
        this.disponible = disponible;
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

    public Empleado getNumempleado() {
        return numempleado;
    }

    public void setNumempleado(Empleado numempleado) {
        this.numempleado = numempleado;
    }

    public Propietario getNumpropietario() {
        return numpropietario;
    }

    public void setNumpropietario(Propietario numpropietario) {
        this.numpropietario = numpropietario;
    }

    public Tipopropiedades getTipo() {
        return tipo;
    }

    public void setTipo(Tipopropiedades tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Comision> getComisionCollection() {
        return comisionCollection;
    }

    public void setComisionCollection(Collection<Comision> comisionCollection) {
        this.comisionCollection = comisionCollection;
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
        hash += (numpropiedad != null ? numpropiedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propiedad)) {
            return false;
        }
        Propiedad other = (Propiedad) object;
        if ((this.numpropiedad == null && other.numpropiedad != null) || (this.numpropiedad != null && !this.numpropiedad.equals(other.numpropiedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Propiedad[ numpropiedad=" + numpropiedad + " ]";
    }
    
}
