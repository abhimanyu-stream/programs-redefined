package com.java17.programs.redifined.all;

public class StrategyPattern {
    // Strategy Pattern (Switching between algorithms)
}

interface DiscountStrategy {
    double apply(double amount);
}

class NewYearDiscount implements DiscountStrategy {
    public double apply(double amount) {
        return amount * 0.9; // 10% off
    }
}

class NoDiscount implements DiscountStrategy {
    public double apply(double amount) {
        return amount;
    }
}

class Cart {
    private DiscountStrategy strategy;

    public Cart(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double checkout(double amount) {
        return strategy.apply(amount);
    }
}
