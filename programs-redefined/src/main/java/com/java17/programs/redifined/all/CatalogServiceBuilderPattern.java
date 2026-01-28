package com.java17.programs.redifined.all;

public class CatalogServiceBuilderPattern {
    public static void main(String[] args) {
        ProductCatalog p = new ProductCatalog.ProductBuilder().setName("Laptop").setPrice(60000).setColor("Silver").build();
        p.show();
    }
}
class ProductCatalog {
    private String name; private double price; private String color;
    private ProductCatalog(ProductBuilder builder) { this.name=builder.name; this.price=builder.price; this.color=builder.color; }
    public static class ProductBuilder {
        private String name; private double price; private String color;
        public ProductBuilder setName(String name) { this.name=name; return this; }
        public ProductBuilder setPrice(double price) { this.price=price; return this; }
        public ProductBuilder setColor(String color) { this.color=color; return this; }
        public ProductCatalog build() { return new ProductCatalog(this); }
    }
    public void show() { System.out.println("Product: " + name + ", Price: ₹" + price + ", Color: " + color); }
}