package cl.starlabs.modelo;

import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Arriendo;
import cl.starlabs.modelo.Comision;
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Historialbusqueda;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Tipopropiedades;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-24T23:52:10")
@StaticMetamodel(Propiedad.class)
public class Propiedad_ { 

    public static volatile SingularAttribute<Propiedad, Tipopropiedades> tipo;
    public static volatile CollectionAttribute<Propiedad, Agenda> agendaCollection;
    public static volatile CollectionAttribute<Propiedad, Comision> comisionCollection;
    public static volatile SingularAttribute<Propiedad, String> calle;
    public static volatile SingularAttribute<Propiedad, Empleado> numempleado;
    public static volatile SingularAttribute<Propiedad, BigInteger> hab;
    public static volatile SingularAttribute<Propiedad, BigInteger> ciudad;
    public static volatile SingularAttribute<Propiedad, Propietario> numpropietario;
    public static volatile CollectionAttribute<Propiedad, Arriendo> arriendoCollection;
    public static volatile SingularAttribute<Propiedad, BigDecimal> numpropiedad;
    public static volatile CollectionAttribute<Propiedad, Historialbusqueda> historialbusquedaCollection;
    public static volatile SingularAttribute<Propiedad, Double> renta;
    public static volatile SingularAttribute<Propiedad, String> codigopostal;
    public static volatile SingularAttribute<Propiedad, Character> disponible;

}