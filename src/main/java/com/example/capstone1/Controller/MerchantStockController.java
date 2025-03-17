package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/souky-store/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    // Getting IN Controller
    @GetMapping("/get")
    public ResponseEntity<?> getAllMerchantStocks() {
        return ResponseEntity.ok(merchantStockService.getAllMerchantStocks());
    }

    // Adding IN Controller
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = merchantStockService.addMerchantStock(merchantStock);

        if (result == 200) {
            return ResponseEntity.ok(new ApiResponse("Merchant stock added successfully!"));
        } else if (result == 1) {
            return ResponseEntity.status(404).body(new ApiResponse("Merchant stock ID already exists!"));
        } else if (result == 2) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Merchant ID!"));
        } else if (result == 3) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Product ID!"));
        }

        return ResponseEntity.status(500).body(new ApiResponse("error!"));
    }

    // Updating IN Controller
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }

        int result = merchantStockService.updateMerchantStock(id, merchantStock);

        if (result == 200) {
            return ResponseEntity.ok(new ApiResponse("Merchant stock updated successfully!"));
        } else if (result == 1) {
            return ResponseEntity.status(404).body(new ApiResponse("Merchant stock not found!"));
        } else if (result == 2) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Merchant ID!"));
        } else if (result == 3) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Product ID!"));
        }

        return ResponseEntity.status(500).body(new ApiResponse("Error!"));
    }

    // Deleting IN Controller
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id) {
        boolean idDeleted = merchantStockService.deleteMerchantStock(id);
        if (idDeleted) {
            return ResponseEntity.ok(new ApiResponse("Merchant stock deleted successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant stock not found!"));
    }

    // 11- Create endpoint where  merchant can add more stocks of product to a merchant Stock
    @PostMapping("/add-stock")
    public ResponseEntity<?> addMoreMerchantStocks(@RequestParam String id, @RequestParam String merchantId,@RequestParam String productId, @RequestParam int amount) {
        int result = merchantStockService.addMoreMerchantStocks(id,merchantId, productId, amount);

        if (result == 200) {
            return ResponseEntity.ok().body(new ApiResponse("Merchant stock updated successfully!"));
        } else if (result == 1) {
            return ResponseEntity.status(404).body(new ApiResponse("Merchant stock not found!"));
        } else if (result == 2) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Merchant ID!"));
        } else if (result == 3) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Product ID!"));
        }
        return ResponseEntity.status(500).body(new ApiResponse("Error!"));

    }
    // 12- Create endpoint where user can buy a product directly
    @PostMapping("/buy-product")
    public ResponseEntity<?> buyProduct(@RequestParam String merchantId,
                                        @RequestParam String productId,
                                        @RequestParam String userId) {
        int result = merchantStockService.buyProduct(merchantId, productId, userId);

        if (result == 200) {
            return ResponseEntity.ok().body(new ApiResponse("Product purchased successfully!"));
        } else if (result == 2) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Merchant ID!"));
        } else if (result == 3) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid Product ID!"));
        } else if (result == 4) {
            return ResponseEntity.status(404).body(new ApiResponse("Invalid User ID!"));
        } else if (result == 5) {
            return ResponseEntity.status(400).body(new ApiResponse("Product is out of stock!"));
        } else if (result == 6) {
            return ResponseEntity.status(400).body(new ApiResponse("Insufficient balance!"));
        }
        return ResponseEntity.status(500).body(new ApiResponse("Error!"));
    }
}
