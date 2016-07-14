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
import de.hsos.kbse.backend.service.SessionService;
import de.hsos.kbse.backend.service.StudentService;
import de.hsos.kbse.view.MyUI;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import eu.maxschuster.vaadin.autocompletetextfield.shared.ScrollBehavior;

import javax.ejb.EJB;
import javax.inject.Inject;
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

    @Inject
    private SessionService sessionService;

    @EJB
    private StudentService studentService;

    private Student student;

    /*public StudentView(){
        Design.read(this);
    }*/

    @Override
    public void enter(ViewChangeEvent event) {

        this.nav = getUI().getNavigator();

        this.student = (Student)this.sessionService.getCurrentUser();

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
        this.examTable.addContainerProperty("Prüfung",String.class,null);
        this.examTable.addContainerProperty("Professor",String.class,null);

        this.examTable.setSizeFull();
        this.examTable.setSelectable(true);
        this.examTable.setImmediate(true);
        this.examTable.addValueChangeListener(e -> this.onExamTableClick());

        examTab.addComponent(examTable);


        //tab2
        VerticalLayout studentTab = new VerticalLayout();
        studentTab.setSpacing(true);
        tabsheet.addTab(studentTab, "Termine");

        this.slotTable = new Table();
        this.slotTable.addContainerProperty("Prüfung",String.class,null);
        this.slotTable.addContainerProperty("Datum",String.class,null);
        this.slotTable.addContainerProperty("Zeit",String.class,null);
        this.slotTable.addContainerProperty("",Button.class,null);

        this.slotTable.setSizeFull();
        studentTab.addComponent(this.slotTable);

        this.logoutButton = new Button("Logout");
        setCompositionRoot(verticalLayout);

        this.refreshData(true);

    }

    protected void refreshData(boolean fetch){
        if(fetch){
            this.student = this.studentService.refresh(this.student);
        }

        this.examTable.removeAllItems();
        this.student.getExams().forEach(exam -> {
            String name = exam.getName();
            String prof = exam.getProfessor().getEmail();

            this.examTable.addItem(new Object[]{name,prof},exam.getId());

        });

        this.slotTable.removeAllItems();
        this.student.getSlots().forEach(slot -> {
            String exam = slot.getExam().getName();
            String date = slot.getDate().toLocalDate().toString();
            String time = slot.getTime().toLocalTime().toString();
            Button unregister = new Button("Austragen");

            unregister.addClickListener(event -> {
                this.student = this.studentService.freeSlot(this.student,slot);
                this.refreshData(false);
            });

            this.slotTable.addItem(new Object[]{exam,date,time,unregister},null);
        });

    }

    protected void onExamTableClick(){
        Long id = (Long)this.examTable.getValue();

        this.nav.navigateTo("student/exam/"+id);
    }

    protected void onLogoutClick(){
//        this.authentificationService.logout();
        nav.navigateTo("login");
    }
}
