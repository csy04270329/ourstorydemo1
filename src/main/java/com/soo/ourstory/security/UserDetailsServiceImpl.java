package com.soo.ourstory.security;

import com.soo.ourstory.domain.Account;
import com.soo.ourstory.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account= accountRepository.findByUserName(userName);
        if(account==null){
            throw new UsernameNotFoundException(userName);
        }
        return new UserDetailsImpl(account);
    }
}
