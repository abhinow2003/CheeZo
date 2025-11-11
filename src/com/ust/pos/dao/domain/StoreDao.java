package com.ust.pos.dao.domain;



import com.ust.pos.bean.StoreBean;
import java.util.List;

public interface StoreDao {
    int create(StoreBean store);
    int update(StoreBean store);
    int delete(String storeId);
    StoreBean findById(String storeId);
    List<StoreBean> findAll();
    List<StoreBean> findByCity(String city);
}

