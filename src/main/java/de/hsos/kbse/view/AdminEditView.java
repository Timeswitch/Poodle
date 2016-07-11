package de.hsos.kbse.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by jan on 07.07.2016.
 */

@CDIView("admin/edit")
@RolesAllowed({"PROFESSOR"})
public class AdminEditView extends CustomComponent implements View{

    private Button abortButton;
    private Button addButton;
    private InlineDateField date;


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        Label header = new Label("Slot hinzufügen");
        header.setStyleName("h1");

        abortButton = new Button("Abbrechen");
        abortButton.setStyleName("primary");
        abortButton.addClickListener((Button.ClickListener)event1 -> this.onAbortClick());

        addButton = new Button("Hinzufügen");
        addButton.setStyleName("primary");
        addButton.addClickListener((Button.ClickListener)event2 -> this.onAddClick());

        date = new InlineDateField();
        date.setValue(new Date());
        date.setImmediate(true);
        date.setTimeZone(TimeZone.getTimeZone("UTC"));
        date.setLocale(Locale.GERMAN);
        date.setResolution(Resolution.MINUTE);

        horizontalLayout.addComponent(addButton);
        horizontalLayout.addComponent(abortButton);

        verticalLayout.addComponent(header);
        verticalLayout.addComponent(date);
        verticalLayout.addComponent(horizontalLayout);

        setCompositionRoot(verticalLayout);
    }

    private void onAbortClick(){

    }

    private void onAddClick(){

    }
}
