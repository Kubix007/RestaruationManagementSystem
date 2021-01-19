package com.example.zpo_projekt.ui;

import com.example.zpo_projekt.WebSecurityConfig;
import com.example.zpo_projekt.controller.AppUserController;
import com.example.zpo_projekt.layout.PostLayout;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Title("ZPO")
@SpringUI(path = "/adminLogged")
public class AdminLogged extends UI {

    @Autowired
    PostLayout postLayout;

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
        menu.addMenuItem("Lista pracowników", VaadinIcons.USER,() -> {
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.addComponent(setAppUserGrid());
            menu.setContent(verticalLayout);
        });
        menu.addMenuItem("Dodaj pracownika", VaadinIcons.PLUS,() ->{
            VerticalLayout verticalLayout = addNewUserLayout();
            menu.setContent(verticalLayout);
        });
        menu.addMenuItem("Lista zadań", VaadinIcons.TASKS, () ->{
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.addComponent(postLayout);
            menu.setContent(verticalLayout);
        });
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

    private Grid<AppUser> setAppUserGrid(){
        Grid<AppUser> appUserGrid = new Grid<>("Lista pracowników:");
        appUserGrid.setItems(appUserController.getAll());
        appUserGrid.removeAllColumns();
        appUserGrid.addColumn(AppUser::getName).setCaption("Imię:");
        appUserGrid.addColumn(AppUser::getSurname).setCaption("Nazwisko:");
        appUserGrid.addColumn(AppUser::getUsername).setCaption("Login:");
        appUserGrid.setSizeFull();

        appUserGrid.addComponentColumn(item ->{
            Button removeUserButton = new Button("Usuń");
            removeUserButton.addClickListener(v->{
               appUserController.deleteAppUser(item.getId());
               appUserGrid.setItems(appUserController.getAll());
               Notification.show("Użytkownik został usunięty", Notification.Type.HUMANIZED_MESSAGE);

            });
            return removeUserButton;
        }).setWidth(110);
        return appUserGrid;
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

    private VerticalLayout addNewUserLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Label newUserLabel = new Label("Dodaj użytkownika");
        TextField loginTextField = new TextField("Nazwa użytkownika:");
        PasswordField passwordField = new PasswordField("Tymczasowe hasło:");
        ComboBox<String> roleComboBox = new ComboBox<>("Rola użytkownika:");
        roleComboBox.setItems("USER","ADMIN");
        Button buttonAddUser = new Button("Dodaj użytkownika");

        buttonAddUser.addClickListener(v->{
            String login = loginTextField.getValue();
            String password = passwordField.getValue();
            String role = roleComboBox.getValue();
            if (!loginTextField.isEmpty() && !passwordField.isEmpty() && !roleComboBox.isEmpty()){
                AppUser appUser = new AppUser(login,webSecurityConfig.passwordEncoder().encode(password),role);
                appUserController.createAppUser(appUser);
                loginTextField.setValue("");
                passwordField.setValue("");
                roleComboBox.setValue("");
                Notification.show("Pomyślnie zarejestrowano użytkownika", Notification.Type.HUMANIZED_MESSAGE);

            }
            else {
                Notification.show("Uzupełnij właściwie wszystkie pola", Notification.Type.ERROR_MESSAGE);
            }

        });
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        verticalLayout.addComponents(newUserLabel,loginTextField,passwordField,roleComboBox,buttonAddUser);

        return verticalLayout;
    }


}
