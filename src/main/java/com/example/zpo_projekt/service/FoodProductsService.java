package com.example.zpo_projekt.service;

import com.example.zpo_projekt.model.FoodProduct;
import com.example.zpo_projekt.repository.FoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FoodProductsService {

    @Autowired
    private FoodProductRepository foodProductRepository;

    public List<FoodProduct> getAll() {
        return foodProductRepository.findAll();
    }

    public FoodProduct getSingleFoodProduct(String productName) {
        return foodProductRepository.findByProductName(productName);

    }

    public FoodProduct createFoodProduct(FoodProduct foodProduct) {
        return foodProductRepository.save(foodProduct);
    }

    @Transactional
    public void deleteFoodProduct(String productName) {
        foodProductRepository.deleteByProductName(productName);
    }

    @Transactional
    public FoodProduct updateFoodProduct(FoodProduct foodProduct, String amount) {
        FoodProduct foodProductEdited = foodProductRepository.findByProductName(foodProduct.getProductName());
        foodProductEdited.setAmount(amount);
        return foodProductEdited;

    }
}
