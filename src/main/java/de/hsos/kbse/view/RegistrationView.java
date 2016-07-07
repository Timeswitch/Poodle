package de.hsos.kbse.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import de.hsos.kbse.backend.security.Role;
import de.hsos.kbse.backend.service.AuthentificationService;

import javax.ejb.EJB;


/**
 * Created by jan on 06.07.2016.
 */

@CDIView("register")
public class RegistrationView extends CustomComponent implements View{

    private TextField usernameField;
    private TextField passwordField;
    private Button registerButton;
    private Button abortButton;

    private Navigator nav;

    @EJB
    AuthentificationService authentificationService;

    @Override
    public void enter(ViewChangeEvent event){


        nav = getUI().getNavigator();

        Label header = new Label("Registrieren");
        header.setStyleName("h1");

        this.usernameField = new TextField("Benutzername");
        this.passwordField = new TextField("Passwort");
        this.registerButton = new Button("Registrieren");
        this.registerButton.addClickListener((Button.ClickListener) event1 -> this.onRegisterClick());

        this.abortButton = new Button("Abbrechen");
        this.abortButton.addClickListener(new Button.ClickListener(){
            public void buttonClick(ClickEvent event){
                nav.navigateTo("login");
            }
        });

        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(header);
        layout.addComponent(this.usernameField);
        layout.addComponent(this.passwordField);
        layout.addComponent(this.registerButton);
        layout.addComponent(this.abortButton);
    }

    public void onRegisterClick(){
        if(authentificationService.register(this.usernameField.getValue(),this.passwordField.getValue(), Role.STUDENT)){
            this.nav.navigateTo("login");
        }
    }

}
