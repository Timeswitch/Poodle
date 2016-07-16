package de.hsos.kbse.poodle.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.poodle.backend.service.AuthentificationService;

import javax.ejb.EJB;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;

@CDIView("login")
@DesignRoot
public class LoginView extends VerticalLayout implements View{


    @EJB
    protected AuthentificationService authentificationService;

    protected TextField usernameField;
    protected PasswordField passwordField;
    protected Button loginButton;
    protected Button registerButton;

    protected Navigator nav;

    public LoginView(){
        Design.read(this);

        this.loginButton.addClickListener((Button.ClickListener) event1 -> this.onLoginClick());
        this.loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        this.registerButton.addClickListener((Button.ClickListener) event12 -> this.onRegisterClick());
    }

    @Override
    public void enter(ViewChangeEvent event) {
        nav = getUI().getNavigator();

        if(this.authentificationService.isLoggedIn()){
            this.navigateToDashboard();
        }
    }


    protected void onLoginClick(){

        if("".equals(this.usernameField.getValue())){
            this.usernameField.setComponentError(new UserError("Geben Sie einen Benutzernamen ein!"));
        }else{
            if(this.authentificationService.authenticate(this.usernameField.getValue(),this.passwordField.getValue())){
                this.navigateToDashboard();
                return;

            }else{
                UserError error = new UserError("Falscher Benutzername/Passwort");
                this.usernameField.setComponentError(error);
                this.passwordField.setComponentError(error);
            }
        }

        Notification.show("Überprüfen Sie Ihre Eingaben.", Notification.Type.TRAY_NOTIFICATION);
    }

    protected void onRegisterClick(){

        nav.navigateTo("register");
    }

    protected void navigateToDashboard(){
        switch(this.authentificationService.getCurrentUser().getRole()){
            case PROFESSOR:
                nav.navigateTo("admin");
                break;
            case STUDENT:
                nav.navigateTo("student");
                break;
        }
    }
}
