package com.soo.ourstory.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name="username")
    private  String userName;

    @Column(name="userpw")
    private String userPw;

    @Column(name="useremail")
    private String userEmail;

    private  String fullName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joined;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    private boolean isAdmin;

    ///group에 속해있는 id.. default값도 줘야하남..
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_group_id")
    private AccountGroup accountGroup;

    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boards;
}
