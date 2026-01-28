package com.java17.programs.redifined.all;

import java.util.HashMap;
import java.util.Map;

public class InventoryServiceSingletonProxyPattern {
    public static void main(String[] args) {
        InventoryProxySPP proxy = new InventoryProxySPP();
        System.out.println("Stock: " + proxy.getStock("SKU1"));
    }
}
interface InventoryAccess { int getStock(String sku); }

class Inventory implements InventoryAccess {
    private static Inventory instance;
    private Map<String,Integer> stock = new HashMap<>();
    private Inventory() { stock.put("SKU1", 100); }
    public static synchronized Inventory getInstance() {
        if(instance==null) instance = new Inventory();
        return instance;
    }
    public int getStock(String sku) { return stock.getOrDefault(sku,0); }
}

class InventoryProxySPP implements InventoryAccess {
    private Inventory inventory = Inventory.getInstance();
    public int getStock(String sku) {
        System.out.println("Checking stock for " + sku);
        return inventory.getStock(sku);
    }
}
