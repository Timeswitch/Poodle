package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.model.Slot;
import de.hsos.kbse.backend.model.Student;
import de.hsos.kbse.backend.service.SessionService;
import de.hsos.kbse.backend.service.StudentService;

import javax.ejb.EJB;
import javax.inject.Inject;

/**
 * Created by jan on 14.07.2016.
 */

@CDIView(value = "student/exam", supportsParameters = true)
@DesignRoot
public class StudentExamView extends VerticalLayout implements View{

    private Table examTable;

    private Navigator nav;

    private Long examId;
    private Student student;
    private Exam exam;

    @EJB
    private StudentService studentService;

    @Inject
    private SessionService sessionService;

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
        this.examTable.addContainerProperty("",Button.class,null);

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
            this.exam.getSlots().forEach(slot -> {
                String date = slot.getDate().toLocalDate().toString();
                String time = slot.getTime().toLocalTime().toString();

                Button action = null;

                if(slot.getStudent() == null){
                    action = new Button("Eintragen");
                    action.addClickListener(e -> this.registerSlot(slot));
                }else if(slot.getStudent().equals(this.student)){
                    action = new Button("Austragen");
                    action.addClickListener(e -> this.freeSlot(slot));
                }else{
                    action = new Button("Belegt");
                    action.setEnabled(false);
                }

                this.examTable.addItem(new Object[]{date,time,action},null);
            });
        }
    }

    protected void registerSlot(Slot s){
        this.student = this.studentService.registerSlot(this.student,s);
        this.refreshData(false);
    }

    protected void freeSlot(Slot s){
        this.student = this.studentService.freeSlot(this.student,s);
        this.refreshData(false);
    }

}
