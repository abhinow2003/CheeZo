package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.UserDao;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.bean.CredentialBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.util.IdGenerator;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImplements implements UserDao {

    @Override
    public String register(ProfileBean profile, CredentialBean creds) {
        String userId = IdGenerator.nextUserId();
        profile.setUserID(userId);
        creds.setUserID(userId);
        InMemoryDataStore.PROFILE_LIST.add(profile);
        InMemoryDataStore.CREDENTIALS_LIST.add(creds);
        return userId;
    }

    @Override
    public ProfileBean findProfileById(String userId) {
        for (ProfileBean p : InMemoryDataStore.PROFILE_LIST) {
            if (p.getUserID().equals(userId)) return p;
        }
        return null;
    }

    @Override
    public CredentialBean findCredentialsById(String userId) {
        for (CredentialBean c : InMemoryDataStore.CREDENTIALS_LIST) {
            if (c.getUserID().equals(userId)) return c;
        }
        return null;
    }

    @Override
    public boolean updateProfile(ProfileBean profile) {
        for (int i = 0; i < InMemoryDataStore.PROFILE_LIST.size(); i++) {
            if (InMemoryDataStore.PROFILE_LIST.get(i).getUserID().equals(profile.getUserID())) {
                InMemoryDataStore.PROFILE_LIST.set(i, profile);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateCredentials(CredentialBean creds) {
        for (int i = 0; i < InMemoryDataStore.CREDENTIALS_LIST.size(); i++) {
            if (InMemoryDataStore.CREDENTIALS_LIST.get(i).getUserID().equals(creds.getUserID())) {
                InMemoryDataStore.CREDENTIALS_LIST.set(i, creds);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ProfileBean> findAllProfiles() {
        return new ArrayList<>(InMemoryDataStore.PROFILE_LIST);
    }
}
