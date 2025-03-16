package com.example.capstone1.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Primary;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "The ID must not be empty")
    private String id;

    @NotEmpty(message = "The user name must not be empty")
    @Size(min = 5, max = 10, message = "The 'Name' be more than 5 length long")
    private String username;

    @NotEmpty(message = "The password must not be empty")
    @Size(min = 6, max = 10, message = "The 'password' be more than 5 length long")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$", message = "Password must contain at least one letter and one number")
    private String password;

    @NotEmpty(message = "The email must not be empty")
    @Email()
    private String email;

    @NotEmpty(message = "Role must not be empty")
    @Pattern(regexp = "Admin|Customer", message = "Role must be either 'Admin' or 'Customer'")
    private String role;

    @NotNull(message = "Balance must not be empty")
    @Positive()
    private double balance;

}
