package com.ust.pos.dao.domain;

import com.ust.pos.bean.CredentialBean;
import com.ust.pos.bean.ProfileBean;
import java.util.List;

public interface ProfileDao {
    String register(ProfileBean profile, CredentialBean creds);
    ProfileBean findProfileById(String userId);
    CredentialBean findCredentialsById(String userId);
    boolean updateProfile(ProfileBean profile);
    boolean updateCredentials(CredentialBean creds);
    List<ProfileBean> findAllProfiles();
}

