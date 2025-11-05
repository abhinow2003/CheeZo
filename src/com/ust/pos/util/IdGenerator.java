package com.ust.pos.util;


public class IdGenerator {

    private static int userCounter = 1000;
    private static int storeCounter = 1000;
    private static int foodCounter = 1000;
    private static int orderCounter = 1000;
    private static int cartCounter = 1000;

    public static String nextUserId(String firstName) {
        userCounter++;
        String prefix = (firstName != null && firstName.length() >= 2)
                ? firstName.substring(0, 2).toUpperCase()
                : "US";
        return prefix + userCounter;
    }

    public static String nextStoreId(String storeName) {
        storeCounter++;
        String prefix = (storeName != null && storeName.length() >= 2)
                ? storeName.substring(0, 2).toUpperCase()
                : "ST";
        return prefix + storeCounter;
    }

    public static String nextFoodId(String foodName) {
        foodCounter++;
        String prefix = (foodName != null && foodName.length() >= 2)
                ? foodName.substring(0, 2).toUpperCase()
                : "FD";
        return prefix + foodCounter;
    }

    public static String nextOrderId() {
        orderCounter++;
        return "OR" + orderCounter;
    }

    public static String nextCartId() {
        cartCounter++;
        return "CT" + cartCounter;
    }
}

