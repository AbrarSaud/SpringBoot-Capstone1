package com.example.capstone1.Service;


import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    private ArrayList<Category> categories = new ArrayList<>();

    // Get All Categories IN Service
    public ArrayList<Category> getAllCategories() {
        return categories;
    }

    // Add Category IN Service
    public boolean addCategory(Category category) {
        for (Category c : categories) {
            if (c.getId().equals(category.getId())) {
                return false;
            }
        }

        categories.add(category);
        return true;
    }

    // Update Category IN Service
    public boolean updateCategory(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    // Delete Category IN Service
    public boolean deleteCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
