package com.ust.pos.data;

import com.ust.pos.bean.FoodBean;
import java.util.ArrayList;
import java.util.List;

public class FoodData {

    public static List<FoodBean> getFoods() {
        List<FoodBean> foods = new ArrayList<>();

        foods.add(new FoodBean("F001", "Margherita Pizza", "Veg", "Medium", 10, 299.0, "S001"));
        foods.add(new FoodBean("F002", "Pepperoni Pizza", "Non-Veg", "Large", 8, 399.0, "S001"));
        foods.add(new FoodBean("F003", "Paneer Tikka Pizza", "Veg", "Medium", 6, 349.0, "S002"));
        foods.add(new FoodBean("F004", "BBQ Chicken Pizza", "Non-Veg", "Large", 5, 429.0, "S003"));
        foods.add(new FoodBean("F005", "Cheese Burst Pizza", "Veg", "Small", 12, 249.0, "S004"));
        foods.add(new FoodBean("F006", "Veggie Delight", "Veg", "Medium", 9, 299.0, "S005"));
        foods.add(new FoodBean("F007", "Spicy Chicken Pizza", "Non-Veg", "Medium", 7, 359.0, "S005"));

        return foods;
    }

    public static void main(String[] args) {
        for (FoodBean f : getFoods()) {
            System.out.println(f);
        }
    }
}
