package com.soo.ourstory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soo.ourstory.commons.BoardDuplicatedException;
import com.soo.ourstory.domain.Account;
import com.soo.ourstory.domain.Board;
import com.soo.ourstory.dto.AccountDto;
import com.soo.ourstory.dto.BoardDto;
import com.soo.ourstory.repository.AccountRepository;
import com.soo.ourstory.repository.BoardRepository;
import com.soo.ourstory.service.AccountApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardApiControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    BoardRepository repository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mock;

    @Before
    public void init(){
        mock= MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void getBoards_success(){

    }

    @Test
    public void CreateBoard_Success(){
        Account account = new Account();
        account.setId(1L);
        account.setUserPw("12345");
        account.setFullName("user1");
        account.setUserName("user1");

        BoardDto.Create board= new BoardDto.Create();
        board.setAccount(account);
        board.setPassword("12345");
        board.setTitle("test1");
        board.setContents("This is my test1 contents");
        try {
            ResultActions resultActions= mock.perform(post("/api/boards").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(board)));
            resultActions.andDo(print());
            resultActions.andExpect(status().isCreated());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(expected = BoardDuplicatedException.class)
    public void CreateBoard_DuplicatedError(){
        Account account = new Account();
        account.setId(1L);
        account.setUserPw("12345");
        account.setFullName("user1");
        account.setUserName("user1");

        BoardDto.Create board= new BoardDto.Create();
        board.setAccount(account);
        board.setPassword("12345");
        board.setTitle("test1");
        board.setContents("This is my test1 contents");


        try {
            ResultActions resultActions= mock.perform(post("/api/boards").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(board)));

            ResultActions resultActions1= mock.perform(post("/api/boards").
                    contentType(MediaType.APPLICATION_JSON).
                    content(objectMapper.writeValueAsString(board)));

            resultActions1.andDo(print());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBoard_Success(){
        try {
            ResultActions resultActions= mock.
                    perform(get("/api/boards/{id}",1L));
            resultActions.andDo(print());
            resultActions.andExpect(status().isOk());
            //assertEquals()

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateBoard_Success(){
        BoardDto.Update board=new BoardDto.Update();
        board.setContents("changed1");
        board.setSecret(true);

        try {
            ResultActions resultActions= mock.perform(put("/api/boards/{id}",3L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(board)));
            resultActions.andDo(print());
            resultActions.andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
