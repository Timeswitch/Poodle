package de.hsos.kbse.backend.Repository;

import de.hsos.kbse.backend.model.User;

import javax.persistence.TypedQuery;

/**
 * Created by michael on 06/07/16.
 */
public class GenericUserRepository<T extends User> extends GenericRepository<T> {

    public GenericUserRepository(Class<T> type){
        super(type);
    }

    public T findByEmail(String email){
        TypedQuery<T> query = this.em.createNamedQuery("User.findByEmail",this.type);
        return query.getSingleResult();
    }
}
