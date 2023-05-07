package com.ati.main.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.UnknownFormatConversionException;

@Getter
 @Setter
public class ResourceNotFound extends RuntimeException {

     String resource ;
     String id;
     Integer argid;

     public ResourceNotFound(String entity, String id, Integer entityid) {

         super(String.format("%s not found with id = %s : %s" , entity ,id, entityid ));
         this.resource=entity;
         this.id=id;
         this.argid=entityid;

    }
}
