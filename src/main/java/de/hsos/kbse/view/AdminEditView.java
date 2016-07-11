package de.hsos.kbse.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
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
    private TextField name;

    private Exam exam;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.nav = this.getUI().getNavigator();

        this.exam = this.examService.findExam(Long.parseLong(event.getParameters()));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        horizontalLayout2.setSpacing(true);

        Label header = new Label(exam.getName());
        header.setStyleName("h1");

        this.name = new TextField("Name");

        this.abortButton = new Button("Abbrechen");
        this.abortButton.setStyleName("primary");
        this.abortButton.addClickListener((Button.ClickListener)event1 -> this.onAbortClick());

        this.addButton = new Button("Hinzufügen");
        this.addButton.setStyleName("primary");
        this.addButton.addClickListener((Button.ClickListener)event2 -> this.onAddClick());

        this.date = new PopupDateField("Termin");
        this.date.setValue(new Date());
        this.date.setImmediate(true);
        this.date.setDateFormat("dd.MM.yyyy mm:hh");
        this.date.setResolution(Resolution.MINUTE);

        horizontalLayout.addComponent(addButton);
        horizontalLayout.addComponent(abortButton);

        horizontalLayout2.addComponent(name);
        horizontalLayout2.addComponent(date);

        verticalLayout.addComponent(header);
        verticalLayout.addComponent(horizontalLayout2);
        verticalLayout.addComponent(horizontalLayout);

        setCompositionRoot(verticalLayout);
    }

    private void onAbortClick(){

    }

    private void onAddClick(){

    }
}
