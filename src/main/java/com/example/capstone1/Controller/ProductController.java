package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/souky-store/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // Get All Products
    @GetMapping("/get")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Adding IN Controller
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(errorMessage));
        }

        int result = productService.addProduct(product);
        if (result == 200) {
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully!"));
        } else if (result == 1) {
            return ResponseEntity.status(404).body(new ApiResponse("Product ID already exists!"));
        } else if (result == 2) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Category ID!"));
        }

        return ResponseEntity.status(500).body(new ApiResponse("error!"));
    }

    // Updating IN Controller
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String messageError = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(messageError));
        }
        boolean isUpdated = productService.updateProduct(id, product);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Product not found"));
    }

    // Deleting IN Controller
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Product not found"));
    }
}
