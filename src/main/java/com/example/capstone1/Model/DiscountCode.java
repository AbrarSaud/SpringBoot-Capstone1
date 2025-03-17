package com.example.capstone1.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountCode {
    private String code;
    private double discountPercentage;
}