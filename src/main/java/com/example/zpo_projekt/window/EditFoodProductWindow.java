package com.example.zpo_projekt.window;


import com.example.zpo_projekt.controller.FoodProductController;
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
public class EditFoodProductWindow extends Window {

    @Autowired
    FoodProductLayout productLayout;

    @Autowired
    FoodProductController foodProductController;

    private final Label titleLabel = new Label("Edytuj ilość produktu");
    private final TextField amountTextField = new TextField("Ilość:");
    private final ComboBox<String> productName = new ComboBox<>("Nazwa produktu:");
    private final Button editFoodProductButton = editFoodProductButton();


    @PostConstruct
    public void init(){
        update();
        VerticalLayout verticalLayout = new VerticalLayout(titleLabel, productName, amountTextField,editFoodProductButton);
        verticalLayout.setComponentAlignment(editFoodProductButton,Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(titleLabel,Alignment.MIDDLE_CENTER);
        setContent(verticalLayout);
        setSettingsForWindow();
    }

    public void update(){
        productName.setItems(foodProductController.getAll().stream().map(FoodProduct::getProductName));
    }

    private void setSettingsForWindow(){
        setResizable(false);
        setDraggable(false);
        center();
    }

    private Button editFoodProductButton(){
        Button editFoodProductButton = new Button("Edytuj ilość");
        editFoodProductButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        editFoodProductButton.addClickListener(v->{
            if (!productName.isEmpty() && !amountTextField.isEmpty()){
                FoodProduct foodProductToEdit = foodProductController.getSingleFoodProduct(productName.getValue());
                productLayout.updateFoodProduct(foodProductToEdit,amountTextField.getValue());
                productLayout.update();
                close();
                Notification.show("Pomyślnie edytowano produkt", Notification.Type.HUMANIZED_MESSAGE);
            }
            else {
                Notification.show("Uzupełnij prawidłowo pola", Notification.Type.ERROR_MESSAGE);
            }


        });

        return editFoodProductButton;
    }
}
