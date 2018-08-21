package com.soo.ourstory.dto;

import com.soo.ourstory.domain.Account;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;

public class BoardDto {

    @Data
    public static class Response {
        private String title;
        private String contents;
        private boolean isSecret;
        private Date joined;
        private Date updated;
        ///댓글...
    }


    @Data
    public static class Create{
        @Size(min = 1)
        private String title;
        @Size(min = 1)
        private String contents;
        private Account account;
        private boolean isSecret;
        private String password;
    }

    @Data
    public static class Update {
        private String contents;
        private boolean isSecret;
        private Date updated;
    }
}
