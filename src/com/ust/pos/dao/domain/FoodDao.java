package com.ust.pos.dao.domain;

import com.ust.pos.bean.FoodBean;
import java.util.List;

public interface FoodDao {
    String create(FoodBean food);
    boolean update(FoodBean food);
    boolean delete(String foodId);
    FoodBean findById(String foodId);
    List<FoodBean> findAll();
    List<FoodBean> findByType(String type);
}
