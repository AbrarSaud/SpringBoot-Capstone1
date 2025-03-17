package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
        }

        return ResponseEntity.status(500).body(new ApiResponse("Unexpected error!"));
    }

    // Updating IN Controller
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);

        if (isUpdated) {
            return ResponseEntity.ok(new ApiResponse("Merchant stock updated successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant stock not found!"));
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
}
