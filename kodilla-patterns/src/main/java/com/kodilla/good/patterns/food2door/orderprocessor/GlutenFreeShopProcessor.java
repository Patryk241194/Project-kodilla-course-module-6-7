package com.kodilla.good.patterns.food2door.orderprocessor;

import com.kodilla.good.patterns.food2door.customer.User;
import com.kodilla.good.patterns.food2door.products.Product;
import com.kodilla.good.patterns.food2door.supplier.GlutenFreeShop;
import com.kodilla.good.patterns.food2door.supplier.Supplier;

import java.time.LocalDateTime;
import java.util.Map;

public class GlutenFreeShopProcessor implements OrderProcessor {
    @Override
    public boolean process(Supplier supplier, LocalDateTime purchaseTime, Map<Product, Integer> products, User user) {
        Supplier glutenFreeShop = new GlutenFreeShop();

        double totalPrice = calculateTotalPrice(products, supplier.getDeliveryPrice());

        if (user.isMembershipActive()) {
            totalPrice = applyMembershipDiscount(totalPrice, supplier.getDeliveryPrice());
        }

        displayOrderSummary(supplier, totalPrice, products);

        updateStock(glutenFreeShop, products);

        return true;
    }

    private double applyMembershipDiscount(double totalPrice, double deliveryPrice) {
        if (totalPrice - deliveryPrice > 50) {
            return totalPrice - deliveryPrice;
        } else {
            return totalPrice;
        }
    }
}
