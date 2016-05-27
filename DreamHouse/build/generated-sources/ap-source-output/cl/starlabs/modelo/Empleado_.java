package cl.starlabs.modelo;

import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Cargo;
import cl.starlabs.modelo.Comision;
import cl.starlabs.modelo.Oficina;
import cl.starlabs.modelo.Propiedad;
import cl.starlabs.modelo.Totpropempleado;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T04:52:56")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile CollectionAttribute<Empleado, Agenda> agendaCollection;
    public static volatile CollectionAttribute<Empleado, Comision> comisionCollection;
    public static volatile SingularAttribute<Empleado, Integer> numempleado;
    public static volatile SingularAttribute<Empleado, Double> salario;
    public static volatile SingularAttribute<Empleado, Oficina> numoficina;
    public static volatile SingularAttribute<Empleado, Totpropempleado> totpropempleado;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile CollectionAttribute<Empleado, Propiedad> propiedadCollection;
    public static volatile SingularAttribute<Empleado, String> apaterno;
    public static volatile SingularAttribute<Empleado, Date> fechnac;
    public static volatile SingularAttribute<Empleado, String> contrasena;
    public static volatile SingularAttribute<Empleado, BigDecimal> id;
    public static volatile SingularAttribute<Empleado, Double> porcentaje;
    public static volatile SingularAttribute<Empleado, String> amaterno;
    public static volatile SingularAttribute<Empleado, Character> sexo;
    public static volatile SingularAttribute<Empleado, Cargo> cargo;

}