package de.hsos.kbse.poodle.backend.repository;

import de.hsos.kbse.poodle.backend.model.User;

import javax.ejb.Stateless;

@Stateless
public class UserRepository extends GenericUserRepository<User>{

    public UserRepository() {
        super(User.class);
    }
}
