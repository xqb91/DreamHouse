package cl.starlabs.modelo;

import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.VisitaPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T04:52:56")
@StaticMetamodel(Visita.class)
public class Visita_ { 

    public static volatile SingularAttribute<Visita, VisitaPK> visitaPK;
    public static volatile SingularAttribute<Visita, String> comentario;
    public static volatile SingularAttribute<Visita, Agenda> eventoagenda;

}