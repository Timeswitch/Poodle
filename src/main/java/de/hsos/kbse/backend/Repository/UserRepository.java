package de.hsos.kbse.backend.Repository;

import de.hsos.kbse.backend.model.User;

import javax.ejb.Stateless;

/**
 * Created by michael on 06/07/16.
 */
@Stateless
public class UserRepository extends GenericUserRepository<User>{

    public UserRepository() {
        super(User.class);
    }
}
