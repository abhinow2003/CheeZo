package com.ust.pos.dao.data;


import com.ust.pos.dao.domain.UserDao;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.bean.CredentialBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.List;
import java.util.ArrayList;

public class UserDaoImplements implements UserDao {

    @Override
    public String register(ProfileBean profile, CredentialBean creds) {
        if (profile == null || creds == null || profile.getFirstName() == null) return "FAIL";
        String userId = IdGenerator.nextUserId(profile.getFirstName());
        profile.setUserID(userId);
        creds.setUserID(userId);
        InMemoryDataStore.PROFILE_MAP.put(userId, profile);
        InMemoryDataStore.CREDENTIALS_MAP.put(userId, creds);
        return userId;
    }

    @Override
    public ProfileBean findProfileById(String userId) {
        return InMemoryDataStore.PROFILE_MAP.get(userId);
    }

    @Override
    public CredentialBean findCredentialsById(String userId) {
        return InMemoryDataStore.CREDENTIALS_MAP.get(userId);
    }

    @Override
    public boolean updateProfile(ProfileBean profile) {
        if (profile == null || profile.getUserID() == null) return false;
        InMemoryDataStore.PROFILE_MAP.put(profile.getUserID(), profile);
        return true;
    }

    @Override
    public boolean updateCredentials(CredentialBean creds) {
        if (creds == null || creds.getUserID() == null) return false;
        InMemoryDataStore.CREDENTIALS_MAP.put(creds.getUserID(), creds);
        return true;
    }

    @Override
    public List<ProfileBean> findAllProfiles() {
        return new ArrayList<>(InMemoryDataStore.PROFILE_MAP.values());
    }
}

