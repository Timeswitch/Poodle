package de.hsos.kbse.view;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.service.AuthentificationService;
import de.hsos.kbse.backend.service.SessionService;

/**
 * Created by jan on 06.07.2016.
 */

@CDIView("login")
@DesignRoot
public class LoginView extends VerticalLayout implements View{


    @EJB
    AuthentificationService authentificationService;

    @Inject
    SessionService sessionService;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;

    private Navigator nav;

    public LoginView(){
        Design.read(this);

        this.loginButton.addClickListener((Button.ClickListener) event1 -> this.onLoginClick());
        this.registerButton.addClickListener((Button.ClickListener) event12 -> this.onRegisterClick());
    }

    @Override
    public void enter(ViewChangeEvent event) {
        nav = getUI().getNavigator();

        ((MyUI) UI.getCurrent()).getButtonLogout().setVisible(false);
    }


    private void onLoginClick(){

        if(this.authentificationService.authenticate(this.usernameField.getValue(),this.passwordField.getValue())){

            switch(this.sessionService.getCurrentUser().getRole()){
                case PROFESSOR:
                    nav.navigateTo("admin");
                    break;
                case STUDENT:
                    nav.navigateTo("student");
                    break;
            }
        }
    }

    private void onRegisterClick(){

        nav.navigateTo("register");
    }
}
