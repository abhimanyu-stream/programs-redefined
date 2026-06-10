package com.java17.programs.redifined.all;

public class FactoryMethodPattern {
    // Factory Method Pattern (Product creation by subclass)

    public static void main(String[] args) {
        processPayment(new CreditCardFactory(), 2500.00);
        processPayment(new UpiFactory(), 150.00);
    }

    private static void processPayment(PaymentFactory factory, double amount) {
        Payment payment = factory.createPayment();
        System.out.println("Processing payment method: " + payment.getType());
        payment.pay(amount);
        System.out.println();
    }
}

abstract class Payment {
    private final String type;

    protected Payment(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract void pay(double amount);
}

class CreditCardPayment extends Payment {
    CreditCardPayment() {
        super("Credit Card");
    }

    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Credit Card");
    }
}

class UpiPayment extends Payment {
    UpiPayment() {
        super("UPI");
    }

    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI");
    }
}

abstract class PaymentFactory {
    public abstract Payment createPayment();
}

class CreditCardFactory extends PaymentFactory {
    public Payment createPayment() {
        return new CreditCardPayment();
    }
}

class UpiFactory extends PaymentFactory {
    public Payment createPayment() {
        return new UpiPayment();
    }
}