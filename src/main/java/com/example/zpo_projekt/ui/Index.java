package com.example.zpo_projekt.ui;

import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.vaadin.teemusa.sidemenu.SideMenu;

@Title("ZPO")
@SpringUI(path = "/index")
public class Index extends UI {

    private String menuCaption = "RESTAURACJA";
    private ThemeResource logo = new ThemeResource("images/logo.jpg");

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        SideMenu menu = new SideMenu();
        menu.setMenuCaption(menuCaption);
        menu.addMenuItem("Zaloguj się", VaadinIcons.USER,() -> {
            getPage().setLocation("/userLogged");
        });
        menu.addMenuItem("Zaloguj się jako administrator", VaadinIcons.LOCK, () ->{
            getPage().setLocation("/adminLogged");
        });
        setContent(menu);
    }

}
