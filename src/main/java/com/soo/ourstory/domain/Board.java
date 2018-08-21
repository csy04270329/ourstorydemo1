package com.soo.ourstory.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String contents;

    private Date joined;

    private Date updated;

    private boolean isSecret;

    private String password;
    //작성자
    @ManyToOne
    @JoinColumn(name ="account_id")
    private Account account;

    //댓글

    //올릴 파일

    //카테고리


}
