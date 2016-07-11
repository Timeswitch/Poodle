package de.hsos.kbse.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.service.ExamService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by jan on 07.07.2016.
 */

@CDIView(value = "admin/edit", supportsParameters = true)
@RolesAllowed({"PROFESSOR"})
public class AdminEditView extends CustomComponent implements View{

    @EJB
    private ExamService examService;

    private Navigator nav;

    private Button abortButton;
    private Button addButton;
    private PopupDateField date;
    private ComboBox name;

    private Exam exam;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.nav = this.getUI().getNavigator();

        this.exam = this.examService.findExam(Long.parseLong(event.getParameters()));


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);

        Label header = new Label("Prüfung bearbeiten");
        header.setStyleName("h1");
        verticalLayout.addComponent(header);

        //tabsheet
        TabSheet tabsheet = new TabSheet();
        verticalLayout.addComponent(tabsheet);

        //tab1
        VerticalLayout examTab = new VerticalLayout();
        examTab.setSpacing(true);

        tabsheet.addTab(examTab, "Prüfungen");

        Table examTable = new Table();
        examTab.addComponent(examTable);

        this.date = new PopupDateField();
        this.date.setValue(new Date());
        this.date.setImmediate(true);
        this.date.setDateFormat("dd.MM.yyyy hh:mm");
        this.date.setResolution(Resolution.MINUTE);

        this.addButton = new Button("Hinzufügen");
        this.addButton.setStyleName("primary");
        this.addButton.addClickListener(event2 -> this.onAddClick());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponent(this.date);
        horizontalLayout.addComponent(this.addButton);
        examTab.addComponent(horizontalLayout);

        //tab2
        VerticalLayout studentTab = new VerticalLayout();
        studentTab.setSpacing(true);
        tabsheet.addTab(studentTab, "Studenten");

        Table studentTable = new Table();
        studentTab.addComponent(studentTable);

        this.name = new ComboBox("Name");
        studentTab.addComponent(this.name);

        setCompositionRoot(verticalLayout);
    }

    private void onAddClick(){

    }

}
