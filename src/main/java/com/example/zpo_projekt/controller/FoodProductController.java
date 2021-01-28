package com.example.zpo_projekt.controller;


import com.example.zpo_projekt.model.FoodProduct;
import com.example.zpo_projekt.service.FoodProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class FoodProductController {

    @Autowired
    private FoodProductsService foodProductsService;

    @GetMapping("/api/foodproducts")
    public List<FoodProduct> getAll(){
        return foodProductsService.getAll();
    }

    @GetMapping("/api/foodproducts/{productName}")
    public FoodProduct getSingleFoodProduct(@PathVariable String productName){
        return foodProductsService.getSingleFoodProduct(productName);
    }

    @PostMapping("/api/foodproducts")
    public FoodProduct createFoodProduct(@RequestBody FoodProduct foodProduct){
        return foodProductsService.createFoodProduct(foodProduct);
    }

    @RequestMapping(value = "/api/foodproducts", produces = "application/json", method=RequestMethod.PUT)
    public FoodProduct updateFoodProduct(@RequestBody FoodProduct foodProduct, @RequestParam String amount){
        return foodProductsService.updateFoodProduct(foodProduct,amount);
    }

    @DeleteMapping("/api/foodproducts/{productName}")
    public void deleteFoodProduct(@PathVariable String productName){
        foodProductsService.deleteFoodProduct(productName);
    }

}
