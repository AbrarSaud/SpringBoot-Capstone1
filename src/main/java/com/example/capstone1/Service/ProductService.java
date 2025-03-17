package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private ArrayList<Product> products = new ArrayList<>();
    private final CategoryService categoryService;


    //  Get All Products IN Service
    public ArrayList<Product> getAllProducts() {
        return products;
    }

    // Add Product IN Service
    public int addProduct(Product product) {
        ArrayList<Category> categories = categoryService.getAllCategories();
        boolean categoryExists = false;
        for (Category c : categories) {
            if (c.getId().equals(product.getCategoryID())) {
                categoryExists = true;
                break;
            }
        }
        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                return 1; // Product ID already exists!
            }
        }
        if (!categoryExists) {
            return 2; // Invalid Category ID!
        }
        products.add(product);
        return 200; // Product added successfully!
    }

    // Updating IN Service
    public boolean updateProduct(String  id, Product product) {
        System.out.println("Updating product with ID: " + id);
        System.out.println("Current products list: " + products);
        for (int i = 0; i < products.size(); i++) {
            System.out.println("Product found! Updating...");
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }
    // Deleting IN Service
    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
