package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.OrderDao;
import com.ust.pos.bean.OrderBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.List;
import java.util.ArrayList;

public class OrderDaoImplements implements OrderDao {

    @Override
    public String create(OrderBean order) {
        String id = IdGenerator.nextOrderId();
        order.setOrderID(id);
        InMemoryDataStore.ORDER_MAP.put(id, order);
        return id;
    }

    @Override
    public boolean update(OrderBean order) {
        if (order == null || order.getOrderID() == null) return false;
        InMemoryDataStore.ORDER_MAP.put(order.getOrderID(), order);
        return true;
    }

    @Override
    public boolean delete(String orderId) {
        return InMemoryDataStore.ORDER_MAP.remove(orderId) != null;
    }

    @Override
    public OrderBean findById(String orderId) {
        return InMemoryDataStore.ORDER_MAP.get(orderId);
    }

    @Override
    public List<OrderBean> findAll() {
        return new ArrayList<>(InMemoryDataStore.ORDER_MAP.values());
    }

    @Override
    public List<OrderBean> findByUser(String userId) {
        List<OrderBean> out = new ArrayList<>();
        for (OrderBean o : InMemoryDataStore.ORDER_MAP.values()) {
            if (userId.equals(o.getUserID())) out.add(o);
        }
        return out;
    }
}

