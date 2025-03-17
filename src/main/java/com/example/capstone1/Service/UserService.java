package com.example.capstone1.Service;

import com.example.capstone1.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private final ArrayList<DiscountCode> discountCodes = new ArrayList<>();

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

    // #2  idea (addProductReview IN Service)
    public int addProductReview(String userId, String productId, int rating, String comment) {
        User user = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
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

        if (user.getReviews() == null) {
            user.setReviews(new ArrayList<>());
        }

        Review review = new Review(userId, productId, rating, comment);
        user.getReviews().add(review);

        return 200;
    }

    // #3  idea (applyDiscountCode IN Service)
    public double applyDiscountCode(String code, double totalAmount) {
        discountCodes.add(new DiscountCode("A10", 0.10));
        discountCodes.add(new DiscountCode("A20", 0.20));
        discountCodes.add(new DiscountCode("A50", 0.50));
        for (DiscountCode discount : discountCodes) {
            if (discount.getCode().equalsIgnoreCase(code)) {
                return totalAmount * (1 - discount.getDiscountPercentage());
            }
        }
        return -1;
    }

    // #4  idea (returnProduct IN Service)
    public int returnProduct(String userId, String productId) {
        User user = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return 2;
        }

        Product product = null;
        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) {
            return 3;
        }
        if (user.getPurchasedProducts() == null) {
            user.setPurchasedProducts(new ArrayList<>());
        }


        if (!user.getPurchasedProducts().contains(productId)) {
            return 4;
        }

        user.setBalance(user.getBalance() + product.getPrice());
        user.getPurchasedProducts().remove(productId);

        return 200;
    }


}
