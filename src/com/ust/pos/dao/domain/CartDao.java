package com.ust.pos.dao.domain;

import com.ust.pos.bean.CartBean;
import java.util.List;

public interface CartDao {
    String add(CartBean cart);
    boolean update(CartBean cart);
    boolean remove(String cartId);
    CartBean findById(String cartId);
    List<CartBean> findByUser(String userId);
}
