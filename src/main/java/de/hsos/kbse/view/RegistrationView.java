package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.model.Professor;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.model.User;
import de.hsos.kbse.backend.security.Role;
import de.hsos.kbse.backend.service.AuthentificationService;

import javax.ejb.EJB;
import javax.inject.Inject;


/**
 * Created by jan on 06.07.2016.
 */

@CDIView("register")
@DesignRoot
public class RegistrationView extends VerticalLayout implements View{

    @EJB
    private AuthentificationService authentificationService;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField passwordConfirmField;
    private Button registerButton;
    private Button abortButton;
    private NativeSelect dropdown;

    private Navigator nav;

    RegistrationView(){
        Design.read(this);

        this.registerButton.addClickListener((Button.ClickListener) e -> this.onRegisterClick());
        this.abortButton.addClickListener((Button.ClickListener) e -> this.onAbortClick());

        this.usernameField.addValidator(new EmailValidator("Benutzername muss eine Email sein!"));

        this.passwordField.addValidator(new StringLengthValidator("Das Passwort muss mindestens 5 Zeichen lang sein!",5,255,false));
        this.passwordField.addValidator(value -> {
            if(!value.equals(this.passwordConfirmField.getValue())){
                throw new Validator.InvalidValueException("Passwörter müssen übereinstimmen!");
            }
        });

        this.passwordConfirmField.addValidator(value -> {
            try{
                this.passwordField.validate();
                this.passwordField.setComponentError(null);
            }catch (Validator.InvalidValueException e){
                this.passwordField.setComponentError(new UserError(e.getHtmlMessage()));
            }
        });
    }

    @Override
    public void enter(ViewChangeEvent event){
        nav = getUI().getNavigator();
    }

    private void onRegisterClick(){

        if(this.validate()){
            Role role = Role.valueOf(((String)this.dropdown.getValue()).toUpperCase());
            if(this.authentificationService.register(this.usernameField.getValue(),this.passwordField.getValue(), role)){
                nav.navigateTo("login");
            }
        }
    }

    private void onAbortClick(){

        nav.navigateTo("login");
    }

    private boolean validate(){

        try{
            this.usernameField.validate();
            this.passwordField.validate();

            return true;
        }catch (Validator.InvalidValueException e){
            Notification.show("Bitte überprüfen Sie Ihre Eingaben.", Notification.Type.TRAY_NOTIFICATION);
        }

        return false;
    }
}
