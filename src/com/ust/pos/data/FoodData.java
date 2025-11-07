package com.ust.pos.data;

import com.ust.pos.bean.FoodBean;
import java.util.ArrayList;
import java.util.List;

public class FoodData {

    public static List<FoodBean> getFoods() {
        List<FoodBean> foods = new ArrayList<>();

       // --- Store ST1001 ---
foods.add(new FoodBean("FD1001", "Margherita Pizza", "Veg", "Medium", 10, 299.0, "ST1001"));
foods.add(new FoodBean("FD1002", "Pepperoni Pizza", "Non-Veg", "Large", 8, 399.0, "ST1001"));
foods.add(new FoodBean("FD1003", "Farmhouse Pizza", "Veg", "Large", 6, 379.0, "ST1001"));
foods.add(new FoodBean("FD1004", "Double Cheese Margherita", "Veg", "Medium", 9, 329.0, "ST1001"));
foods.add(new FoodBean("FD1005", "Veg Supreme Pizza", "Veg", "Small", 12, 259.0, "ST1001"));
foods.add(new FoodBean("FD1006", "Chicken Golden Delight", "Non-Veg", "Medium", 7, 389.0, "ST1001"));

// --- Store ST1002 ---
foods.add(new FoodBean("FD1007", "Paneer Tikka Pizza", "Veg", "Medium", 6, 349.0, "ST1002"));
foods.add(new FoodBean("FD1008", "Mexican Green Wave", "Veg", "Large", 5, 399.0, "ST1002"));
foods.add(new FoodBean("FD1009", "Tandoori Paneer Pizza", "Veg", "Medium", 8, 359.0, "ST1002"));
foods.add(new FoodBean("FD1010", "Veggie Paradise", "Veg", "Small", 11, 279.0, "ST1002"));
foods.add(new FoodBean("FD1011", "Cheese n Corn Pizza", "Veg", "Medium", 10, 309.0, "ST1002"));
foods.add(new FoodBean("FD1012", "Paneer Makhani Pizza", "Veg", "Large", 7, 389.0, "ST1002"));

// --- Store ST1003 ---
foods.add(new FoodBean("FD1013", "BBQ Chicken Pizza", "Non-Veg", "Large", 5, 429.0, "ST1003"));
foods.add(new FoodBean("FD1014", "Smoked Chicken Pizza", "Non-Veg", "Medium", 6, 419.0, "ST1003"));
foods.add(new FoodBean("FD1015", "Peri Peri Chicken Pizza", "Non-Veg", "Large", 7, 439.0, "ST1003"));
foods.add(new FoodBean("FD1016", "Chicken Keema Pizza", "Non-Veg", "Medium", 8, 409.0, "ST1003"));
foods.add(new FoodBean("FD1017", "Chicken Fiesta Pizza", "Non-Veg", "Small", 9, 349.0, "ST1003"));
foods.add(new FoodBean("FD1018", "Spicy Chicken Delight", "Non-Veg", "Medium", 5, 369.0, "ST1003"));

// --- Store ST1004 ---
foods.add(new FoodBean("FD1019", "Cheese Burst Pizza", "Veg", "Small", 12, 249.0, "ST1004"));
foods.add(new FoodBean("FD1020", "Classic Veg Pizza", "Veg", "Medium", 10, 299.0, "ST1004"));
foods.add(new FoodBean("FD1021", "Deluxe Veggie Pizza", "Veg", "Large", 6, 339.0, "ST1004"));
foods.add(new FoodBean("FD1022", "Peppy Paneer Pizza", "Veg", "Medium", 8, 329.0, "ST1004"));
foods.add(new FoodBean("FD1023", "Veg Extravaganza", "Veg", "Large", 7, 379.0, "ST1004"));
foods.add(new FoodBean("FD1024", "Cheesy Tomato Pizza", "Veg", "Small", 11, 269.0, "ST1004"));

// --- Store ST1005 ---
foods.add(new FoodBean("FD1025", "Veggie Delight", "Veg", "Medium", 9, 299.0, "ST1005"));
foods.add(new FoodBean("FD1026", "Spicy Chicken Pizza", "Non-Veg", "Medium", 7, 359.0, "ST1005"));
foods.add(new FoodBean("FD1027", "Chicken Sausage Pizza", "Non-Veg", "Large", 6, 419.0, "ST1005"));
foods.add(new FoodBean("FD1028", "Corn & Cheese Pizza", "Veg", "Medium", 10, 289.0, "ST1005"));
foods.add(new FoodBean("FD1029", "Paneer Onion Pizza", "Veg", "Small", 12, 269.0, "ST1005"));
foods.add(new FoodBean("FD1030", "Chicken Tandoori Feast", "Non-Veg", "Large", 5, 449.0, "ST1005"));


        return foods;
    }

    public static void main(String[] args) {
        for (FoodBean f : getFoods()) {
            System.out.println(f);
        }
    }
}
