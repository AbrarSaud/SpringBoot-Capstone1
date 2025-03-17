package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
            return 2;
        }
        products.add(product);
        return 200;
    }

    // Updating IN Service
    public int updateProduct(String id, Product product) {
        boolean categoryExists = false;
        for (Category c : categoryService.getAllCategories()) {
            if (c.getId().equals(product.getCategoryID())) {
                categoryExists = true;
                break;
            }
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return 200;
            }
        }
        if (!categoryExists) {
            return 2;
        }
        return 1;
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
