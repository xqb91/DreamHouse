package cl.starlabs.modelo;

import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T04:52:56")
@StaticMetamodel(Propietario.class)
public class Propietario_ { 

    public static volatile SingularAttribute<Propietario, Integer> rut;
    public static volatile SingularAttribute<Propietario, Character> dv;
    public static volatile CollectionAttribute<Propietario, Propiedad> propiedadCollection;
    public static volatile SingularAttribute<Propietario, String> apaterno;
    public static volatile SingularAttribute<Propietario, Ciudad> ciudad;
    public static volatile SingularAttribute<Propietario, String> direccion;
    public static volatile SingularAttribute<Propietario, BigDecimal> numpropietario;
    public static volatile SingularAttribute<Propietario, String> amaterno;
    public static volatile SingularAttribute<Propietario, String> telefono;
    public static volatile SingularAttribute<Propietario, String> nombre;
    public static volatile SingularAttribute<Propietario, String> email;

}