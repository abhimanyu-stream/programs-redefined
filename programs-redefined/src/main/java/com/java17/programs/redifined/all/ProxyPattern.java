package com.java17.programs.redifined.all;

public class ProxyPattern {
    // Proxy Pattern (Control access)

}

interface InventoryService {
    void checkStock(String product);
}

class RealInventoryService implements InventoryService {
    public void checkStock(String product) {
        System.out.println(product + " stock is available.");
    }
}

class InventoryProxy implements InventoryService {
    private RealInventoryService realService = new RealInventoryService();

    public void checkStock(String product) {
        System.out.println("Checking cache before DB...");
        realService.checkStock(product);
    }
}
