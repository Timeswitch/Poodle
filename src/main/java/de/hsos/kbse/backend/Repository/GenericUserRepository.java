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
        TypedQuery<User> query = this.em.createNamedQuery("User.findByEmail",User.class);
        query.setParameter("email", email);
        query.setParameter("type",this.getType());
        List<User> res = query.getResultList();

        if(!res.isEmpty()){
            return (T)res.get(0);
        }
        return null;
    }

    public Collection<T> search(String query){
        TypedQuery<User> q = this.em.createNamedQuery("User.search",User.class);
        q.setParameter("query", "%" + query + "%");
        q.setParameter("type",this.getType());
        Collection<T> res = (Collection<T>) ((Collection<?>) q.getResultList());

        return res;
    }

    public Role getType(){
        DiscriminatorValue dv = this.type.getAnnotation(DiscriminatorValue.class);
        Role type = null;
        if(dv != null){
            type = Role.valueOf(dv.value());
        }

        return type;
    }
}
