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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "EMPLEADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findById", query = "SELECT e FROM Empleado e WHERE e.id = :id"),
    @NamedQuery(name = "Empleado.findByNumempleado", query = "SELECT e FROM Empleado e WHERE e.numempleado = :numempleado"),
    @NamedQuery(name = "Empleado.findByContrasena", query = "SELECT e FROM Empleado e WHERE e.contrasena = :contrasena"),
    @NamedQuery(name = "Empleado.findByNombre", query = "SELECT e FROM Empleado e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empleado.buscarUltimo", query = "SELECT e FROM Empleado e ORDER BY e.id DESC"),
    @NamedQuery(name = "Empleado.findByApaterno", query = "SELECT e FROM Empleado e WHERE e.apaterno = :apaterno"),
    @NamedQuery(name = "Empleado.findByAmaterno", query = "SELECT e FROM Empleado e WHERE e.amaterno = :amaterno"),
    @NamedQuery(name = "Empleado.findBySexo", query = "SELECT e FROM Empleado e WHERE e.sexo = :sexo"),
    @NamedQuery(name = "Empleado.findByFechnac", query = "SELECT e FROM Empleado e WHERE e.fechnac = :fechnac"),
    @NamedQuery(name = "Empleado.findBySalario", query = "SELECT e FROM Empleado e WHERE e.salario = :salario"),
    @NamedQuery(name = "Empleado.findByPorcentaje", query = "SELECT e FROM Empleado e WHERE e.porcentaje = :porcentaje")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "NUMEMPLEADO")
    private int numempleado;
    @Basic(optional = false)
    @Column(name = "CONTRASENA")
    private String contrasena;
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
    @Column(name = "SEXO")
    private Character sexo;
    @Basic(optional = false)
    @Column(name = "FECHNAC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechnac;
    @Basic(optional = false)
    @Column(name = "SALARIO")
    private double salario;
    @Basic(optional = false)
    @Column(name = "PORCENTAJE")
    private double porcentaje;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Totpropempleado totpropempleado;
    @JoinColumn(name = "CARGO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "NUMOFICINA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Oficina numoficina;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numempleado")
    private Collection<Propiedad> propiedadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Comision> comisionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private Collection<Agenda> agendaCollection;

    public Empleado() {
    }

    public Empleado(BigDecimal id) {
        this.id = id;
    }

    public Empleado(BigDecimal id, int numempleado, String contrasena, String nombre, String apaterno, String amaterno, Character sexo, Date fechnac, double salario, double porcentaje, Cargo c, Oficina of) {
        this.id = id;
        this.numempleado = numempleado;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.sexo = sexo;
        this.fechnac = fechnac;
        this.salario = salario;
        this.porcentaje = porcentaje;
        this.cargo = c;
        this.numoficina = of;
    }
    
    public Empleado(int numempleado, String contrasena, String nombre, String apaterno, String amaterno, Character sexo, Date fechnac, double salario, double porcentaje) {
        this.numempleado = numempleado;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.sexo = sexo;
        this.fechnac = fechnac;
        this.salario = salario;
        this.porcentaje = porcentaje;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public int getNumempleado() {
        return numempleado;
    }

    public void setNumempleado(int numempleado) {
        this.numempleado = numempleado;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getFechnac() {
        return fechnac;
    }

    public void setFechnac(Date fechnac) {
        this.fechnac = fechnac;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Totpropempleado getTotpropempleado() {
        return totpropempleado;
    }

    public void setTotpropempleado(Totpropempleado totpropempleado) {
        this.totpropempleado = totpropempleado;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Oficina getNumoficina() {
        return numoficina;
    }

    public void setNumoficina(Oficina numoficina) {
        this.numoficina = numoficina;
    }

    @XmlTransient
    public Collection<Propiedad> getPropiedadCollection() {
        return propiedadCollection;
    }

    public void setPropiedadCollection(Collection<Propiedad> propiedadCollection) {
        this.propiedadCollection = propiedadCollection;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Empleado[ id=" + id + " ]";
    }
    
}
