package com.example.capstone1.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "The ID must not be empty")
    private String id;


    @NotEmpty(message = "The Name must not be empty")
    @Size(min = 3, max = 10, message = "The 'Name' be more than 3 length long")
    private String name;
}
