package com.example.capstone1.Service;

import com.example.capstone1.Model.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserService {
    private ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private ArrayList<Map<String, String>> reviews = new ArrayList<>();
//    @Lazy
//    private final MerchantStockService merchantStockService;


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
        User user = getUserById(userId);
        if (user == null) {
            return 2;
        }

        boolean productExists = false;
        String nameProduct = "";
        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productId)) {
                nameProduct = p.getName();
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            return 3;
        }

        Map<String, String> review = new HashMap<>();
        review.put("userId", userId);
        review.put("productId", productId);
        review.put("nameProduct", nameProduct);
        review.put("rating", String.valueOf(rating));
        review.put("comment", comment);

        reviews.add(review);
        return 200;
    }

    // #3  idea (applyDiscountCode IN Service)
    public double applyDiscountCode(User user, String code, double totalAmount) {
        for (String[] discount : user.getDiscountCodes()) {
            if (discount[0].equals(code)) {
                double discountPercentage = Double.parseDouble(discount[1]);
                return totalAmount * (1 - discountPercentage);
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
        user.setBalance(user.getBalance() + product.getPrice());
        user.getPurchasedProducts().remove(productId);

//        MerchantStock stock = merchantStockService.findByProductId(productId);
//        if (stock != null) {
//            stock.setStock(stock.getStock() + 1);
//        }


        return 200;
    }


    public ArrayList<Map<String, String>> getReviews() {
        return reviews;
    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
