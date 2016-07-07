package de.hsos.kbse.backend.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by michael on 26/06/16.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Model implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    public long getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.id == null || !obj.getClass().equals(this.getClass())){
            return false;
        }else{
            return this.id.equals(((Model)obj).getId());
        }
    }
}
