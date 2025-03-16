package com.example.capstone1.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "The ID must not be empty")
    private String id;


    @NotEmpty(message = "The Product ID must not be empty")
    private String productID;

    @NotEmpty(message = "The  Merchant ID must not be empty")
    private String merchantID;

    @NotEmpty(message = "The stock must not be empty")
    @Size(min = 10, max = 100, message = "The 'Stock' be more than 10 ")
    private String stock;


}
