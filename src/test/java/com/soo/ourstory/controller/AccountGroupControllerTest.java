package com.soo.ourstory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soo.ourstory.domain.Account;
import com.soo.ourstory.domain.AccountGroup;
import com.soo.ourstory.repository.AccountGroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountGroupControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountGroupRepository group;

    MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createAccountGroup(){
        Account account1=new Account();
        account1.setId(1L);
        account1.setUserPw("12345");
        account1.setFullName("test1");

        Account account2=new Account();
        account2.setId(2L);
        account2.setUserPw("12345");
        account2.setFullName("test2");

        List<Account> list= new ArrayList<>();
        list.add(account1);
        list.add(account2);
        AccountGroup newGroup= new AccountGroup();
        newGroup.setId(1L);
        newGroup.setAccounts(list);

        try {
            ResultActions result= mockMvc.perform(post("/api/accountgroup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newGroup)));
            result.andDo(print());
            result.andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
