package de.hsos.kbse.view;

import javax.inject.Inject;


import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import de.hsos.kbse.backend.Repository.StudentRepository;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.view.MyUI;

/**
 * Created by jan on 06.07.2016.
 */

@CDIView("login")
public class LoginView extends CustomComponent implements View, ClickListener{


    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private VerticalLayout layout;

    private Navigator nav;

    @Override
    public void enter(ViewChangeEvent event) {

        nav = getUI().getNavigator();

        this.usernameField = new TextField("Username");
        this.passwordField = new PasswordField("Password");
        this.loginButton = new Button("Login");
        this.loginButton.addClickListener(this);
        this.loginButton.setClickShortcut(KeyCode.ENTER);

        this.layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(this.usernameField);
        layout.addComponent(this.passwordField);
        layout.addComponent(this.loginButton);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // Dummy implementation



        /*if (this.nav != null) {
            this.nav.navigateTo("hello");
        }*/
    }
}
