package de.hsos.kbse.view;

import javax.ejb.EJB;
import com.vaadin.cdi.CDIView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import de.hsos.kbse.backend.service.AuthentificationService;

/**
 * Created by jan on 06.07.2016.
 */

@CDIView("login")
public class LoginView extends CustomComponent implements View{


    @EJB
    AuthentificationService authentificationService;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;

    private Navigator nav;

    @Override
    public void enter(ViewChangeEvent event) {

        nav = getUI().getNavigator();

        Label header = new Label("Anmelden");
        header.setStyleName("h1");

        this.usernameField = new TextField("Benutzername");
        this.passwordField = new PasswordField("Passwort");
        this.loginButton = new Button("Anmelden");
        this.loginButton.addClickListener(new Button.ClickListener(){
            public void buttonClick(ClickEvent event){

                nav.navigateTo("exams");
            }
        });

        this.registerButton = new Button("Registrieren");
        this.registerButton.addClickListener(new Button.ClickListener(){
            public void buttonClick(ClickEvent event){

                nav.navigateTo("register");
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
        layout.addComponent(this.loginButton);
        layout.addComponent(this.registerButton);
    }
}
