package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.OrderDao;
import com.ust.pos.bean.OrderBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImplements implements OrderDao {

    @Override
    public String create(OrderBean order) {
        String id = IdGenerator.nextOrderId();
        order.setOrderID(id);
        InMemoryDataStore.ORDER_LIST.add(order);
        return id;
    }

    @Override
    public boolean update(OrderBean updated) {
        for (int i = 0; i < InMemoryDataStore.ORDER_LIST.size(); i++) {
            if (InMemoryDataStore.ORDER_LIST.get(i).getOrderID().equals(updated.getOrderID())) {
                InMemoryDataStore.ORDER_LIST.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String orderId) {
        return InMemoryDataStore.ORDER_LIST.removeIf(o -> o.getOrderID().equals(orderId));
    }

    @Override
    public OrderBean findById(String orderId) {
        for (OrderBean o : InMemoryDataStore.ORDER_LIST) {
            if (o.getOrderID().equals(orderId)) return o;
        }
        return null;
    }

    @Override
    public List<OrderBean> findAll() {
        return new ArrayList<>(InMemoryDataStore.ORDER_LIST);
    }

    @Override
    public List<OrderBean> findByUser(String userId) {
        List<OrderBean> result = new ArrayList<>();
        for (OrderBean o : InMemoryDataStore.ORDER_LIST) {
            if (o.getUserID().equals(userId)) result.add(o);
        }
        return result;
    }
}
