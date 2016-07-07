package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

import javax.annotation.security.RolesAllowed;

/**
 * Created by michael on 07/07/16.
 */
@CDIView("admin")
@DesignRoot
@RolesAllowed({"PROFESSOR"})
public class AdminView extends VerticalLayout implements View {

    public AdminView(){
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
