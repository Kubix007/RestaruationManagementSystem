package com.example.zpo_projekt.repository;

import com.example.zpo_projekt.model.FoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodProductRepository extends JpaRepository<FoodProduct, Long> {

    FoodProduct findByProductName(String productName);
    void deleteByProductName(String productName);

}
