package com.utp.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-04T09:17:26")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, String> apellidos;
    public static volatile SingularAttribute<Persona, Integer> codigo;
    public static volatile SingularAttribute<Persona, Date> fechaNacimiento;
    public static volatile SingularAttribute<Persona, String> sexo;
    public static volatile SingularAttribute<Persona, String> nombres;

}