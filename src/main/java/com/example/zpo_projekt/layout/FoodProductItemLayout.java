package com.example.zpo_projekt.layout;

import com.example.zpo_projekt.model.FoodProduct;
import com.vaadin.data.Binder;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

public class FoodProductItemLayout extends HorizontalLayout {

    private final TextArea productName;
    private final TextArea amount;
    private final TextArea postfix;

    public FoodProductItemLayout(FoodProduct foodProduct) {
        this.productName = new TextArea("Nazwa produktu:");
        this.amount = new TextArea("Ilość:");
        this.postfix = new TextArea("Rodzaj:");

        addComponents(productName,amount,postfix);
        setSettingsForComponents();

        Binder<FoodProduct> binder = new Binder<>(FoodProduct.class);
        binder.bindInstanceFields(this);
        binder.setBean(foodProduct);

    }

    void setSettingsForComponents(){
        setHeight("60px");
        addStyleName(ValoTheme.LAYOUT_WELL);
        productName.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        productName.setReadOnly(true);
        amount.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS);
        amount.setReadOnly(true);
        postfix.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        postfix.setReadOnly(true);
    }
}
