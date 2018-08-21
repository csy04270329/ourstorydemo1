package com.soo.ourstory.commons;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDuplicatedException extends RuntimeException {
    String username;
    public UserDuplicatedException(String username) {
        this.username = username;
    }
}
