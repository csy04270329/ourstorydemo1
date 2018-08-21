package com.soo.ourstory.controller.api;

import com.soo.ourstory.commons.AccountGroupNotFoundException;
import com.soo.ourstory.commons.DuplicatedAccountGroupException;
import com.soo.ourstory.commons.ErrorResponse;
import com.soo.ourstory.domain.Account;
import com.soo.ourstory.domain.AccountGroup;
import com.soo.ourstory.dto.AccountDto;
import com.soo.ourstory.dto.AccountGroupDto;
import com.soo.ourstory.repository.AccountGroupRepository;
import com.soo.ourstory.repository.AccountRepository;
import com.soo.ourstory.service.AccountGroupApiService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/accountgroup")
public class AccountGroupApiController {

    private Logger logger = LoggerFactory.getLogger(AccountApiController.class);

    @Autowired
    AccountGroupRepository accountGroupRepository;

    @Autowired
    AccountGroupApiService accountGroupApiService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public PageImpl<AccountGroupDto.Response> getAccountGroups(Pageable pageable){
        Page<AccountGroup> page= accountGroupRepository.findAll(pageable);
        List<AccountGroupDto.Response> accounts= page.getContent().parallelStream()
                .map(accountGroup->modelMapper.map(accountGroup,AccountGroupDto.Response.class))
                .collect(Collectors.toList());
        return new PageImpl<>(accounts,pageable,page.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountGroupDto.Response getAccountGroup(@PathVariable Long id){
        AccountGroup group= accountGroupApiService.getAccountGroup(id);
        return modelMapper.map(group,AccountGroupDto.Response.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity createAccountGroup(@RequestBody AccountGroup group, BindingResult result){
        if(result.hasErrors()){
            ErrorResponse errorResponse= new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad.request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        AccountGroup newGroup=accountGroupApiService.createAccountGroup(group);
        return new ResponseEntity<>(modelMapper.map(newGroup,AccountGroupDto.Response.class),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateAccountGroup(@PathVariable Long id,@RequestBody AccountGroup group,
                                             BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AccountGroup updateGroup= accountGroupApiService.updateAccountGroup(id,group);
        return new ResponseEntity<>(modelMapper.map(updateGroup,AccountGroupDto.Response.class),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteAccountGroup(@PathVariable Long id, @RequestBody AccountGroup group,
                                             BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accountGroupApiService.deleteAccountGroup(id,group);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountGroupNotFoundException(AccountGroupNotFoundException e){
        ErrorResponse response= new ErrorResponse();
        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDuplicatedAccountGroupException(DuplicatedAccountGroupException e){
        ErrorResponse response= new ErrorResponse();
        return response;
    }
}
