package com.soo.ourstory.security;


import com.soo.ourstory.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

///프로젝트마다 다른 user정보를 security에 알려줌 userdetails는 추상화된 인터페이스
public class UserDetailsImpl extends User {

    public UserDetailsImpl(Account account) {
        super(account.getUserName(), account.getUserPw(), authorities(account));
    }

    private static Collection<? extends GrantedAuthority> authorities(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(account.isAdmin()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }
}
