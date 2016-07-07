package de.hsos.kbse.backend.service;

import com.vaadin.cdi.access.JaasAccessControl;
import de.hsos.kbse.backend.repository.UserRepository;
import de.hsos.kbse.backend.model.User;
import de.hsos.kbse.backend.security.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletException;

/**
 * Created by michael on 06/07/16.
 */

@Stateless
public class AuthentificationService {

    @Inject
    SessionService sessionService;

    @Inject
    JaasAccessControl jaasAccessControl;

    @Inject
    UserRepository userRepository;

    public boolean authenticate(String email, String password){

        User u = this.userRepository.findByEmail(email);

        if(u != null){

            try {
                this.jaasAccessControl.logout();
                this.jaasAccessControl.login(email,password);
                this.sessionService.setCurrentUser(u);
                return true;
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void logout(){
        this.sessionService.setCurrentUser(null);

        try {
            this.jaasAccessControl.logout();
        }catch (ServletException e){
            //So oder so ist der User nicht mehr eingeloggt.
        }
    }

    public boolean register(String email, String password, Role role){

        User u = this.userRepository.findByEmail(email);

        if(u != null){
            return false;
        }

        u = User.make(role);

        u.setEmail(email);
        u.setPassword(password);

        this.userRepository.add(u);

        return true;

    }
}
