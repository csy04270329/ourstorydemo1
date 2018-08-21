package com.soo.ourstory.dto;

import com.soo.ourstory.domain.Account;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

public class AccountGroupDto {

    @Data
    public static class CreateDto{


    }

    @Data
    public static class UpdateDto{

    }

    @Data
    public static class Response{
        List<Account> accounts;
    }
}
