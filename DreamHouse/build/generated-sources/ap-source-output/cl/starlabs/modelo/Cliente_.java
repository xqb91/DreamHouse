package cl.starlabs.modelo;

import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Arriendo;
import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Historialbusqueda;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-06T21:52:31")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Double> maxrent;
    public static volatile CollectionAttribute<Cliente, Agenda> agendaCollection;
    public static volatile SingularAttribute<Cliente, String> direccion;
    public static volatile SingularAttribute<Cliente, String> nombre;
    public static volatile SingularAttribute<Cliente, Integer> rut;
    public static volatile SingularAttribute<Cliente, BigInteger> tipopref;
    public static volatile SingularAttribute<Cliente, Character> dv;
    public static volatile SingularAttribute<Cliente, BigDecimal> numcliente;
    public static volatile SingularAttribute<Cliente, String> apaterno;
    public static volatile SingularAttribute<Cliente, Ciudad> ciudad;
    public static volatile CollectionAttribute<Cliente, Arriendo> arriendoCollection;
    public static volatile SingularAttribute<Cliente, String> amaterno;
    public static volatile SingularAttribute<Cliente, String> telefono;
    public static volatile CollectionAttribute<Cliente, Historialbusqueda> historialbusquedaCollection;
    public static volatile SingularAttribute<Cliente, String> email;

}