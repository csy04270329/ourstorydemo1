package com.soo.ourstory.service;

import com.soo.ourstory.domain.AccountGroup;

public interface AccountGroupApiService {

    AccountGroup getAccountGroup(Long id);

    AccountGroup createAccountGroup(AccountGroup group);

    AccountGroup updateAccountGroup(Long id, AccountGroup group);

    void deleteAccountGroup(Long id, AccountGroup group);
}
