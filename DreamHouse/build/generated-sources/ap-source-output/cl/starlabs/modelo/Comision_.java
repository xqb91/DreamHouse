package cl.starlabs.modelo;

import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-24T23:52:10")
@StaticMetamodel(Comision.class)
public class Comision_ { 

    public static volatile SingularAttribute<Comision, Date> fecha;
    public static volatile SingularAttribute<Comision, Empleado> empleado;
    public static volatile SingularAttribute<Comision, Double> comision;
    public static volatile SingularAttribute<Comision, Propiedad> propiedad;
    public static volatile SingularAttribute<Comision, BigDecimal> id;

}