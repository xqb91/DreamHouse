package cl.starlabs.modelo;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-20T10:48:01")
@StaticMetamodel(VisitaPK.class)
public class VisitaPK_ { 

    public static volatile SingularAttribute<VisitaPK, Date> fecha;
    public static volatile SingularAttribute<VisitaPK, BigInteger> numcliente;
    public static volatile SingularAttribute<VisitaPK, BigInteger> numpropiedad;

}