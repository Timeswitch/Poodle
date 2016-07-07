package de.hsos.kbse.view;

import javax.ejb.EJB;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.service.AuthentificationService;

/**
 * Created by jan on 06.07.2016.
 */

@CDIView("login")
@DesignRoot
public class LoginView extends VerticalLayout implements View{


    @EJB
    AuthentificationService authentificationService;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;

    private Navigator nav;

    public LoginView(){
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeEvent event) {

        nav = getUI().getNavigator();

        this.loginButton.addClickListener((Button.ClickListener) event1 -> this.onLoginClick());
        this.registerButton.addClickListener((Button.ClickListener) event12 -> this.onRegisterClick());
    }


    private void onLoginClick(){

        if(this.authentificationService.authenticate(this.usernameField.getValue(),this.passwordField.getValue())){
            nav.navigateTo("exams");
        }
    }

    private void onRegisterClick(){

        nav.navigateTo("register");
    }
}
