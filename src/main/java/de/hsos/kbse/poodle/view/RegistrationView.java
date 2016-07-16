package de.hsos.kbse.poodle.view;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.poodle.backend.security.Role;
import de.hsos.kbse.poodle.backend.service.AuthentificationService;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import javax.ejb.EJB;

@CDIView("register")
@DesignRoot
public class RegistrationView extends VerticalLayout implements View{

    @EJB
    protected AuthentificationService authentificationService;

    protected TextField usernameField;
    protected PasswordField passwordField;
    protected PasswordField passwordConfirmField;
    protected Button registerButton;
    protected Button abortButton;
    protected NativeSelect dropdown;

    protected Navigator nav;

    RegistrationView(){
        Design.read(this);
        
        this.registerButton.addClickListener((Button.ClickListener) e -> this.onRegisterClick());
        this.abortButton.addClickListener((Button.ClickListener) e -> this.onAbortClick());

        this.usernameField.setRequired(true);
        this.usernameField.addValidator(new EmailValidator("Benutzername muss eine Email sein!"));

        this.passwordField.setRequired(true);
        this.passwordField.addValidator(new StringLengthValidator("Das Passwort muss mindestens 5 Zeichen lang sein!",5,255,false));
        this.passwordField.addValidator(value -> {
            if(!value.equals(this.passwordConfirmField.getValue())){
                throw new Validator.InvalidValueException("Passwörter müssen übereinstimmen!");
            }
        });

        this.passwordConfirmField.setRequired(true);
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

    protected void onRegisterClick(){

        if(this.validate()){
            Role role = Role.valueOf(((String)this.dropdown.getValue()).toUpperCase());
            if(this.authentificationService.register(this.usernameField.getValue(),this.passwordField.getValue(), role)){
                nav.navigateTo("login");
            }else{
                this.usernameField.setComponentError(new UserError("Benutzername bereits vorhanden."));
            }
        }
    }

    protected void onAbortClick(){

        nav.navigateTo("login");
    }

    protected boolean validate(){

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
