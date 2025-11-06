package com.ust.pos.util;

import java.util.ArrayList;
import java.util.List;
import com.ust.pos.bean.*;

public class InMemoryDataStore {

    public static final List<FoodBean> FOOD_LIST = new ArrayList<>();
    public static final List<StoreBean> STORE_LIST = new ArrayList<>();
    public static final List<ProfileBean> PROFILE_LIST = new ArrayList<>();
    public static final List<CredentialBean> CREDENTIALS_LIST = new ArrayList<>();
    public static final List<CartBean> CART_LIST = new ArrayList<>();
    public static final List<OrderBean> ORDER_LIST = new ArrayList<>();
    public static final List<PaymentBean> PAYMENT_LIST = new ArrayList<>();
}

