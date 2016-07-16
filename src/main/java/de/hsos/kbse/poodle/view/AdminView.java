package de.hsos.kbse.poodle.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.poodle.backend.model.Exam;
import de.hsos.kbse.poodle.backend.service.ExamService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import java.util.Collection;

@CDIView("admin")
@DesignRoot
@RolesAllowed({"PROFESSOR"})
public class AdminView extends VerticalLayout implements View {

    @EJB
    protected ExamService examService;

    protected Navigator nav;

    protected Table examTable;
    protected Button newButton;
    protected TextField nameField;

    public AdminView(){
        Design.read(this);

        this.newButton.addClickListener(event -> this.onNewButtonClick());

        this.nameField.setValidationVisible(false);
        this.nameField.addValidator(new StringLengthValidator("Geben Sie einen Namen zwischen 1 - 255 Zeichen ein!",1,255,false));

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.nav = this.getUI().getNavigator();

        this.examTable.addContainerProperty("Name", String.class, null);
        this.examTable.addContainerProperty("", CssLayout.class, null);
        this.examTable.setEditable(false);
        this.examTable.setDragMode(Table.TableDragMode.NONE);
        this.examTable.setColumnCollapsingAllowed(false);
        this.examTable.setColumnReorderingAllowed(false);

        this.refreshTable();

    }

    public CssLayout createButton(Exam exam){
        CssLayout layout = new CssLayout();

        Button edit = new Button("Bearbeiten");
        Button delete = new Button("Löschen");

        edit.addClickListener(event -> {
            this.nav.navigateTo("admin/edit/"+exam.getId());
            this.refreshTable();
        });

        delete.addClickListener(event->{
            this.examService.deleteExam(exam);
            this.refreshTable();
        });

        layout.addComponent(edit);
        layout.addComponent(delete);

        return layout;
    }

    public void refreshTable(){
        this.examTable.removeAllItems();

        Collection<Exam> exams = this.examService.findExams();

        for (Exam exam : exams) {
            this.examTable.addItem(new Object[]{exam.getName(),this.createButton(exam)}, null);
        }
    }

    public void onNewButtonClick(){

        try{
            this.nameField.validate();
            this.nameField.setComponentError(null);
        }catch (Validator.InvalidValueException e){
            this.nameField.setComponentError(new UserError(e.getHtmlMessage()));
            Notification.show(e.getHtmlMessage(), Notification.Type.TRAY_NOTIFICATION);
            return;
        }

        Exam e = this.examService.createExam(this.nameField.getValue());
        this.examTable.addItem(new Object[]{e.getName()},null);
        this.nameField.setValue("");

        this.refreshTable();
    }
}
