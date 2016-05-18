package cl.starlabs.modelo;

import cl.starlabs.modelo.Region;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-06T21:52:31")
@StaticMetamodel(Pais.class)
public class Pais_ { 

    public static volatile CollectionAttribute<Pais, Region> regionCollection;
    public static volatile SingularAttribute<Pais, BigDecimal> idpais;
    public static volatile SingularAttribute<Pais, String> nombre;

}