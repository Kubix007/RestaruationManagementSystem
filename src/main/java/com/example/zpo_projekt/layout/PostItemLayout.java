package com.example.zpo_projekt.layout;

import com.example.zpo_projekt.model.Post;
import com.example.zpo_projekt.service.PostService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme("mytheme")
public class PostItemLayout extends VerticalLayout {

    private final TextArea content;
    private final TextField author;
    private final DateField data;
    private final CheckBox done;
    private final Long id;

    public PostItemLayout(Post post,Long id, PostService postService) {
        this.content = new TextArea("Treść:");
        this.author = new TextField("Dodał:");
        this.data = new DateField("Data dodania:");
        this.done = new CheckBox();
        this.id = id;

        addComponents(author,data,content,done);
        setSettingsForComponents();

        done.addValueChangeListener(v->{
            if (done.getValue()){
                addStyleName("backColor");

            }
            else {
                setStyleName(ValoTheme.LAYOUT_WELL);
            }
            postService.updateDonePost(id,done.getValue());
        });



        Binder<Post> binder = new Binder<>(Post.class);
        binder.bindInstanceFields(this);
        binder.setBean(post);
    }

    void setSettingsForComponents(){
        setSizeUndefined();
        addStyleName(ValoTheme.LAYOUT_WELL);
        content.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        content.setReadOnly(true);
        author.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS,ValoTheme.TEXTFIELD_HUGE);
        author.setReadOnly(true);
        data.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS,ValoTheme.TEXTFIELD_LARGE);
        data.setReadOnly(true);
        setComponentAlignment(done,Alignment.MIDDLE_CENTER);
    }
}
