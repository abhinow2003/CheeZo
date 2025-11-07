package com.ust.pos.util;

import java.util.ArrayList;
import java.util.List;
import com.ust.pos.bean.*;

public class InMemoryDataStore {

    public static  List<FoodBean> FOOD_LIST = new ArrayList<>();
    public static  List<StoreBean> STORE_LIST = new ArrayList<>();
    public static  List<ProfileBean> PROFILE_LIST = new ArrayList<>();
    public static  List<CredentialBean> CREDENTIALS_LIST = new ArrayList<>();
    public static  List<CartBean> CART_LIST = new ArrayList<>();
    public static  List<OrderBean> ORDER_LIST = new ArrayList<>();
    public static  List<PaymentBean> PAYMENT_LIST = new ArrayList<>();
}

