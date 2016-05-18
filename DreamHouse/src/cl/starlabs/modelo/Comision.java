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
@Table(name = "COMISION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comision.findAll", query = "SELECT c FROM Comision c"),
    @NamedQuery(name = "Comision.findById", query = "SELECT c FROM Comision c WHERE c.id = :id"),
    @NamedQuery(name = "Comision.findByFecha", query = "SELECT c FROM Comision c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Comision.comisionPorEmpleadoYMes", query = "SELECT SUM(c.comision) FROM Comision c WHERE c.fecha BETWEEN :primeraFecha AND :segundaFecha AND c.empleado = :empleado"),
    @NamedQuery(name = "Comision.findByComision", query = "SELECT c FROM Comision c WHERE c.comision = :comision")})
public class Comision implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "COMISION")
    private double comision;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "PROPIEDAD", referencedColumnName = "NUMPROPIEDAD")
    @ManyToOne(optional = false)
    private Propiedad propiedad;

    public Comision() {
    }

    public Comision(BigDecimal id) {
        this.id = id;
    }

    public Comision(BigDecimal id, Date fecha, double comision) {
        this.id = id;
        this.fecha = fecha;
        this.comision = comision;
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

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
        if (!(object instanceof Comision)) {
            return false;
        }
        Comision other = (Comision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Comision[ id=" + id + " ]";
    }
    
}
