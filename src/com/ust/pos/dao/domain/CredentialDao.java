package com.ust.pos.dao.domain;

import com.ust.pos.bean.CredentialBean;

public interface CredentialDao {

    int create(CredentialBean creds);

    CredentialBean findByUserId(String userId);

    int update(CredentialBean creds);

    int delete(String userId);

    CredentialBean authenticate(String userId, String password);
}

