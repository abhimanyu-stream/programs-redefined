package com.java17.programs.redifined.all;

public class PaymentServiceStrategyPattern {
    public static void main(String[] args) {
        // Pay with Credit Card
        PaymentContext payment = new PaymentContext(new CreditCardPaymentSP());
        payment.pay(1500);

        // Pay with UPI
        payment = new PaymentContext(new UpiPaymentSP());
        payment.pay(1200);
    }
}

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPaymentSP implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " via Credit Card");
    }
}

class UpiPaymentSP implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " via UPI");
    }
}

class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void pay(double amount) {
        strategy.pay(amount);
    }
}
