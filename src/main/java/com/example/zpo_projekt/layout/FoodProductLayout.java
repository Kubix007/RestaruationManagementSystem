package com.example.zpo_projekt.layout;

import com.example.zpo_projekt.controller.FoodProductController;
import com.example.zpo_projekt.model.FoodProduct;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
@UIScope
public class FoodProductLayout extends VerticalLayout {

    @Autowired
    private FoodProductController foodProductController;

    @PostConstruct
    void init(){
        update();
    }

    public void update(){
        setFoodProducts(foodProductController.getAll());
    }

    private void setFoodProducts(List<FoodProduct> foodProductList){
        removeAllComponents();
        foodProductList.forEach(foodProduct->addComponent(new FoodProductItemLayout(foodProduct)));
    }

    public void addNewFoodProduct(FoodProduct foodProduct) {
        foodProductController.createFoodProduct(foodProduct);
        update();
    }

    public void deleteFoodProduct(FoodProduct foodProduct){
        foodProductController.deleteFoodProduct(foodProduct.getProductName());
        update();
    }

    public void updateFoodProduct(FoodProduct foodProduct, String amount){
        foodProductController.updateFoodProduct(foodProduct,amount);
        update();
    }
}
