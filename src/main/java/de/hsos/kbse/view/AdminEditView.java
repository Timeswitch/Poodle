package de.hsos.kbse.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import de.hsos.kbse.backend.model.Exam;
import de.hsos.kbse.backend.model.Slot;
import de.hsos.kbse.backend.service.ExamService;
import de.hsos.kbse.backend.service.StudentService;
import de.hsos.kbse.view.utils.StudentAutocompleteSuggestionProvider;
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField;
import eu.maxschuster.vaadin.autocompletetextfield.shared.ScrollBehavior;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

/**
 * Created by jan on 07.07.2016.
 */

@CDIView(value = "admin/edit", supportsParameters = true)
@RolesAllowed({"PROFESSOR"})
public class AdminEditView extends CustomComponent implements View{

    @EJB
    private ExamService examService;

    @EJB
    private StudentService studentService;

    @Inject
    StudentAutocompleteSuggestionProvider studentAutocompleteSuggestionProvider;

    private Navigator nav;

    private Button abortButton;
    private Button addButton;
    private PopupDateField date;
    private AutocompleteTextField name;
    private Table slotTable;
    private Table studentTable;
    private Button addStudentButton;

    private Exam exam;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.nav = this.getUI().getNavigator();

        this.exam = this.examService.findExam(Long.parseLong(event.getParameters()));


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);

        Label header = new Label("\""+exam.getName()+"\" bearbeiten");
        header.setStyleName("h1");
        verticalLayout.addComponent(header);

        //tabsheet
        TabSheet tabsheet = new TabSheet();
        verticalLayout.addComponent(tabsheet);

        //tab1
        VerticalLayout examTab = new VerticalLayout();
        examTab.setSpacing(true);

        tabsheet.addTab(examTab, "Termine");

        Label slotLabel = new Label("Termin:");

        this.slotTable = new Table();
        this.slotTable.addContainerProperty("Datum",String.class,null);
        this.slotTable.addContainerProperty("Uhrzeit",String.class,null);
        this.slotTable.addContainerProperty("Student",String.class,null);
        this.slotTable.addContainerProperty("",Button.class,null);
        this.slotTable.setSizeFull();

        examTab.addComponent(slotTable);

        this.date = new PopupDateField();
        this.date.setValue(new Date());
        this.date.setImmediate(true);
        this.date.setDateFormat("dd.MM.yyy HH:mm");
        this.date.setResolution(Resolution.MINUTE);

        this.addButton = new Button("Hinzufügen");
        this.addButton.setStyleName("primary");
        this.addButton.addClickListener(event2 -> this.onAddClick());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponent(slotLabel);
        horizontalLayout.addComponent(this.date);
        horizontalLayout.addComponent(this.addButton);
        horizontalLayout.setComponentAlignment(slotLabel,Alignment.MIDDLE_LEFT);
        examTab.addComponent(horizontalLayout);

        //tab2
        VerticalLayout studentTab = new VerticalLayout();
        studentTab.setSpacing(true);
        tabsheet.addTab(studentTab, "Studenten");

        this.studentTable = new Table();
        this.studentTable.addContainerProperty("Name",String.class,null);
        this.studentTable.addContainerProperty("",Button.class,null);
        this.studentTable.setSizeFull();
        studentTab.addComponent(studentTable);

        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        horizontalLayout1.setSpacing(true);
        studentTab.addComponent(horizontalLayout1);

        Label studentLable = new Label("Student:");
        horizontalLayout1.addComponent(studentLable);
        horizontalLayout1.setComponentAlignment(studentLable,Alignment.MIDDLE_LEFT);

        this.name = new AutocompleteTextField();
        this.name.setCache(true);
        this.name.setDelay(150);
        this.name.setItemAsHtml(false);
        this.name.setMinChars(3);
        this.name.setScrollBehavior(ScrollBehavior.NONE);
        this.name.setSuggestionLimit(0);
        this.name.setSuggestionProvider(this.studentAutocompleteSuggestionProvider);
        horizontalLayout1.addComponent(this.name);

        this.addStudentButton = new Button("Hinzufügen");
        this.addStudentButton.addClickListener(event1 -> this.onAddStudentButtonClick());
        this.addStudentButton.setStyleName("primary");
        horizontalLayout1.addComponent(this.addStudentButton);

        setCompositionRoot(verticalLayout);

        this.refreshData(false);
    }

    private void refreshData(){
        this.refreshData(true);
    }

    private void refreshData(boolean fetch){
        this.examService.refresh(this.exam);

        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyy");

        this.slotTable.removeAllItems();
        this.exam.getSlots().forEach(slot -> {

            Button free = new Button("Freigeben");

            if(slot.getStudent() != null){
                free.addClickListener(event -> {
                    this.exam = this.examService.freeSlot(this.exam,slot);
                    this.refreshData(false);
                });
            }else {
                free.setEnabled(false);
            }

            String student = "FREI";

            if(slot.getStudent() != null){
                student = slot.getStudent().getEmail();
            }

            String date = formatDate.format(slot.getDate());
            this.slotTable.addItem(new Object[]{date,slot.getTime().toLocalTime().toString(),student, free},null);
        });

        this.studentTable.removeAllItems();
        this.exam.getStudents().forEach(student -> {
            Button remove = new Button("Entfernen");

            remove.addClickListener(event -> {
                this.exam = this.examService.removeStudent(this.exam,student);
                this.refreshData(false);
            });

            this.studentTable.addItem(new Object[]{student.getEmail(),remove},null);
        });
    }

    private void onAddClick(){
        Slot s = new Slot();

        Date inputDate = this.date.getValue();

        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);

        Long timestamp = calendar.getTimeInMillis();

        java.sql.Date date = new java.sql.Date(timestamp);
        Time time = new Time(timestamp);

        s.setTime(time);
        s.setDate(date);

        this.date.setValue(new Date());

        try{
            this.exam = this.examService.addSlot(this.exam,s);
            this.refreshData(false);
        }catch (Exception e){
            Notification.show("Fehler","Konnte Slot nicht anlegen", Notification.Type.TRAY_NOTIFICATION);
        }

    }

    private void onAddStudentButtonClick(){
        String email = this.name.getValue();
        if("".equals(email)){
            UserError error = new UserError("Geben Sie eine gültige Email ein!");
            this.name.setComponentError(error);
            Notification.show(error.getMessage());
            return;
        }

        try{
            this.exam = this.examService.addStudent(this.exam,email);
            this.refreshData(false);
            this.name.setValue("");
            this.name.setComponentError(null);
        }catch(Exception e){
            UserError error = new UserError("Student wurde nicht gefunden.");
            this.name.setComponentError(error);

            Notification.show(error.getMessage());
        }
    }

}
