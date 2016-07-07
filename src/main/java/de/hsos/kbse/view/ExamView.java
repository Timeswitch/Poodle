package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.service.AuthentificationService;
import de.hsos.kbse.view.MyUI;

import javax.ejb.EJB;
import javax.persistence.*;

/**
 * Created by jan on 06.07.2016.
 */

@CDIView("exams")
@DesignRoot
public class ExamView extends VerticalLayout implements View{

    private Navigator nav;

    private Table datatable;

    private Button logoutButton;

    @EJB
    private AuthentificationService authentificationService;

    public ExamView(){
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeEvent event) {

        nav = getUI().getNavigator();

        datatable.addContainerProperty("Name", String.class, null);
        datatable.addContainerProperty("Dozent", String.class, null);
        datatable.addContainerProperty("Datum", String.class, null);
        datatable.addContainerProperty("Uhrzeit", String.class, null);

        datatable.addItem(new Object[]{"OOAD", "Rossmann", "28.7.16", "14:00"},1);
        datatable.addItem(new Object[]{"KBSE", "Rossmann", "27.7.16", "13:00"},2);
        datatable.addItem(new Object[]{"Grundlagen Programmieren", "Rossmann", "26.7.16", "12:00"},3);
        datatable.addItem(new Object[]{"Fortgeschrittene Programmierung", "Rossmann", "25.7.16", "11:00"},4);

        datatable.setPageLength(datatable.size());

        this.logoutButton.addClickListener((Button.ClickListener) event1 -> this.onLogoutClick());

    }

    protected void onLogoutClick(){
        this.authentificationService.logout();
        nav.navigateTo("login");
    }
}
