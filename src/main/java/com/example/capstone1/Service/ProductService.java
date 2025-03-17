package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    private ArrayList<Product> products = new ArrayList<>();
    private CategoryService categoryService;

    //  Get All Products IN Service
    public ArrayList<Product> getAllProducts() {
        return products;
    }

    // Add Product IN Service
    // Add Product IN Service
    public int addProduct(Product product) {
        ArrayList<Category> categories = categoryService.getAllCategories();

        if (categories == null) {
            return 3; // Category is null
        }

        boolean categoryExists = false;
        for (Category c : categories) {
            if (c.getId().equals(product.getCategoryID())) {
                categoryExists = true;
                break;
            }
        }

        if (!categoryExists) {
            return 2; // Invalid Category ID!
        }

        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                return 1; // Product ID already exists!
            }
        }

        products.add(product);
        return 200; // Product added successfully!
    }

    // Updating IN Service
    public boolean updateProduct(int id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    // Deleting IN Service
    public boolean deleteProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
