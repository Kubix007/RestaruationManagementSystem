package com.example.zpo_projekt.window;


import com.example.zpo_projekt.controller.AppUserController;
import com.example.zpo_projekt.controller.PostController;
import com.example.zpo_projekt.layout.PostLayout;
import com.example.zpo_projekt.model.Post;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@UIScope
@SpringComponent
public class NewPostWindow extends Window {

    @Autowired
    PostLayout postLayout;

    @Autowired
    PostController postController;

    @Autowired
    AppUserController appUserController;

    private final Label titleLabel = new Label("Dodaj nowy post");
    private final TextArea contentTextArea = new TextArea("Treść:");

    @PostConstruct
    public void init(){
        FormLayout formLayout = new FormLayout(titleLabel,contentTextArea,addNewPostButton());
        setContent(formLayout);
        setSettingsForWindow();
    }

    private Button addNewPostButton(){
        Button addNewPostButton = new Button("Dodaj post");
        addNewPostButton.setStyleName(ValoTheme.BUTTON_PRIMARY);

        addNewPostButton.addClickListener(clickEvent->{
            postLayout.addNewPost(new Post(appUserController.getSingleAppUser(getCurrentUsername()).getName(),contentTextArea.getValue(),LocalDate.now(),false));
            close();
            Notification.show("Pomyślnie dodano nowy post!",Notification.Type.HUMANIZED_MESSAGE);
        });

        return addNewPostButton;
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

    private void setSettingsForWindow(){
        setResizable(false);
        setDraggable(false);
        setHeight("250px");
        setWidth("300px");
        center();
        contentTextArea.setMaxLength(255);
    }

}


