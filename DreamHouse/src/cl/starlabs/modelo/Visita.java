/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "VISITA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visita.findAll", query = "SELECT v FROM Visita v"),
    @NamedQuery(name = "Visita.findByNumcliente", query = "SELECT v FROM Visita v WHERE v.visitaPK.numcliente = :numcliente"),
    @NamedQuery(name = "Visita.findByNumpropiedad", query = "SELECT v FROM Visita v WHERE v.visitaPK.numpropiedad = :numpropiedad"),
    @NamedQuery(name = "Visita.findByFecha", query = "SELECT v FROM Visita v WHERE v.visitaPK.fecha = :fecha")})
public class Visita implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VisitaPK visitaPK;
    @Lob
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumn(name = "EVENTOAGENDA", referencedColumnName = "ID")
    @ManyToOne
    private Agenda eventoagenda;

    public Visita() {
    }

    public Visita(VisitaPK visitaPK) {
        this.visitaPK = visitaPK;
    }

    public Visita(BigInteger numcliente, BigInteger numpropiedad, Date fecha) {
        this.visitaPK = new VisitaPK(numcliente, numpropiedad, fecha);
    }

    public VisitaPK getVisitaPK() {
        return visitaPK;
    }

    public void setVisitaPK(VisitaPK visitaPK) {
        this.visitaPK = visitaPK;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Agenda getEventoagenda() {
        return eventoagenda;
    }

    public void setEventoagenda(Agenda eventoagenda) {
        this.eventoagenda = eventoagenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitaPK != null ? visitaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visita)) {
            return false;
        }
        Visita other = (Visita) object;
        if ((this.visitaPK == null && other.visitaPK != null) || (this.visitaPK != null && !this.visitaPK.equals(other.visitaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Visita[ visitaPK=" + visitaPK + " ]";
    }
    
}
