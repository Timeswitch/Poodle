package de.hsos.kbse.poodle;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.cdi.CDIViewProvider;
import de.hsos.kbse.poodle.backend.service.AuthentificationService;
import de.hsos.kbse.poodle.view.ErrorView;
import de.hsos.kbse.poodle.component.PageHeaderComponent;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.Locale;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("de.hsos.kbse.PoodleWidgetset")
@CDIUI("")
@PreserveOnRefresh
public class PoodleUI extends UI {

    @EJB
    protected AuthentificationService authentificationService;

    @Inject
    protected CDIViewProvider viewProvider;

    protected Navigator nav;

    protected VerticalLayout uiLayout;
    protected VerticalLayout contentLayout;
    protected PageHeaderComponent pageHeader;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        this.getSession().setLocale(Locale.GERMANY );

        this.uiLayout = new VerticalLayout();
        this.contentLayout = new VerticalLayout();

        this.pageHeader = new PageHeaderComponent();
        this.pageHeader.addLogoutListener(event -> this.onLogoutClick());

        this.uiLayout.addComponent(this.pageHeader);
        this.uiLayout.addComponent(this.contentLayout);

        setContent(this.uiLayout);

        this.nav = new Navigator(this, this.contentLayout);
        this.nav.addProvider(viewProvider);
        this.nav.setErrorView(ErrorView.class);
        this.nav.navigateTo("login");

        this.nav.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                refreshHeader();
            }
        });
    }

    public void refreshHeader(){
        if(this.authentificationService.isLoggedIn()){
            this.pageHeader.setUsername(this.authentificationService.getCurrentUser().getEmail());
            this.pageHeader.setShowToolPanel(true);
        }else{
            this.pageHeader.setShowToolPanel(false);
        }
    }

    public void onLogoutClick(){
        this.authentificationService.logout();
        this.nav.navigateTo("login");
    }
}
