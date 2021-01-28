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
public class DeleteFoodProductWindow extends Window {

    @Autowired
    FoodProductController foodProductController;

    @Autowired
    FoodProductLayout foodProductLayout;

    private final Label titleLabel = new Label("Usuń produkt");
    private final ComboBox<String> productName = new ComboBox<>("Nazwa produktu:");
    private final Button removeFoodProductButton = removeFoodProductButton();


    @PostConstruct
    public void init(){
        update();
        VerticalLayout verticalLayout = new VerticalLayout(titleLabel,productName,removeFoodProductButton);
        verticalLayout.setComponentAlignment(removeFoodProductButton,Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(titleLabel,Alignment.MIDDLE_CENTER);
        setContent(verticalLayout);
        setSettingsForWindow();
    }

    public void update(){
        productName.setItems(foodProductController.getAll().stream().map(FoodProduct::getProductName));

    }

    private Button removeFoodProductButton(){
        Button removeFoodProductButton = new Button("Usuń produkt");
        removeFoodProductButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        removeFoodProductButton.addClickListener(v->{
            if (!productName.isEmpty()){
                foodProductLayout.deleteFoodProduct(foodProductController.getSingleFoodProduct(productName.getValue()));
                close();
                foodProductLayout.update();
                update();
                productName.setValue("");
                Notification.show("Pomyślnie usunięto produkt", Notification.Type.HUMANIZED_MESSAGE);
            }
            else {
                Notification.show("Uzupełnij prawidłowo pola", Notification.Type.ERROR_MESSAGE);
            }

        });

        return removeFoodProductButton;
    }

    private void setSettingsForWindow(){
        setResizable(false);
        setDraggable(false);
        center();
    }
}
