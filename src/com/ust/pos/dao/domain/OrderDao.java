package com.ust.pos.dao.domain;


import com.ust.pos.bean.OrderBean;
import java.util.List;

public interface OrderDao {
    String create(OrderBean order);
    boolean update(OrderBean order);
    boolean delete(String orderId);
    OrderBean findById(String orderId);
    List<OrderBean> findAll();
    List<OrderBean> findByUser(String userId);
}
