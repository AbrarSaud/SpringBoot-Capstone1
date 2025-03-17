package com.example.capstone1.Model;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "The ID must not be empty")
    private String id;


    @NotEmpty(message = "The Product ID must not be empty")
    private String productID;

    @NotEmpty(message = "The  Merchant ID must not be empty")
    private String merchantID;

    @NotNull(message = "The stock must not be empty")
    @Max(value = 100, message = "The 'Stock' be more than 10 ")
    @Min(value = 10, message = "The 'Stock' be more than 10")
    private int stock;

}
