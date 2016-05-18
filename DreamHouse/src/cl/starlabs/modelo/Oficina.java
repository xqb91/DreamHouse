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
@Table(name = "OFICINA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oficina.findAll", query = "SELECT o FROM Oficina o"),
    @NamedQuery(name = "Oficina.findById", query = "SELECT o FROM Oficina o WHERE o.id = :id"),
    @NamedQuery(name = "Oficina.findByNumoficina", query = "SELECT o FROM Oficina o WHERE o.numoficina = :numoficina"),
    @NamedQuery(name = "Oficina.findByCodigopostal", query = "SELECT o FROM Oficina o WHERE o.codigopostal = :codigopostal")})
public class Oficina implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NUMOFICINA")
    private short numoficina;
    @Basic(optional = false)
    @Lob
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "CODIGOPOSTAL")
    private String codigopostal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numoficina")
    private Collection<Empleado> empleadoCollection;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "IDCIUDAD")
    @ManyToOne(optional = false)
    private Ciudad ciudad;

    public Oficina() {
    }

    public Oficina(BigDecimal id) {
        this.id = id;
    }

    public Oficina(BigDecimal id, short numoficina, String calle) {
        this.id = id;
        this.numoficina = numoficina;
        this.calle = calle;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public short getNumoficina() {
        return numoficina;
    }

    public void setNumoficina(short numoficina) {
        this.numoficina = numoficina;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oficina)) {
            return false;
        }
        Oficina other = (Oficina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Oficina[ id=" + id + " ]";
    }
    
}
