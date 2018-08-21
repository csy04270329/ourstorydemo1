package com.soo.ourstory.controller.api;


import com.soo.ourstory.commons.AccountNotFoundException;
import com.soo.ourstory.commons.ErrorResponse;
import com.soo.ourstory.commons.UserDuplicatedException;
import com.soo.ourstory.domain.Account;
import com.soo.ourstory.domain.AccountGroup;
import com.soo.ourstory.dto.AccountDto;
import com.soo.ourstory.repository.AccountRepository;
import com.soo.ourstory.service.AccountApiService;
import com.soo.ourstory.service.AccountGroupApiService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Getter
@Setter
public class AccountApiController {

    private Logger logger= LoggerFactory.getLogger(AccountApiController.class);

    @Autowired
    private AccountApiService service;

    @Autowired
    private AccountGroupApiService groupApiService;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(value = "/api/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity addAccountGroup(@PathVariable("id") Long id,
                                          @RequestBody AccountGroup accountGroup,
                                          BindingResult result){

        if(result.hasErrors()){
            ErrorResponse errorResponse= new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad.request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Account getAccount= service.getAccount(id);
        getAccount.setAccountGroup(accountGroup);
        service.updateAccount(id,getAccount);
        logger.debug("Account Update  : {}", service.getAccount(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //converter가 json 문자열이 account 객체로..
    @PostMapping("/api/accounts")
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create,
                                        BindingResult result){
        logger.debug("createAccount  : {}", create);
        if(result.hasErrors()){
            ErrorResponse errorResponse= new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad.request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Account newAccount=service.createAccount(create);
        return new ResponseEntity<>(modelMapper.map(newAccount,AccountDto.Response.class), HttpStatus.CREATED);
    }

    @GetMapping("/api/accounts")
    @ResponseStatus(HttpStatus.OK)
    public PageImpl<AccountDto.Response> getAccounts(Pageable pageable){
        Page<Account> page= repository.findAll(pageable);
        List<AccountDto.Response> content=page.getContent().parallelStream()
                .map(account -> modelMapper.map(account,AccountDto.Response.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content,pageable,page.getTotalElements());
    }

    //by userId
    @GetMapping("/api/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto.Response getAccount(@PathVariable Long userId){
        Account account= service.getAccount(userId);
        return modelMapper.map(account,AccountDto.Response.class);
    }

    @RequestMapping(value = "/api/accounts/{id}",method = RequestMethod.PUT)
    public ResponseEntity updateAccount(@PathVariable Long userId,
                                        @RequestBody @Valid AccountDto.Update updateDto,
                                        BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account updatedAccount= service.updateAccount(userId,updateDto);
        return new ResponseEntity<>(modelMapper.map(updatedAccount,AccountDto.Response.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/accounts/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable Long userId){
        service.deleteAccount(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    ///어떻게 front단으로 넘길지 고민중...질문해보자..

    @PostMapping("/api/accounts/login")
    @ResponseStatus(HttpStatus.OK)
    public String loginAccount(@RequestBody Account account){
        AccountDto.Response getAccount=getAccount(account.getId());
        logger.debug("getAccount: {}" ,getAccount.getUsername());

        if(getAccount==null){
            return "login";
        }
        else{
            return "list";
        }
    }

    @ExceptionHandler(UserDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserDuplicatedException(UserDuplicatedException e){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setMessage("["+e.getUsername()+"] 중복된 username 입니다.");
        errorResponse.setCode("duplicated.username.exception");
        return errorResponse;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setMessage("["+e.getUserId()+"]에 해당하는 계정이 없습니다.");
        errorResponse.setCode("account.not.found.exception");
        return errorResponse;
    }
}
