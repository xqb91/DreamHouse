package cl.starlabs.modelo;

import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Empleado;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-06T21:52:31")
@StaticMetamodel(Oficina.class)
public class Oficina_ { 

    public static volatile CollectionAttribute<Oficina, Empleado> empleadoCollection;
    public static volatile SingularAttribute<Oficina, Ciudad> ciudad;
    public static volatile SingularAttribute<Oficina, String> calle;
    public static volatile SingularAttribute<Oficina, Short> numoficina;
    public static volatile SingularAttribute<Oficina, BigDecimal> id;
    public static volatile SingularAttribute<Oficina, String> codigopostal;

}