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
 * @author cetecom
 */
@Entity
@Table(name = "ARRIENDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Arriendo.findAll", query = "SELECT a FROM Arriendo a"),
    @NamedQuery(name = "Arriendo.findByNumarriendo", query = "SELECT a FROM Arriendo a WHERE a.numarriendo = :numarriendo"),
    @NamedQuery(name = "Arriendo.findByRenta", query = "SELECT a FROM Arriendo a WHERE a.renta = :renta"),
    @NamedQuery(name = "Arriendo.findByFormapago", query = "SELECT a FROM Arriendo a WHERE a.formapago = :formapago"),
    @NamedQuery(name = "Arriendo.findByDeposito", query = "SELECT a FROM Arriendo a WHERE a.deposito = :deposito"),
    @NamedQuery(name = "Arriendo.findByPagado", query = "SELECT a FROM Arriendo a WHERE a.pagado = :pagado"),
    @NamedQuery(name = "Arriendo.findByIniciorenta", query = "SELECT a FROM Arriendo a WHERE a.iniciorenta = :iniciorenta"),
    @NamedQuery(name = "Arriendo.findByFinrenta", query = "SELECT a FROM Arriendo a WHERE a.finrenta = :finrenta"),
    @NamedQuery(name = "Arriendo.findByNumempleado", query = "SELECT a FROM Arriendo a WHERE a.numempleado = :numempleado")})
public class Arriendo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "NUMARRIENDO")
    private BigDecimal numarriendo;
    @Basic(optional = false)
    @Column(name = "RENTA")
    private double renta;
    @Basic(optional = false)
    @Column(name = "FORMAPAGO")
    private String formapago;
    @Column(name = "DEPOSITO")
    private Double deposito;
    @Basic(optional = false)
    @Column(name = "PAGADO")
    private Character pagado;
    @Basic(optional = false)
    @Column(name = "INICIORENTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iniciorenta;
    @Basic(optional = false)
    @Column(name = "FINRENTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finrenta;
    @Basic(optional = false)
    @Column(name = "NUMEMPLEADO")
    private int numempleado;
    @JoinColumn(name = "NUMCLIENTE", referencedColumnName = "NUMCLIENTE")
    @ManyToOne(optional = false)
    private Cliente numcliente;
    @JoinColumn(name = "NUMPROPIEDAD", referencedColumnName = "NUMPROPIEDAD")
    @ManyToOne(optional = false)
    private Propiedad numpropiedad;

    public Arriendo() {
    }

    public Arriendo(BigDecimal numarriendo) {
        this.numarriendo = numarriendo;
    }

    public Arriendo(BigDecimal numarriendo, double renta, String formapago, Character pagado, Date iniciorenta, Date finrenta, int numempleado) {
        this.numarriendo = numarriendo;
        this.renta = renta;
        this.formapago = formapago;
        this.pagado = pagado;
        this.iniciorenta = iniciorenta;
        this.finrenta = finrenta;
        this.numempleado = numempleado;
    }

    public BigDecimal getNumarriendo() {
        return numarriendo;
    }

    public void setNumarriendo(BigDecimal numarriendo) {
        this.numarriendo = numarriendo;
    }

    public double getRenta() {
        return renta;
    }

    public void setRenta(double renta) {
        this.renta = renta;
    }

    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    public Double getDeposito() {
        return deposito;
    }

    public void setDeposito(Double deposito) {
        this.deposito = deposito;
    }

    public Character getPagado() {
        return pagado;
    }

    public void setPagado(Character pagado) {
        this.pagado = pagado;
    }

    public Date getIniciorenta() {
        return iniciorenta;
    }

    public void setIniciorenta(Date iniciorenta) {
        this.iniciorenta = iniciorenta;
    }

    public Date getFinrenta() {
        return finrenta;
    }

    public void setFinrenta(Date finrenta) {
        this.finrenta = finrenta;
    }

    public int getNumempleado() {
        return numempleado;
    }

    public void setNumempleado(int numempleado) {
        this.numempleado = numempleado;
    }

    public Cliente getNumcliente() {
        return numcliente;
    }

    public void setNumcliente(Cliente numcliente) {
        this.numcliente = numcliente;
    }

    public Propiedad getNumpropiedad() {
        return numpropiedad;
    }

    public void setNumpropiedad(Propiedad numpropiedad) {
        this.numpropiedad = numpropiedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numarriendo != null ? numarriendo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Arriendo)) {
            return false;
        }
        Arriendo other = (Arriendo) object;
        if ((this.numarriendo == null && other.numarriendo != null) || (this.numarriendo != null && !this.numarriendo.equals(other.numarriendo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Arriendo[ numarriendo=" + numarriendo + " ]";
    }
    
}
