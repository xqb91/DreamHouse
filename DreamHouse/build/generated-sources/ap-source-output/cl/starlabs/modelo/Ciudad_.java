package cl.starlabs.modelo;

import cl.starlabs.modelo.Cliente;
import cl.starlabs.modelo.Oficina;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Provincia;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-20T10:48:01")
@StaticMetamodel(Ciudad.class)
public class Ciudad_ { 

    public static volatile CollectionAttribute<Ciudad, Oficina> oficinaCollection;
    public static volatile SingularAttribute<Ciudad, BigDecimal> idciudad;
    public static volatile SingularAttribute<Ciudad, Provincia> provincia;
    public static volatile SingularAttribute<Ciudad, String> nombre;
    public static volatile CollectionAttribute<Ciudad, Cliente> clienteCollection;
    public static volatile CollectionAttribute<Ciudad, Propietario> propietarioCollection;

}