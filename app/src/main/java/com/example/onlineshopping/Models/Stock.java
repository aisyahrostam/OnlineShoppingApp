package com.example.onlineshopping.Models;

import java.util.Comparator;

public class Stock {

    String id, name, manufacturer, category,price, quantity;

    public Stock() {
    }

    public Stock(String id, String name, String price, String quantity, String manufacturer, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.category = category;
    }

    public static Comparator<Stock> StockAZComparator = new Comparator<Stock>() {
        @Override
        public int compare(Stock s1, Stock s2) {
            return s1.getName().compareTo(s2.getName());
        }
    };

    public static Comparator<Stock> StockZAComparator = new Comparator<Stock>() {
        @Override
        public int compare(Stock s1, Stock s2) {
            return s2.getName().compareTo(s1.getName());
        }
    };

    public static Comparator<Stock> StockManufacturerAZComparator = new Comparator<Stock>() {
        @Override
        public int compare(Stock s1, Stock s2) {
            return s1.getManufacturer().compareTo(s2.getManufacturer());
        }
    };

    public static Comparator<Stock> StockManufacturerZAComparator = new Comparator<Stock>() {
        @Override
        public int compare(Stock s1, Stock s2) {
            return s2.getManufacturer().compareTo(s1.getManufacturer());
        }
    };
//    public static Comparator<Stock> StockManufacturerAZComparator = new Comparator<Stock>() {
//        @Override
//        public int compare(Stock s1, Stock s2) {
//            return s1.getManufacturer().compareTo(s2.getManufacturer());
//        }
//    };
//
//    public static Comparator<Stock> StockManufacturerZAComparator = new Comparator<Stock>() {
//        @Override
//        public int compare(Stock s1, Stock s2) {
//            return s2.getManufacturer().compareTo(s1.getManufacturer());
//        }
//    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
