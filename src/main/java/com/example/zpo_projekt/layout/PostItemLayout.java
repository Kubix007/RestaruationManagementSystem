package com.example.zpo_projekt.layout;

import com.example.zpo_projekt.model.Post;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class PostItemLayout extends VerticalLayout {

    private final TextArea content;
    private final TextField author;
    private final TextField data;

    public PostItemLayout(Post post) {
        this.content = new TextArea();
        this.author = new TextField();
        this.data = new TextField();

        setSettingsForComponents();

        addComponents(author,data,content);

        Binder<Post> binder = new Binder<>(Post.class);
        binder.bindInstanceFields(this);
        binder.setBean(post);
    }

    void setSettingsForComponents(){
        setSizeUndefined();
        setStyleName(ValoTheme.LAYOUT_WELL);
        content.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        content.setReadOnly(true);
        author.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS,ValoTheme.TEXTFIELD_HUGE);
        author.setReadOnly(true);
        data.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS,ValoTheme.TEXTFIELD_LARGE);
        data.setReadOnly(true);
    }
}
