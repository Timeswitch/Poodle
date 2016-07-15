package de.hsos.kbse.view;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.cdi.CDIViewProvider;

import javax.inject.Inject;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("de.hsos.kbse.MyAppWidgetset")
@CDIUI("")
public class MyUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    private Button logout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout verticalLayout = new VerticalLayout();
        GridLayout headergrid = new GridLayout();
        VerticalLayout verticalLayout2 = new VerticalLayout();

        Label label = new Label("Poodle");
        label.addStyleName("h1");
        label.setSizeUndefined();

        this.logout = new Button("Logout");

        headergrid.setSpacing(true);

        headergrid.removeAllComponents();
        headergrid.setRows(1);
        headergrid.setColumns(3);
        headergrid.setSizeFull();
        headergrid.setColumnExpandRatio(1,1.0f);

        headergrid.addComponent(label,0,0);
        headergrid.addComponent(this.logout,2,0);
        headergrid.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
        headergrid.setComponentAlignment(this.logout, Alignment.MIDDLE_LEFT);
        headergrid.addStyleName("headerBackColor");

        verticalLayout.addComponent(headergrid);

        verticalLayout.addComponent(verticalLayout2);

        setContent(verticalLayout);

        Navigator navigator = new Navigator(this, verticalLayout2);
        navigator.addProvider(viewProvider);
        navigator.navigateTo("login");
    }

//    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
//    public static class MyUIServlet extends VaadinServlet {
//    }


    public Button getButtonLogout(){
        return this.logout;
    }
}
