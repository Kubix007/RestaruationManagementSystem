package com.example.zpo_projekt.window;

import com.example.zpo_projekt.layout.FoodProductLayout;
import com.example.zpo_projekt.model.FoodProduct;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
public class NewFoodProductWindow extends Window {

    @Autowired
    FoodProductLayout productLayout;

    @Autowired
    EditFoodProductWindow editFoodProductWindow;

    private final Label titleLabel = new Label("Dodaj nowy produkt");
    private final TextField productNameTextField = new TextField("Nazwa produktu:");
    private final ComboBox<String> postfixComboBox = new ComboBox<>("Rodzaj:");
    private final Button addNewFoodProductButton = addNewFoodProductButton();

    @PostConstruct
    public void init(){
        postfixComboBox.setItems("szt.","kg","g");
        VerticalLayout verticalLayout = new VerticalLayout(titleLabel,productNameTextField,postfixComboBox,addNewFoodProductButton);
        verticalLayout.setComponentAlignment(addNewFoodProductButton,Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(titleLabel,Alignment.MIDDLE_CENTER);
        setContent(verticalLayout);
        setSettingsForWindow();
    }

    private void setSettingsForWindow(){
        setResizable(false);
        setDraggable(false);
        center();
    }

    private Button addNewFoodProductButton(){
        Button addNewFoodButton = new Button("Dodaj nowy produkt");
        addNewFoodButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addNewFoodButton.addClickListener(v->{
            if (!productNameTextField.isEmpty() && !postfixComboBox.isEmpty()){
                productLayout.addNewFoodProduct(new FoodProduct(productNameTextField.getValue(),postfixComboBox.getValue()));
                editFoodProductWindow.update();
                close();
                Notification.show("Pomyślnie dodano nowy produkt!",Notification.Type.HUMANIZED_MESSAGE);
            }
            else {
                Notification.show("Uzupełnij poprawnie dane!",Notification.Type.ERROR_MESSAGE);
            }

        });
        return addNewFoodButton;

    }
}
