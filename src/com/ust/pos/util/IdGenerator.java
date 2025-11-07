package com.ust.pos.util;

public class IdGenerator {

    private static int userCounter = 1005;
    private static int storeCounter = 1005;
    private static int foodCounter = 1030;
    private static int orderCounter = 1000;
    private static int cartCounter = 1000;

    public static String nextUserId() {
        userCounter++;
        return "US" + userCounter;
    }

    public static String nextStoreId() {
        storeCounter++;
        return "ST" + storeCounter;
    }

    public static String nextFoodId() {
        foodCounter++;
        return "FD" + foodCounter;
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
