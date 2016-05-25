package cl.starlabs.modelo;

import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-24T23:52:10")
@StaticMetamodel(Historialbusqueda.class)
public class Historialbusqueda_ { 

    public static volatile SingularAttribute<Historialbusqueda, Date> fecha;
    public static volatile SingularAttribute<Historialbusqueda, Cliente> cliente;
    public static volatile SingularAttribute<Historialbusqueda, Propiedad> propiedad;
    public static volatile SingularAttribute<Historialbusqueda, BigDecimal> id;

}