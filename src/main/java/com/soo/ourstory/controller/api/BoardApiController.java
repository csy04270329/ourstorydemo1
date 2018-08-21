package com.soo.ourstory.controller.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.soo.ourstory.commons.BoardDuplicatedException;
import com.soo.ourstory.commons.BoardNotFoundException;
import com.soo.ourstory.commons.ErrorResponse;
import com.soo.ourstory.domain.Account;
import com.soo.ourstory.domain.Board;
import com.soo.ourstory.dto.AccountDto;
import com.soo.ourstory.dto.BoardDto;
import com.soo.ourstory.repository.BoardRepository;
import com.soo.ourstory.service.BoardService;
import org.modelmapper.ModelMapper;
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
public class BoardApiController {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository repository;

    @Autowired
    ModelMapper mapper;

    @GetMapping("/api/boards")
    public PageImpl<BoardDto.Response> getBoards(Pageable pageable){
        Page<Board> page = repository.findAll(pageable);
        List<BoardDto.Response> boards= page.getContent().parallelStream()
                .map(board->mapper.map(board,BoardDto.Response.class))
                .collect(Collectors.toList());
        return new PageImpl<>(boards,pageable,page.getTotalElements());
    }

    @PostMapping("/api/boards")
    public ResponseEntity createBoard(@RequestBody BoardDto.Create board,
                                      BindingResult result){
        if(result.hasErrors()){
            ErrorResponse error=new ErrorResponse();
            error.setMessage("잘못된 요청입니다.");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        Board newBoard=boardService.createBoard(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoardDto.Response getBoard(@PathVariable("id") Long id){
        Board board = boardService.getBoard(id);
        System.out.println(board);
        return mapper.map(board, BoardDto.Response.class);
    }

    @PutMapping("/api/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateBoard(@PathVariable("id") Long id,@RequestBody BoardDto.Update board,
                                      BindingResult result){
        if(result.hasErrors()){
            ErrorResponse error=new ErrorResponse();
            error.setMessage("잘못된 요청입니다.");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        Board updateBoard=boardService.updateBoard(id,board);
        return new ResponseEntity<>(mapper.map(updateBoard,BoardDto.Update.class),HttpStatus.OK);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBoardNotFoundException(){
        ErrorResponse response= new ErrorResponse();
        return response;
    }

    @ExceptionHandler(BoardDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBoardDuplicatedExceptionn(){
        ErrorResponse response= new ErrorResponse();
        return response;
    }
}
