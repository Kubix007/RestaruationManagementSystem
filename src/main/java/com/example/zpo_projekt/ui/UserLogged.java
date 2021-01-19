package com.example.zpo_projekt.ui;

import com.example.zpo_projekt.WebSecurityConfig;
import com.example.zpo_projekt.controller.AppUserController;
import com.example.zpo_projekt.model.AppUser;
import com.example.zpo_projekt.repository.AppUserRepository;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.teemusa.sidemenu.SideMenu;

@Title("ZPO")
@SpringUI(path = "/userLogged")
public class UserLogged extends UI {

    @Autowired
    AppUserController appUserController;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        SideMenu menu = new SideMenu();
        menu.setMenuCaption("Restauracja");
       // menu.addMenuItem("Zaloguj się", VaadinIcons.USER,() -> {
       // });
        setUser(getCurrentUsername(),VaadinIcons.MALE,menu);
        setContent(menu);
    }

    private void setUser(String name, Resource icon, SideMenu sideMenu) {
        sideMenu.setUserName(name);
        sideMenu.setUserIcon(icon);
        sideMenu.clearUserMenu();

        sideMenu.addUserMenuItem("Ustawienia", VaadinIcons.WRENCH, () ->{
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.addComponents(setUserDetailsLayout(),setUserPasswordLayout());

            sideMenu.setContent(horizontalLayout);
        });

        sideMenu.addUserMenuItem("Wyloguj się", VaadinIcons.EXIT, () -> {
            Notification.show("Logging out..", Notification.Type.TRAY_NOTIFICATION);
            getPage().setLocation("/j_spring_security_logout");
        });
    }

    private String getCurrentUsername(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    private VerticalLayout setUserDetailsLayout(){
        VerticalLayout userDetailsLayout = new VerticalLayout();

        Label infoLabel = new Label("Dane osobowe:");
        TextField nameTextField = new TextField("Imię:");
        TextField surnameTextField = new TextField("Nazwisko:");
        Button saveUserDetailsButton = new Button("Zapisz");

        nameTextField.setPlaceholder(appUserController.getSingleAppUser(getCurrentUsername()).getName());
        surnameTextField.setPlaceholder(appUserController.getSingleAppUser(getCurrentUsername()).getSurname());

        saveUserDetailsButton.addClickListener(v->{
            if (!nameTextField.isEmpty() && !surnameTextField.isEmpty()){
                AppUser appUser = appUserController.getSingleAppUser(getCurrentUsername());
                appUserController.updateAppUser(appUser,nameTextField.getValue(),surnameTextField.getValue());
                Notification.show("Pomyślnie zaktualizowano!",Notification.Type.HUMANIZED_MESSAGE);
            }
            else {
                Notification.show("Uzupełnij wszystkie pola!",Notification.Type.ERROR_MESSAGE);

            }
        });

        userDetailsLayout.addComponents(infoLabel,nameTextField,surnameTextField,saveUserDetailsButton);
        return userDetailsLayout;
    }

    private VerticalLayout setUserPasswordLayout(){
        VerticalLayout userPasswordLayout = new VerticalLayout();

        Label infoLabel = new Label("Zmiana hasła:");
        PasswordField oldPasswordField = new PasswordField("Stare hasło:");
        PasswordField newPasswordField = new PasswordField("Nowe hasło:");
        Button saveUserPasswordButton = new Button("Zmień hasło");

        saveUserPasswordButton.addClickListener(v->{
            AppUser currentUser = appUserController.getSingleAppUser(getCurrentUsername());
            String oldPassword = oldPasswordField.getValue();
            boolean isPasswordMatch = webSecurityConfig.passwordEncoder().matches(oldPassword,currentUser.getPassword());
            if (isPasswordMatch){
                appUserRepository.updatePassword(currentUser.getUsername(),webSecurityConfig.passwordEncoder().encode(newPasswordField.getValue()));
                Notification.show("Pomyślnie zmieniono hasło",Notification.Type.HUMANIZED_MESSAGE);
            }
            else {
                Notification.show("Podałeś nieprawidłowe hasło",Notification.Type.ERROR_MESSAGE);
            }
        });
        userPasswordLayout.addComponents(infoLabel,oldPasswordField,newPasswordField,saveUserPasswordButton);

        return userPasswordLayout;
    }
}
