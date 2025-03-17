package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/souky-store/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    // Getting IN Controller
    @GetMapping("/get")
    public ResponseEntity<?> getAllMerchants() {
        return ResponseEntity.ok().body(merchantService.getAllMerchants());

    }

    // Adding IN Controller
    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        boolean isAdded = merchantService.addMerchant(merchant);
        if (isAdded) {
            return ResponseEntity.ok(new ApiResponse("Merchant added successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant ID already exists!"));
    }

    // Updating IN Controller
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if (isUpdated) {
            return ResponseEntity.ok(new ApiResponse("Merchant updated successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant not found!"));
    }

    // Deleting IN Controller
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse("Merchant deleted successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant not found!"));
    }
}
