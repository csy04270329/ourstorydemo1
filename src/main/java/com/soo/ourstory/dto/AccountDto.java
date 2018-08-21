package com.soo.ourstory.dto;

import com.soo.ourstory.domain.AccountGroup;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

public class AccountDto {

    @Data
    public static class Create{
        @Size(min=5)
        private String username;
        @Size(min=5)
        private String userpw;
        private AccountGroup accountGroup;
    }

    @Data
    public static class Response{
        private String username;
        private String fullName;
        private Date joined;
        private Date updated;
    }

    @Data
    public static class Update{
        private String password;
        private String fullName;
        private AccountGroup accountGroup;
    }

}
