package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/souky-store/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Get All Categories IN Controller
    @GetMapping("/get")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    // Add Category IN Controller
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String messageError = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(messageError));
        }
        boolean isAdded = categoryService.addCategory(category);
        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Category added successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Cannot add category, ID is taken!"));
    }

    // Updating IN Controller
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String messageError = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(messageError));
        }
        boolean isUpdated = categoryService.updateCategory(id, category);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("NOT found"));
    }

    // Deleting IN Controller
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("NOT found"));
    }
}
