package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class MerchantStockService {
    private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;

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
}
