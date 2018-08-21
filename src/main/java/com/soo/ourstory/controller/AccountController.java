package com.soo.ourstory.controller;

import com.soo.ourstory.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping
    public String list(ModelMap modelMap){
        //parameter의 modelMap은 controller를 실행해주는 handler adapter가 넣어주게
        //modelMap에 전달된 애들을 tymeleaf까지 넘겨버린다.
        modelMap.addAttribute("name","kim");
        //./resource/templates 파일에서 tymeleaf template을 찾아
        //viewName 리턴
        List<Account> list=new ArrayList<>();

        return "list";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signin")
    public String signin(){
        return "signin";
    }
}
