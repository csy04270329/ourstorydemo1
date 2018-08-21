package com.soo.ourstory.repository;

import com.soo.ourstory.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUserName(String username);
}
