package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.StoreDao;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StoreDaoImplements implements StoreDao {

    @Override
    public String create(StoreBean store) {
        String id = IdGenerator.nextStoreId(store.getName());
        store.setStoreID(id);
        InMemoryDataStore.STORE_MAP.put(id, store);
        return id;
    }

    @Override
    public boolean update(StoreBean store) {
        if (store == null || store.getStoreID() == null) return false;
        InMemoryDataStore.STORE_MAP.put(store.getStoreID(), store);
        return true;
    }

    @Override
    public boolean delete(String storeId) {
        return InMemoryDataStore.STORE_MAP.remove(storeId) != null;
    }

    @Override
    public StoreBean findById(String storeId) {
        return InMemoryDataStore.STORE_MAP.get(storeId);
    }

    @Override
    public List<StoreBean> findAll() {
        return new ArrayList<>(InMemoryDataStore.STORE_MAP.values());
    }

    @Override
    public List<StoreBean> findByCity(String city) {
        if (city == null) return findAll();
        return InMemoryDataStore.STORE_MAP.values().stream()
                .filter(s -> city.equalsIgnoreCase(s.getCity()))
                .collect(Collectors.toList());
    }
}

