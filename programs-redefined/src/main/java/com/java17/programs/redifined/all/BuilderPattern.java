package com.java17.programs.redifined.all;

public class BuilderPattern {

}

class Order {
    private String product;
    private int quantity;
    private String address;

    private Order(Builder builder) {
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.address = builder.address;
    }

    static class Builder {
        private String product;
        private int quantity;
        private String address;

        public Builder product(String product) {
            this.product = product;
            return this;
        }

        public Builder quantity(int qty) {
            this.quantity = qty;
            return this;
        }

        public Builder address(String addr) {
            this.address = addr;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public String toString() {
        return "Order: " + quantity + "x " + product + " to " + address;
    }
}