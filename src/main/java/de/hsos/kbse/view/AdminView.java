package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.service.AuthentificationService;
import de.hsos.kbse.backend.service.ExamService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import java.util.Collection;

/**
 * Created by michael on 07/07/16.
 */
@CDIView("admin")
@DesignRoot
@RolesAllowed({"PROFESSOR"})
public class AdminView extends VerticalLayout implements View {

    @EJB
    ExamService examService;

    Navigator nav;

    Table examTable;
    Button newButton;
    TextField nameField;

    public AdminView(){
        Design.read(this);

        this.examTable.setSelectable(true);
        this.examTable.setImmediate(true);
        this.examTable.addValueChangeListener(event -> this.onTableClick());

        this.newButton.addClickListener(event -> this.onNewButtonClick());

        this.nameField.setRequired(true);
        this.nameField.setRequiredError("Bitte geben Sie einen Namen ein");

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.nav = this.getUI().getNavigator();

        this.examTable.addContainerProperty("Name", String.class, null);
        this.examTable.addContainerProperty("", CssLayout.class, null);

        Collection<Exam> exams = this.examService.findExams();

        for (Exam exam : exams) {
            this.examTable.addItem(new Object[]{exam.getName(),this.createButton(exam)}, null);
        }

    }

    public CssLayout createButton(Exam exam){
        CssLayout layout = new CssLayout();

        Button edit = new Button("Bearbeiten");
        Button delete = new Button("Löschen");

        edit.addClickListener(event -> {
            this.nav.navigateTo("edit/"+exam.getId());
        });

        edit.addClickListener(event->{
            //this.examService
        });

        layout.addComponent(edit);
        return layout;
    }

    public void onTableClick(){
        Notification.show(this.examTable.getValue().toString());
    }

    public void onNewButtonClick(){
        Exam e = this.examService.createExam(this.nameField.getValue());
        this.examTable.addItem(new Object[]{e.getName()},null);
        this.nameField.setValue("");
    }
}
