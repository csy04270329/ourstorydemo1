package com.soo.ourstory.service.impl;

import com.soo.ourstory.domain.AccountGroup;
import com.soo.ourstory.repository.AccountGroupRepository;
import com.soo.ourstory.service.AccountGroupApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountGroupApiServiceImpl implements AccountGroupApiService {

    @Autowired
    AccountGroupRepository accountGroupRepository;
    @Override
    public AccountGroup getAccountGroup(Long id) {
        return accountGroupRepository.getOne(id);
    }

    @Override
    public AccountGroup createAccountGroup(AccountGroup group) {
        return accountGroupRepository.save(group);
    }

    @Override
    public AccountGroup updateAccountGroup(Long id, AccountGroup group) {
        return null;
    }

    @Override
    public void deleteAccountGroup(Long id, AccountGroup group) {

    }
}
