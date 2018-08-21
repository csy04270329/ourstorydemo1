package com.soo.ourstory.controller;


import com.soo.ourstory.domain.Board;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/boards")
public class BoardController {

    @GetMapping("/list")
    public String getBoards(){
        return "board_list";
    }
}
