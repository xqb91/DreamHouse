/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "TOTPROPEMPLEADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Totpropempleado.findAll", query = "SELECT t FROM Totpropempleado t"),
    @NamedQuery(name = "Totpropempleado.findByNumempleado", query = "SELECT t FROM Totpropempleado t WHERE t.numempleado = :numempleado"),
    @NamedQuery(name = "Totpropempleado.findByTotprop", query = "SELECT t FROM Totpropempleado t WHERE t.totprop = :totprop")})
public class Totpropempleado implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUMEMPLEADO")
    private BigDecimal numempleado;
    @Column(name = "TOTPROP")
    private BigInteger totprop;
    @JoinColumn(name = "NUMEMPLEADO", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Empleado empleado;

    public Totpropempleado() {
    }

    public Totpropempleado(BigDecimal numempleado) {
        this.numempleado = numempleado;
    }

    public BigDecimal getNumempleado() {
        return numempleado;
    }

    public void setNumempleado(BigDecimal numempleado) {
        this.numempleado = numempleado;
    }

    public BigInteger getTotprop() {
        return totprop;
    }

    public void setTotprop(BigInteger totprop) {
        this.totprop = totprop;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numempleado != null ? numempleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Totpropempleado)) {
            return false;
        }
        Totpropempleado other = (Totpropempleado) object;
        if ((this.numempleado == null && other.numempleado != null) || (this.numempleado != null && !this.numempleado.equals(other.numempleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Totpropempleado[ numempleado=" + numempleado + " ]";
    }
    
}
