package de.hsos.kbse.poodle.backend.repository;

import de.hsos.kbse.poodle.backend.model.User;

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
