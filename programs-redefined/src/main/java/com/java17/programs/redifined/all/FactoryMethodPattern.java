package com.java17.programs.redifined.all;

public class FactoryMethodPattern {
// Factory Method Pattern (Product creation by subclass)

}

abstract class Payment {
    public abstract void pay(double amount);
}

class CreditCardPayment extends Payment {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Credit Card");
    }
}

class UpiPayment extends Payment {
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