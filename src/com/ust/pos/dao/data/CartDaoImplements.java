package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.CartDao;
import com.ust.pos.bean.CartBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;

public class CartDaoImplements implements CartDao {

    @Override
    public String add(CartBean cart) {
        String id = IdGenerator.nextCartId();
        cart.setCartID(id);
        InMemoryDataStore.CART_MAP.put(id, cart);
        return id;
    }

    @Override
    public boolean update(CartBean cart) {
        if (cart == null) return false;
        InMemoryDataStore.CART_MAP.put(cart.getCartID(), cart);
        return true;
    }

    @Override
    public boolean remove(String cartId) {
        return InMemoryDataStore.CART_MAP.remove(cartId) != null;
    }

    @Override
    public CartBean findById(String cartId) {
        return InMemoryDataStore.CART_MAP.get(cartId);
    }

    @Override
    public List<CartBean> findByUser(String userId) {
        List<CartBean> out = new ArrayList<>();
        for (Entry<String, CartBean> e : InMemoryDataStore.CART_MAP.entrySet()) {
            if (userId.equals(e.getValue().getUserID())) out.add(e.getValue());
        }
        return out;
    }
}

