/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "HISTORIALBUSQUEDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historialbusqueda.findAll", query = "SELECT h FROM Historialbusqueda h"),
    @NamedQuery(name = "Historialbusqueda.findById", query = "SELECT h FROM Historialbusqueda h WHERE h.id = :id"),
    @NamedQuery(name = "Historialbusqueda.findByFecha", query = "SELECT h FROM Historialbusqueda h WHERE h.fecha = :fecha")})
public class Historialbusqueda implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "NUMCLIENTE")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "PROPIEDAD", referencedColumnName = "NUMPROPIEDAD")
    @ManyToOne(optional = false)
    private Propiedad propiedad;

    public Historialbusqueda() {
    }

    public Historialbusqueda(BigDecimal id) {
        this.id = id;
    }

    public Historialbusqueda(BigDecimal id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
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
        if (!(object instanceof Historialbusqueda)) {
            return false;
        }
        Historialbusqueda other = (Historialbusqueda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Historialbusqueda[ id=" + id + " ]";
    }
    
}
