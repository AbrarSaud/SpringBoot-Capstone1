package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class MerchantStockService {
    private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;
    private final UserService userService;


    // Getting IN Service
    public ArrayList<MerchantStock> getAllMerchantStocks() {
        return merchantStocks;
    }

    // Adding IN Service
    public int addMerchantStock(MerchantStock merchantStock) {
        boolean merchantExists = false;
        boolean productExists = false;
        for (Merchant m : merchantService.getAllMerchants()) {
            if (m.getId().equals(merchantStock.getMerchantID())) {
                merchantExists = true;
                break;
            }
        }


        if (!merchantExists) {
            return 2; // Invalid Merchant ID
        }
        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(merchantStock.getProductID())) {
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            return 3;
        }

        for (MerchantStock ms : merchantStocks) {
            if (ms.getId().equals(merchantStock.getId())) {
                return 1;
            }
        }


        merchantStocks.add(merchantStock);
        return 200;
    }

    // Updating IN Service
    public int updateMerchantStock(String id, MerchantStock merchantStock) {
        boolean merchantExists = false;
        boolean productExists = false;
        for (Merchant m : merchantService.getAllMerchants()) {
            if (m.getId().equals(merchantStock.getMerchantID())) {
                merchantExists = true;
                break;
            }
        }
        if (!merchantExists) {
            return 2;
        }

        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(merchantStock.getProductID())) {
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            return 3;
        }


        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return 200;
            }
        }
        return 1;
    }

    // Deleting IN Service
    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    // 11- Create endpoint where  merchant can add more stocks of product to a merchant Stock
    public int addMoreMerchantStocks(String id, String merchantID, String productID, int amount) {
        boolean merchantExists = false;
        boolean productExists = false;
        MerchantStock stock = null;


        for (Merchant m : merchantService.getAllMerchants()) {
            if (m.getId().equals(merchantID)) {
                merchantExists = true;
                break;
            }
        }
        if (!merchantExists) {
            return 2;
        }

        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productID)) {
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            return 3;
        }

        for (MerchantStock ms : merchantStocks) {
            if (ms.getId().equals(id) && ms.getMerchantID().equals(merchantID) && ms.getProductID().equals(productID)) {
                stock = ms;
                break;
            }
        }
        if (stock == null) {
            return 1;
        }

        stock.setStock(stock.getStock() + amount);
        return 200;
    }

    // 12- Create endpoint where user can buy a product directly
    public int buyProduct(String id, String merchantID, String productID, String userId) {
        // check if all the given ids are valid or not
        boolean merchantExists = false;
        boolean productExists = false;
        boolean userExists = false;

        for (Merchant m : merchantService.getAllMerchants()) {
            if (m.getId().equals(merchantID)) {
                merchantExists = true;
                break;
            }
        }
        if (!merchantExists) {
            return 2;
        }

        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productID)) {
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            return 3;
        }

        for (User u : userService.getAllUsers()) {
            if (u.getId().equals(userId)) {
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            return 4;
        }
        // check if the merchant has the product in stock or return bad request
        MerchantStock stock = null;
        for (MerchantStock ms : merchantStocks) {
            if (ms.getId().equals(id) && ms.getMerchantID().equals(merchantID) && ms.getProductID().equals(productID)) {
                stock = ms;
                break;
            }
        }
        if (stock == null || stock.getStock() <= 0) {
            return 5;
        }
        //  reduce the stock from the MerchantStock.
        Product product = null;
        for (Product p : productService.getAllProducts()) {
            if (p.getId().equals(productID)) {
                product = p;
                break;
            }
        }

        if (product == null) {
            return 3;
        }
        //  deducted the price of the product from the user balance.
        User user = null;
        for (User u : userService.getAllUsers()) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        // if balance is less than the product price returns bad request
        if (user.getBalance() < product.getPrice()) {
            return 6;
        }
        if (user.getPurchasedProducts() == null) {
            user.setPurchasedProducts(new ArrayList<>());
        }
        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());
        user.getPurchasedProducts().add(productID);

        return 200;
    }

    ///   مثود استعملتها في  returnProduct IN Service عشان ارجع القيمة stockالاصلية بعد ارجاع المتج
    public MerchantStock findByProductId(String productId) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductID().equals(productId)) {
                return stock;
            }
        }
        return null;
    }

}
