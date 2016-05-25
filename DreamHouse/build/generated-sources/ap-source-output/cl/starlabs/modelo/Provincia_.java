package cl.starlabs.modelo;

import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Region;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-24T23:52:10")
@StaticMetamodel(Provincia.class)
public class Provincia_ { 

    public static volatile CollectionAttribute<Provincia, Ciudad> ciudadCollection;
    public static volatile SingularAttribute<Provincia, BigDecimal> idprovincia;
    public static volatile SingularAttribute<Provincia, Region> region;
    public static volatile SingularAttribute<Provincia, String> nombre;

}