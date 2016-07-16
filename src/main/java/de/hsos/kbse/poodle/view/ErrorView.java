package de.hsos.kbse.poodle.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/**
 * Created by michael on 15/07/16.
 */
@CDIView("error")
@DesignRoot
public class ErrorView extends VerticalLayout implements View{

    Label error;
    Label message;

    public ErrorView(){
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
