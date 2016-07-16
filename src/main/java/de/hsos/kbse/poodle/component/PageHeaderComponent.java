package de.hsos.kbse.poodle.component;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class PageHeaderComponent extends HorizontalLayout{

    protected Label titleLabel;

    protected HorizontalLayout toolLayout;
    protected Label userLabel;
    protected Button logoutButton;

    protected boolean showToolPanel = true;

    public PageHeaderComponent(){
        super();

        this.setWidth("100%");
        this.setSpacing(true);
        this.addStyleName("headerBackColor");
        this.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        this.setMargin(new MarginInfo(false,true,false,true));
        this.titleLabel = new Label("Poodle");
        this.titleLabel.addStyleName("h1");
        this.titleLabel.addStyleName("headerColor");
        this.addComponent(titleLabel);
        this.setExpandRatio(this.titleLabel,1.0f);

        this.toolLayout = new HorizontalLayout();
        this.toolLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        this.toolLayout.setSpacing(true);


        this.userLabel = new Label("");
        this.userLabel.addStyleName("headerColor");

        this.logoutButton = new Button("Logout");
        this.logoutButton.setStyleName("link");
        this.logoutButton.addStyleName("headerColor");

        this.toolLayout.addComponent(this.userLabel);
        this.toolLayout.addComponent(this.logoutButton);

        this.toolLayout.setSizeUndefined();

        this.addComponent(this.toolLayout);
    }

    public void addLogoutListener(Button.ClickListener ls){
        this.logoutButton.addClickListener(ls);
    }

    public void setUsername(String username){
        this.userLabel.setValue(username);
    }

    public void setShowToolPanel(boolean showToolPanel){
        if(showToolPanel && !this.showToolPanel){
            this.addComponent(this.toolLayout);
        }else if(!showToolPanel && this.showToolPanel){
            this.removeComponent(this.toolLayout);
        }

        this.showToolPanel = showToolPanel;
    }

}
