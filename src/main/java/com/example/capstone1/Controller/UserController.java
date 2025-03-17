package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/souky-store/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Getting IN Controller
    @GetMapping("/get")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    // Adding IN Controller
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        boolean isAdded = userService.addUser(user);

        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("User added successfully!"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("User ID already exists!"));
    }

    // Updating IN Controller
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        boolean isUpdated = userService.updateUser(id, user);

        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("User ID not found!"));
    }

    // Deleting IN Controller
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String id) {
        boolean isDeleted = userService.deleteUser(id);

        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("User ID not found!"));
    }

    // #1( first ) idea of extra endpoints (addToLike IN Service)
    @PostMapping("/add-to-like")
    public ResponseEntity<?> addToLike(@RequestParam String id, @RequestParam String productId) {
        int result = userService.addToLike(id, productId);

        if (result == 200) {
            return ResponseEntity.ok().body(new ApiResponse("Product added to liked list successfully!"));
        } else if (result == 2) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found!"));
        } else if (result == 3) {
            return ResponseEntity.status(404).body(new ApiResponse("Product not found!"));
        } else if (result == 4) {
            return ResponseEntity.status(400).body(new ApiResponse("Product already liked!"));
        }
        return ResponseEntity.status(500).body(new ApiResponse("Error!"));
    }

}
