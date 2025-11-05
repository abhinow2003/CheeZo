package com.ust.pos.dao.domain;



import com.ust.pos.bean.StoreBean;
import java.util.List;

public interface StoreDao {
    String create(StoreBean store);
    boolean update(StoreBean store);
    boolean delete(String storeId);
    StoreBean findById(String storeId);
    List<StoreBean> findAll();
    List<StoreBean> findByCity(String city);
}

