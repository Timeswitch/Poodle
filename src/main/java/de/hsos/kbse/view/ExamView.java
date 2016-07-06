package de.hsos.kbse.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.service.AuthentificationService;
import de.hsos.kbse.view.MyUI;

import javax.ejb.EJB;
import javax.persistence.*;

/**
 * Created by jan on 06.07.2016.
 */

@CDIView("exams")
public class ExamView extends CustomComponent implements View{

    private Navigator nav;

    @EJB
    private AuthentificationService authentificationService;

    @Override
    public void enter(ViewChangeEvent event) {

        nav = getUI().getNavigator();

        Label header = new Label("Prüfungen");
        header.setStyleName("h1");

        Button logoutButton = new Button("Abmelden");
        logoutButton.addClickListener((ClickListener) event1 -> this.onLogoutClick());

        Table datatable = new Table("Prüfungen");

        datatable.addContainerProperty("Name", String.class, null);
        datatable.addContainerProperty("Dozent", String.class, null);
        datatable.addContainerProperty("Datum", String.class, null);
        datatable.addContainerProperty("Uhrzeit", String.class, null);

        datatable.addItem(new Object[]{"OOAD", "Rossmann", "28.7.16", "14:00"},1);
        datatable.addItem(new Object[]{"KBSE", "Rossmann", "27.7.16", "13:00"},2);
        datatable.addItem(new Object[]{"Grundlagen Programmieren", "Rossmann", "26.7.16", "12:00"},3);
        datatable.addItem(new Object[]{"Fortgeschrittene Programmierung", "Rossmann", "25.7.16", "11:00"},4);

        datatable.setPageLength(datatable.size());

        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(header);
        layout.addComponent(datatable);
        layout.addComponent(logoutButton);
    }

    protected void onLogoutClick(){
        this.authentificationService.logout();
        nav.navigateTo("login");
    }
}
