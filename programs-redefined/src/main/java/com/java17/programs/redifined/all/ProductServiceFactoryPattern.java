package com.java17.programs.redifined.all;

public class ProductServiceFactoryPattern {
    public static void main(String[] args) {
        ProductFP p1 = ProductFactoryFP.create("Book");
        ProductFP p2 = ProductFactoryFP.create("Electronics");
        System.out.println(p1.getType() + " created");
        System.out.println(p2.getType() + " created");
    }
}
abstract class ProductFP { abstract String getType(); }
class BookFP extends ProductFP { String getType() { return "Book"; } }
class ElectronicsFP extends ProductFP { String getType() { return "Electronics"; } }

class ProductFactoryFP {
    public static ProductFP create(String type) {
        return switch(type) {
            case "Book" -> new BookFP();
            case "Electronics" -> new ElectronicsFP();
            default -> throw new IllegalArgumentException("Unknown product");
        };
    }
}