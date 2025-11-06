package com.ust.pos.dao.domain;

import com.ust.pos.bean.CartBean;
import java.util.List;

public interface CartDao {
    String add(CartBean cart);           // Return cartID as String
    boolean update(CartBean cart);
    boolean remove(String cartId);       // Use String for cartId
    CartBean findById(String cartId);    // Use String for cartId
    List<CartBean> findByUser(String userId);
}
