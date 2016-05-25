package cl.starlabs.modelo;

import cl.starlabs.modelo.Empleado;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-24T23:52:10")
@StaticMetamodel(Cargo.class)
public class Cargo_ { 

    public static volatile CollectionAttribute<Cargo, Empleado> empleadoCollection;
    public static volatile SingularAttribute<Cargo, BigDecimal> id;
    public static volatile SingularAttribute<Cargo, String> nombre;

}