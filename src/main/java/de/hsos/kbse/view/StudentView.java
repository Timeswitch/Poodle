package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.service.AuthentificationService;
import de.hsos.kbse.view.MyUI;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import eu.maxschuster.vaadin.autocompletetextfield.shared.ScrollBehavior;

import javax.ejb.EJB;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by jan on 06.07.2016.
 */

@CDIView("student")
@DesignRoot
public class StudentView extends CustomComponent implements View{

    private Navigator nav;

    private Table examTable;

    private Table slotTable;

    private Button logoutButton;

    @EJB
    private AuthentificationService authentificationService;

    /*public StudentView(){
        Design.read(this);
    }*/

    @Override
    public void enter(ViewChangeEvent event) {

        this.nav = getUI().getNavigator();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);

        Label header = new Label("Angemeldete Prüfungen");
        header.addStyleName("h1");
        verticalLayout.addComponent(header);

        //tabsheet
        TabSheet tabsheet = new TabSheet();
        verticalLayout.addComponent(tabsheet);

        //tab1
        VerticalLayout examTab = new VerticalLayout();
        examTab.setSpacing(true);

        tabsheet.addTab(examTab, "Prüfungen");

        this.examTable = new Table();


        this.examTable.setSizeFull();

        examTab.addComponent(examTable);


        //tab2
        VerticalLayout studentTab = new VerticalLayout();
        studentTab.setSpacing(true);
        tabsheet.addTab(studentTab, "Termine");

        this.slotTable = new Table();

        this.slotTable.setSizeFull();
        studentTab.addComponent(this.slotTable);

        this.logoutButton = new Button("Logout");
        setCompositionRoot(verticalLayout);

    }

    protected void onLogoutClick(){
        this.authentificationService.logout();
        nav.navigateTo("login");
    }
}
