package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;

    // Getting IN Service
    public ArrayList<User> getAllUsers() {
        return users;
    }


    // Adding IN Service
    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    // Updating IN Service
    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    // Deleting IN Service
    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    // #1( first ) idea of extra endpoints (addToLike IN Service)
    public int addToLike(String id, String productId) {
        User user = null;
        for (User u : users) {
            if (u.getId().equals(id)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return 2;
        }

        boolean productExists = false;
        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productId)) {
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            return 3;
        }
        System.out.println("Liked products: " + user.getLikedProducts());
        if (user.getLikedProducts() == null) {
            user.setLikedProducts(new ArrayList<>());
        }

        if (user.getLikedProducts().contains(productId)) {
            return 4;
        }

        user.getLikedProducts().add(productId);
        System.out.println("Liked products: " + user.getLikedProducts());

        return 200;
    }


}
