package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.FoodDao;
import com.ust.pos.bean.FoodBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FoodDaoImplements implements FoodDao {

    @Override
    public String create(FoodBean food) {
        String id = IdGenerator.nextFoodId(food.getName());
        food.setFoodID(id);
        InMemoryDataStore.FOOD_MAP.put(id, food);
        return id;
    }

    @Override
    public boolean update(FoodBean food) {
        if (food == null || food.getFoodID() == null) return false;
        InMemoryDataStore.FOOD_MAP.put(food.getFoodID(), food);
        return true;
    }

    @Override
    public boolean delete(String foodId) {
        return InMemoryDataStore.FOOD_MAP.remove(foodId) != null;
    }

    @Override
    public FoodBean findById(String foodId) {
        return InMemoryDataStore.FOOD_MAP.get(foodId);
    }

    @Override
    public List<FoodBean> findAll() {
        return new ArrayList<>(InMemoryDataStore.FOOD_MAP.values());
    }

    @Override
    public List<FoodBean> findByType(String type) {
        if (type == null) return findAll();
        return InMemoryDataStore.FOOD_MAP.values().stream()
                .filter(f -> type.equalsIgnoreCase(f.getType()))
                .collect(Collectors.toList());
    }
}

