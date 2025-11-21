package com.ust.pos.dao.domain;

import com.ust.pos.bean.CredentialBean;
import com.ust.pos.bean.ProfileBean;

public interface CredentialDao {

    int create(CredentialBean creds);

    CredentialBean findByUserId(String userId);

    int update(CredentialBean creds);

    int delete(String userId);

    CredentialBean authenticate(String userId, String password);

    boolean logout(String userID);
    
    boolean updateLoginStatus(String userID, int status);

    public ProfileBean findProfileByUserId(String userID);

}

