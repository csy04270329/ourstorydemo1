package com.soo.ourstory.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "account_group")
public class AccountGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //멤버 2명
    @OneToMany(mappedBy = "accountGroup",
            cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Account> accounts;


}
