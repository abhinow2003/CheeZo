package com.ust.pos.util;

import java.util.HashMap;
import java.util.Map;
import com.ust.pos.bean.*;

public class InMemoryDataStore {
    public static final Map<String, FoodBean> FOOD_MAP = new HashMap<>();
    public static final Map<String, StoreBean> STORE_MAP = new HashMap<>();
    public static final Map<String, ProfileBean> PROFILE_MAP = new HashMap<>();
    public static final Map<String, CredentialBean> CREDENTIALS_MAP = new HashMap<>();
    public static final Map<String, CartBean> CART_MAP = new HashMap<>();
    public static final Map<String, OrderBean> ORDER_MAP = new HashMap<>();
    public static final Map<String, PaymentBean> PAYMENT_MAP = new HashMap<>();
}
