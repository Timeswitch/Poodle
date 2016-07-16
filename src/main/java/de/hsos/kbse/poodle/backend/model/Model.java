package de.hsos.kbse.poodle.backend.model;

import java.io.Serializable;

/**
 * Created by michael on 26/06/16.
 */

public abstract class Model implements Serializable, Cloneable {

    public abstract Object getId();

    @Override
    public boolean equals(Object obj) {
        if(this.getId() == null || obj == null || !obj.getClass().equals(this.getClass())){
            return false;
        }else{
            return this.getId().equals(((Model)obj).getId());
        }
    }
}
