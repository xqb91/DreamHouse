package cl.starlabs.modelo;

import cl.starlabs.modelo.Pais;
import cl.starlabs.modelo.Provincia;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T04:52:56")
@StaticMetamodel(Region.class)
public class Region_ { 

    public static volatile CollectionAttribute<Region, Provincia> provinciaCollection;
    public static volatile SingularAttribute<Region, BigDecimal> idregion;
    public static volatile SingularAttribute<Region, String> nombre;
    public static volatile SingularAttribute<Region, Pais> pais;

}