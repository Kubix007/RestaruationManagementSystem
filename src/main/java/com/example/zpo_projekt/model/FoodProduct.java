package com.example.zpo_projekt.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FoodProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String amount;
    private String postfix;

    public FoodProduct() {
    }

    public FoodProduct(Long id, String productName, String amount, String postfix) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.postfix = postfix;
    }

    public FoodProduct(String productName, String postfix) {
        this.productName = productName;
        this.postfix = postfix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
}
