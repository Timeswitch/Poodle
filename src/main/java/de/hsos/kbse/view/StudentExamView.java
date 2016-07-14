package de.hsos.kbse.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/**
 * Created by jan on 14.07.2016.
 */

@CDIView("student/exam")
@DesignRoot
public class StudentExamView extends VerticalLayout implements View{

    private Table examTable;

    private Navigator nav;

    public StudentExamView(){
        Design.read(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
        nav = getUI().getNavigator();


    }
}
