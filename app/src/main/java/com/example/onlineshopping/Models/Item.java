package com.example.onlineshopping.Models;

import java.util.Comparator;

public class Item{

    String id, name, price, quantity, manufacturer, category;



    public Item() {
    }



    public Item(String id, String name, String price, String quantity, String manufacturer, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.category = category;
    }

    public static Comparator<Item> itemAZComparator = new Comparator<Item>() {
        @Override
        public int compare(Item t1, Item t2) {
            return t1.getName().compareTo(t2.getName());
        }
    };

    public static Comparator<Item> itemZAComparator = new Comparator<Item>() {
        @Override
        public int compare(Item t1, Item t2) {
            return t2.getName().compareTo(t1.getName());
        }
    };

    public static Comparator<Item> itemBrandAZComparator = new Comparator<Item>() {
        @Override
        public int compare(Item t1, Item t2) {
            return t1.getManufacturer().compareTo(t2.getManufacturer());
        }
    };

    public static Comparator<Item> itemBrandZAComparator = new Comparator<Item>() {
        @Override
        public int compare(Item t1, Item t2) {
            return t2.getManufacturer().compareTo(t1.getManufacturer());
        }
    };

//    public static Comparator<Item> itemAscendingComparator = new Comparator<Item>() {
//        @Override
//        public int compare(Item t1, Item t2) {
//            return t1.getPrice() - t2.getPrice();
//        }
//    };
//
//    public static Comparator<Item> itemDescendingComparator = new Comparator<Item>() {
//        @Override
//        public int compare(Item t1, Item t2) {
//            return t2.getNprice() - t1.getNprice();
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
