package de.hsos.kbse.backend.service;

import de.hsos.kbse.backend.repository.UserRepository;
import de.hsos.kbse.backend.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by michael on 06/07/16.
 */

@Stateless
public class AuthentificationService {

    @Inject
    SessionService sessionService;

    @Inject
    UserRepository userRepository;

    public boolean authenticate(String email, String password){

        User u = this.userRepository.findByEmail(email);

        if(u.getPassword().equals(password)){
            sessionService.setCurrentUser(u);
            return true;
        }

        return false;
    }
}
