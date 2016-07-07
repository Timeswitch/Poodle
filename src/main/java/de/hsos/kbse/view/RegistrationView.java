package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.model.Professor;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.model.User;
import de.hsos.kbse.backend.service.AuthentificationService;

import javax.inject.Inject;


/**
 * Created by jan on 06.07.2016.
 */

@CDIView("register")
@DesignRoot
public class RegistrationView extends VerticalLayout implements View{


    @Inject
    private AuthentificationService authentificationService;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField passwordConfirmField;
    private Button registerButton;
    private Button abortButton;
    private NativeSelect dropdown;

    private Navigator nav;

    @Override
    public void enter(ViewChangeEvent event){


        nav = getUI().getNavigator();
        this.registerButton.addClickListener((Button.ClickListener) event1 -> this.onRegisterClick());
        this.abortButton.addClickListener((Button.ClickListener) event1 -> this.onAbortClick());

        this.dropdown.addItems("Student", "Professor");
    }

    private void onRegisterClick(){



        nav.navigateTo("login");
    }

    private void onAbortClick(){

        nav.navigateTo("login");
    }
}
