package de.hsos.kbse.backend.service;

import de.hsos.kbse.backend.model.User;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by michael on 06/07/16.
 */
@SessionScoped
public class SessionService implements Serializable{

    User currentUser = null;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isLoggedIn(){
        return this.currentUser != null;
    }
}
