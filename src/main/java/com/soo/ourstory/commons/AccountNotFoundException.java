package com.soo.ourstory.commons;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AccountNotFoundException extends RuntimeException {
    Long userId;
    public AccountNotFoundException(Long userId){this.userId=userId;}
}
