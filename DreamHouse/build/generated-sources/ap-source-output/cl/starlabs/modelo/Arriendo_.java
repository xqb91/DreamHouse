package cl.starlabs.modelo;

import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Propiedad;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-20T10:48:01")
@StaticMetamodel(Arriendo.class)
public class Arriendo_ { 

    public static volatile SingularAttribute<Arriendo, BigDecimal> numarriendo;
    public static volatile SingularAttribute<Arriendo, Character> pagado;
    public static volatile SingularAttribute<Arriendo, Date> iniciorenta;
    public static volatile SingularAttribute<Arriendo, Cliente> numcliente;
    public static volatile SingularAttribute<Arriendo, Integer> numempleado;
    public static volatile SingularAttribute<Arriendo, Double> deposito;
    public static volatile SingularAttribute<Arriendo, Date> finrenta;
    public static volatile SingularAttribute<Arriendo, Propiedad> numpropiedad;
    public static volatile SingularAttribute<Arriendo, Double> renta;
    public static volatile SingularAttribute<Arriendo, String> formapago;

}