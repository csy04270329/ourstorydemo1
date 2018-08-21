package com.soo.ourstory.service;

import com.soo.ourstory.domain.Account;
import com.soo.ourstory.dto.AccountDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountApiService {
    Account createAccount(AccountDto.Create dto);
    Account getAccount(Long id);
    List<Account> getAccounts();
    Account updateAccount(Long id,AccountDto.Update account);
    Account updateAccount(Long id,Account account);
    void deleteAccount(Long id);
}
