package com.java17.programs.redifined.all;

public class DecoratorPattern {
    // Decorator Pattern (Adding features dynamically)
}

interface Product {
    String getDescription();

    double getPrice();
}

class BasicIProduct implements IProduct {
    public String getDescription() {
        return "Laptop";
    }

    @Override
    public String getName() {
        return null;
    }

    public double getPrice() {
        return 50000;
    }
}

class GiftWrapDecorator implements IProduct {
    private IProduct product;

    public GiftWrapDecorator(IProduct p) {
        this.product = p;
    }

    public String getDescription() {
        return product.getDescription() + " + Gift Wrap";
    }

    @Override
    public String getName() {
        return null;
    }

    public double getPrice() {
        return product.getPrice() + 200;
    }
}
