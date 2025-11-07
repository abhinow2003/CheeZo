package com.ust.pos.data;

import com.ust.pos.bean.ProfileBean;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProfileData {
    public static List<ProfileBean> getProfiles() {
        List<ProfileBean> profiles = new ArrayList<>();
        profiles.add(new ProfileBean(
                "US1001",
                "Abhinav",
                "Rajesh",
                Date.valueOf("2002-05-15"),
                "Male",
                "123 MG Road",
                "Near Park",
                "Bangalore",
                "Karnataka",
                "560001",
                "9876543210",
                "abhinav@example.com",
                "pass123"
        ));

        profiles.add(new ProfileBean(
                "US1002",
                "Anjali",
                "Menon",
                Date.valueOf("2001-08-20"),
                "Female",
                "45 Church Street",
                "Old Town",
                "Kochi",
                "Kerala",
                "682001",
                "9876543211",
                "anjali@example.com",
                "pass123"
        ));

        profiles.add(new ProfileBean(
                "US1003",
                "Rahul",
                "Sharma",
                Date.valueOf("1999-12-10"),
                "Male",
                "77 Park Avenue",
                "Central",
                "Mumbai",
                "Maharashtra",
                "400001",
                "9876543212",
                "rahul@example.com",
                "pass123"
        ));

        profiles.add(new ProfileBean(
                "US1004",
                "Sneha",
                "Iyer",
                Date.valueOf("2000-03-05"),
                "Female",
                "22 MG Road",
                "West End",
                "Chennai",
                "Tamil Nadu",
                "600001",
                "9876543213",
                "sneha@example.com",
                "pass123"
        ));

        profiles.add(new ProfileBean(
                "US1005",
                "Vikram",
                "Das",
                Date.valueOf("1998-11-25"),
                "Male",
                "88 Marine Drive",
                "South City",
                "Kolkata",
                "West Bengal",
                "700001",
                "9876543214",
                "vikram@example.com",
                "pass123"
        ));

        return profiles;
    }

    public static void main(String[] args) {
        List<ProfileBean> profiles = getProfiles();
        for (ProfileBean profile : profiles) {
            System.out.println(profile);
        }
    }
}

