package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.CartDao;
import com.ust.pos.bean.CartBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImplements implements CartDao {

    @Override
    public String add(CartBean cart) {
        String newId = IdGenerator.nextCartId();  
        cart.setCartID(newId);
        InMemoryDataStore.CART_LIST.add(cart);
        return newId;
    }

    @Override
    public boolean update(CartBean updated) {
        for (int i = 0; i < InMemoryDataStore.CART_LIST.size(); i++) {
            CartBean existing = InMemoryDataStore.CART_LIST.get(i);
            if (existing.getCartID().equals(updated.getCartID())) {
                InMemoryDataStore.CART_LIST.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(String cartId) {
        return InMemoryDataStore.CART_LIST.removeIf(c -> c.getCartID().equals(cartId));
    }

    @Override
    public CartBean findById(String cartId) {
        for (CartBean c : InMemoryDataStore.CART_LIST) {
            if (c.getCartID().equals(cartId)) return c;
        }
        return null;
    }

    @Override
    public List<CartBean> findByUser(String userId) {
        List<CartBean> result = new ArrayList<>();
        for (CartBean c : InMemoryDataStore.CART_LIST) {
            if (c.getUserID() != null && c.getUserID().equals(userId)) {
                result.add(c);
            }
        }
        return result;
    }
}
