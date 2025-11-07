package com.ust.pos.data;
import com.ust.pos.bean.StoreBean;
import java.util.ArrayList;
import java.util.List;

public class StoreData {

    public static List<StoreBean> getStores() {
        List<StoreBean> stores = new ArrayList<>();

        stores.add(new StoreBean(
                "ST1001",
                "FreshMart",
                "12 MG Road",
                "9876500010",
                "Bangalore",
                "Karnataka",
                "560001"
        ));

        stores.add(new StoreBean(
                "ST1002",
                "Daily Needs",
                "45 Church Street",
                "9876500011",
                "Kochi",
                "Kerala",
                "682001"
        ));

        stores.add(new StoreBean(
                "ST1003",
                "QuickShop",
                "77 Park Avenue",
                "9876500012",
                "Mumbai",
                "Maharashtra",
                "400001"
        ));

        stores.add(new StoreBean(
                "ST1004",
                "Urban Store",
                "22 MG Road",
                "9876500013",
                "Chennai",
                "Tamil Nadu",
                "600001"
        ));

        stores.add(new StoreBean(
                "ST1005",
                "CityMart",
                "88 Marine Drive",
                "9876500014",
                "Kolkata",
                "West Bengal",
                "700001"
        ));

        return stores;
    }

    public static void main(String[] args) {
        List<StoreBean> stores = getStores();
        for (StoreBean store : stores) {
            System.out.println(store);
        }
    }
}

