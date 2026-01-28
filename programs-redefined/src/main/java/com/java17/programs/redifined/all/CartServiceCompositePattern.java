package com.java17.programs.redifined.all;

import java.util.ArrayList;
import java.util.List;

public class CartServiceCompositePattern {
    public static void main(String[] args) {
        CartCP cart = new CartCP();
        cart.add(new ProductItem(500));
        cart.add(new ProductItem(1500));
        System.out.println("Total Cart Price: ₹" + cart.getPrice());
    }
}
interface CartItem { double getPrice(); }

class ProductItem implements CartItem {
    private double price;
    public ProductItem(double price) { this.price = price; }
    public double getPrice() { return price; }
}

class CartCP implements CartItem {
    private List<CartItem> items = new ArrayList<>();
    public void add(CartItem item) { items.add(item); }
    public double getPrice() { return items.stream().mapToDouble(CartItem::getPrice).sum(); }
}