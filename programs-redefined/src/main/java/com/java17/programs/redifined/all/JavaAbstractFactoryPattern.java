package com.java17.programs.redifined.all;

public class JavaAbstractFactoryPattern {
    public static void main(String[] args) {
        // Get Electronics Factory
        ProductFactory electronicsFactory = FactoryProducer.getFactory("ELECTRONICS");
        IProduct mobile = electronicsFactory.createProduct("MOBILE");
        System.out.println("Bought: " + mobile.getName() + " for ₹" + mobile.getPrice());

        IProduct laptop = electronicsFactory.createProduct("LAPTOP");
        System.out.println("Bought: " + laptop.getName() + " for ₹" + laptop.getPrice());

        // Get Clothing Factory
        ProductFactory clothingFactory = FactoryProducer.getFactory("CLOTHING");
        IProduct shirt = clothingFactory.createProduct("SHIRT");
        System.out.println("Bought: " + shirt.getName() + " for ₹" + shirt.getPrice());

        IProduct jeans = clothingFactory.createProduct("JEANS");
        System.out.println("Bought: " + jeans.getName() + " for ₹" + jeans.getPrice());
    }

}

// Abstract Product
interface IProduct {
    String getName();

    double getPrice();

    String getDescription();
}

// Concrete Products for Electronics
class Mobile implements IProduct {
    @Override
    public String getName() {
        return "Smartphone";
    }

    @Override
    public double getPrice() {
        return 25000.0;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

class Laptop implements IProduct {
    @Override
    public String getName() {
        return "Laptop";
    }

    @Override
    public double getPrice() {
        return 60000.0;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

// Concrete Products for Clothing
class Shirt implements IProduct {
    @Override
    public String getName() {
        return "Shirt";
    }

    @Override
    public double getPrice() {
        return 1500.0;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

class Jeans implements IProduct {
    @Override
    public String getName() {
        return "Jeans";
    }

    @Override
    public double getPrice() {
        return 2000.0;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

// Abstract Factory
interface ProductFactory {
    IProduct createProduct(String type);
}

// Concrete Factory for Electronics
class ElectronicsFactory implements ProductFactory {
    @Override
    public IProduct createProduct(String type) {
        if ("MOBILE".equalsIgnoreCase(type)) {
            return new Mobile();
        } else if ("LAPTOP".equalsIgnoreCase(type)) {
            return new Laptop();
        }
        throw new IllegalArgumentException("Unknown Electronics product type: " + type);
    }
}

// Concrete Factory for Clothing
class ClothingFactory implements ProductFactory {
    @Override
    public IProduct createProduct(String type) {
        if ("SHIRT".equalsIgnoreCase(type)) {
            return new Shirt();
        } else if ("JEANS".equalsIgnoreCase(type)) {
            return new Jeans();
        }
        throw new IllegalArgumentException("Unknown Clothing product type: " + type);
    }
}

// Factory Producer (to get factories by category)
class FactoryProducer {
    public static ProductFactory getFactory(String choice) {
        if ("ELECTRONICS".equalsIgnoreCase(choice)) {
            return new ElectronicsFactory();
        } else if ("CLOTHING".equalsIgnoreCase(choice)) {
            return new ClothingFactory();
        }
        throw new IllegalArgumentException("Unknown factory type: " + choice);
    }
}
