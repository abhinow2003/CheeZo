package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.StoreDao;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.ArrayList;
import java.util.List;

public class StoreDaoImplements implements StoreDao {

    @Override
    public String create(StoreBean store) {
        String id = IdGenerator.nextStoreId();
        store.setStoreID(id);
        InMemoryDataStore.STORE_LIST.add(store);
        return id;
    }

    @Override
    public boolean update(StoreBean updated) {
        for (int i = 0; i < InMemoryDataStore.STORE_LIST.size(); i++) {
            StoreBean s = InMemoryDataStore.STORE_LIST.get(i);
            if (s.getStoreID().equals(updated.getStoreID())) {
                InMemoryDataStore.STORE_LIST.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String storeId) {
        return InMemoryDataStore.STORE_LIST.removeIf(s -> s.getStoreID().equals(storeId));
    }

    @Override
    public StoreBean findById(String storeId) {
        for (StoreBean s : InMemoryDataStore.STORE_LIST) {
            if (s.getStoreID().equals(storeId)) return s;
        }
        return null;
    }

    @Override
    public List<StoreBean> findAll() {
        return new ArrayList<>(InMemoryDataStore.STORE_LIST);
    }

    @Override
    public List<StoreBean> findByCity(String city) {
        List<StoreBean> result = new ArrayList<>();
        for (StoreBean s : InMemoryDataStore.STORE_LIST) {
            if (s.getCity().equalsIgnoreCase(city)) result.add(s);
        }
        return result;
    }
}
