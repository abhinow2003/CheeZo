package com.ust.pos.dao.domain;

import com.ust.pos.bean.FoodBean;
import java.util.List;

public interface FoodDao {
    int create(FoodBean food);
    int update(FoodBean food);
    int delete(String foodId);
    FoodBean findById(String foodId);
    List<FoodBean> findAll();
    List<FoodBean> findByType(String type);
    List<FoodBean> findByStore(String storeID);
    
}
