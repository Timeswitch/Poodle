package de.hsos.kbse.backend.repository;

import de.hsos.kbse.backend.model.User;
import de.hsos.kbse.backend.security.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.TypedQuery;

import java.util.Collection;
import java.util.List;

/**
 * Created by michael on 06/07/16.
 */
public class GenericUserRepository<T extends User> extends GenericRepository<T> {

    public GenericUserRepository(Class<T> type){
        super(type);
    }

    public T findByEmail(String email){
        TypedQuery<T> query = this.em.createNamedQuery("User.findByEmail",this.type);
        query.setParameter("email", email);
        List<T> res = query.getResultList();

        if(!res.isEmpty()){
            return res.get(0);
        }
        return null;
    }

    public Collection<T> search(String query){

        Role type = Role.valueOf(this.type.getAnnotation(DiscriminatorValue.class).value());

        TypedQuery<User> q = this.em.createNamedQuery("User.search",User.class);
        q.setParameter("query", "%" + query + "%");
        q.setParameter("type", type);
        Collection<T> res = (Collection<T>) ((Collection<?>) q.getResultList());

        return res;
    }
}
