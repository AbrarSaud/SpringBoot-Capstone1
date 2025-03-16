package com.example.capstone1.Controller;


import com.example.capstone1.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/souky-store/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Getting IN Controller
    // Adding IN Controller
    // Updating IN Controller
    // Deleting IN Controller
}
