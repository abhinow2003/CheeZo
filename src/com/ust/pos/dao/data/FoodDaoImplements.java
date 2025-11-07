package com.ust.pos.dao.data;
import com.ust.pos.dao.domain.FoodDao;
import com.ust.pos.bean.FoodBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoImplements implements FoodDao {

    @Override
    public String create(FoodBean food) {
        String id = IdGenerator.nextFoodId();
        food.setFoodID(id);
        InMemoryDataStore.FOOD_LIST.add(food);
        return id;
    }

    @Override
    public boolean update(FoodBean updated) {
        for (int i = 0; i < InMemoryDataStore.FOOD_LIST.size(); i++) {
            FoodBean f = InMemoryDataStore.FOOD_LIST.get(i);
            if (f.getFoodID().equals(updated.getFoodID())) {
                InMemoryDataStore.FOOD_LIST.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String foodId) {
        return InMemoryDataStore.FOOD_LIST.removeIf(f -> f.getFoodID().equals(foodId));
    }

    @Override
    public FoodBean findById(String foodId) {
        for (FoodBean f : InMemoryDataStore.FOOD_LIST) {
            if (f.getFoodID().equals(foodId)) return f;
        }
        return null;
    }

    @Override
    public List<FoodBean> findAll() {
        return new ArrayList<>(InMemoryDataStore.FOOD_LIST);
    }

    @Override
    public List<FoodBean> findByType(String type) {
        List<FoodBean> result = new ArrayList<>();
        for (FoodBean f : InMemoryDataStore.FOOD_LIST) {
            if (type.equalsIgnoreCase(f.getType())) result.add(f);
        }
        return result;
    }
}
