package de.hsos.kbse.poodle.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.poodle.backend.model.Exam;
import de.hsos.kbse.poodle.backend.model.Slot;
import de.hsos.kbse.poodle.backend.model.Student;
import de.hsos.kbse.poodle.backend.service.SessionService;
import de.hsos.kbse.poodle.backend.service.StudentService;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;

@CDIView(value = "student/exam", supportsParameters = true)
@DesignRoot
@RolesAllowed({"STUDENT"})
public class StudentExamView extends VerticalLayout implements View{

    protected Table examTable;
    protected Button backButton;

    protected Navigator nav;

    protected Long examId;
    protected Student student;
    protected Exam exam;

    @EJB
    protected StudentService studentService;

    @Inject
    protected SessionService sessionService;

    public StudentExamView(){
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
        nav = getUI().getNavigator();

        this.student = (Student)this.sessionService.getCurrentUser();
        this.examId = Long.parseLong(event.getParameters());

        this.examTable.addContainerProperty("Datum",String.class,null);
        this.examTable.addContainerProperty("Uhrzeit",String.class,null);
        this.examTable.addContainerProperty("  ",Button.class,null);
        this.examTable.setEditable(false);
        this.examTable.setDragMode(Table.TableDragMode.NONE);
        this.examTable.setColumnCollapsingAllowed(false);
        this.examTable.setColumnReorderingAllowed(false);
        this.examTable.setColumnWidth("  ",125);

        this.backButton.addClickListener(e -> {
            this.nav.navigateTo("student");
        });

        this.refreshData(true);

    }

    protected void refreshData(boolean fetch){
        if(fetch){
            this.student = this.studentService.refresh(this.student);
        }

        this.exam = null;
        this.student.getExams().forEach(exam -> {
            if(exam.getId().equals(this.examId)){
                this.exam = exam;
            }
        });

        this.examTable.removeAllItems();
        if(this.exam != null){
            boolean isRegistered = this.student.hasSlotInExam(this.exam);
            this.exam.getSlots().forEach(slot -> {
                String date = slot.getDate().toLocalDate().toString();
                String time = slot.getTime().toLocalTime().toString();

                Button action = null;

                if(slot.getStudent() == null){
                    action = new Button("Eintragen");
                    action.addClickListener(e -> this.registerSlot(slot));

                    if(isRegistered){
                        action.setEnabled(false);
                    }
                }else if(slot.getStudent().equals(this.student)){
                    action = new Button("Austragen");
                    action.addClickListener(e -> this.freeSlot(slot));
                }else{
                    action = new Button("Belegt");
                    action.setEnabled(false);
                }

                this.examTable.addItem(new Object[]{date,time,action},null);
            });
        }else{
            this.nav.navigateTo("student");
            Notification.show("Fehler","Prüfung konnte nicht gefunden werden.", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    protected void registerSlot(Slot s){
        try{
            this.student = this.studentService.registerSlot(this.student,s);
        }catch (Exception e){
            Notification.show("Fehler","Der Slot ist bereits vergeben!", Notification.Type.TRAY_NOTIFICATION);
        }

        this.refreshData(false);
    }

    protected void freeSlot(Slot s){
        this.student = this.studentService.freeSlot(this.student,s);
        this.refreshData(false);
    }

}
