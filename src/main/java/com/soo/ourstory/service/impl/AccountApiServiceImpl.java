package com.soo.ourstory.service.impl;

import com.soo.ourstory.commons.AccountNotFoundException;
import com.soo.ourstory.commons.BoardNotFoundException;
import com.soo.ourstory.commons.UserDuplicatedException;
import com.soo.ourstory.domain.Account;
import com.soo.ourstory.dto.AccountDto;
import com.soo.ourstory.repository.AccountRepository;
import com.soo.ourstory.service.AccountApiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountApiServiceImpl implements AccountApiService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Account createAccount(AccountDto.Create dto) {
        Account account= modelMapper.map(dto, Account.class);
        String userName=dto.getUsername();
        if(accountRepository.findByUserName(userName)!=null){
            throw new UserDuplicatedException(userName);
        }
        account.setUserPw(passwordEncoder.encode(dto.getUserpw()));
        Date now= new Date();
        account.setJoined(now);
        account.setUpdated(now);
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long id) {
        Account account= accountRepository.getOne(id);
        if(account==null) throw new AccountNotFoundException(id);
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Long userId, AccountDto.Update account) {
        Account newAccount= new Account();
        newAccount.setId(userId);
        newAccount.setUserPw(account.getPassword());
        newAccount.setFullName(account.getFullName());
        Date now= new Date();
        newAccount.setUpdated(now);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account updateAccount(Long id, Account newAccount) {
        return accountRepository.save(newAccount);
    }


    @Override
    public void deleteAccount(Long id) {
        Account getAccount= accountRepository.getOne(id);
        if(getAccount!=null)
            throw new AccountNotFoundException(id);
        accountRepository.delete(getAccount);
    }
}
