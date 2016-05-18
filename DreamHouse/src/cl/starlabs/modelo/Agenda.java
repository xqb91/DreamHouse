/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "AGENDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a"),
    @NamedQuery(name = "Agenda.findById", query = "SELECT a FROM Agenda a WHERE a.id = :id"),
    @NamedQuery(name = "Agenda.findByFechaAgendamiento", query = "SELECT a FROM Agenda a WHERE a.fechaAgendamiento = :fechaAgendamiento"),
    @NamedQuery(name = "Agenda.findByFechaAgendada", query = "SELECT a FROM Agenda a WHERE a.fechaAgendada = :fechaAgendada"),
    @NamedQuery(name = "Agenda.findByRealizada", query = "SELECT a FROM Agenda a WHERE a.realizada = :realizada")})
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "FECHA_AGENDAMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAgendamiento;
    @Basic(optional = false)
    @Column(name = "FECHA_AGENDADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAgendada;
    @Basic(optional = false)
    @Column(name = "REALIZADA")
    private Character realizada;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "NUMCLIENTE")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "EMPLEADO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "PROPIEDAD", referencedColumnName = "NUMPROPIEDAD")
    @ManyToOne(optional = false)
    private Propiedad propiedad;
    @OneToMany(mappedBy = "eventoagenda")
    private Collection<Visita> visitaCollection;

    public Agenda() {
    }

    public Agenda(BigDecimal id) {
        this.id = id;
    }

    public Agenda(BigDecimal id, Date fechaAgendamiento, Date fechaAgendada, Character realizada) {
        this.id = id;
        this.fechaAgendamiento = fechaAgendamiento;
        this.fechaAgendada = fechaAgendada;
        this.realizada = realizada;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaAgendamiento() {
        return fechaAgendamiento;
    }

    public void setFechaAgendamiento(Date fechaAgendamiento) {
        this.fechaAgendamiento = fechaAgendamiento;
    }

    public Date getFechaAgendada() {
        return fechaAgendada;
    }

    public void setFechaAgendada(Date fechaAgendada) {
        this.fechaAgendada = fechaAgendada;
    }

    public Character getRealizada() {
        return realizada;
    }

    public void setRealizada(Character realizada) {
        this.realizada = realizada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    @XmlTransient
    public Collection<Visita> getVisitaCollection() {
        return visitaCollection;
    }

    public void setVisitaCollection(Collection<Visita> visitaCollection) {
        this.visitaCollection = visitaCollection;
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
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Agenda[ id=" + id + " ]";
    }
    
}
