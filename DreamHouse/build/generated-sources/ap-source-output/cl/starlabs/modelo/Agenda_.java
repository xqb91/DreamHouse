package cl.starlabs.modelo;

import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Propiedad;
import cl.starlabs.modelo.Visita;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-06T21:52:31")
@StaticMetamodel(Agenda.class)
public class Agenda_ { 

    public static volatile SingularAttribute<Agenda, Cliente> cliente;
    public static volatile SingularAttribute<Agenda, Date> fechaAgendada;
    public static volatile SingularAttribute<Agenda, Empleado> empleado;
    public static volatile SingularAttribute<Agenda, Date> fechaAgendamiento;
    public static volatile SingularAttribute<Agenda, Character> realizada;
    public static volatile SingularAttribute<Agenda, Propiedad> propiedad;
    public static volatile SingularAttribute<Agenda, BigDecimal> id;
    public static volatile CollectionAttribute<Agenda, Visita> visitaCollection;

}