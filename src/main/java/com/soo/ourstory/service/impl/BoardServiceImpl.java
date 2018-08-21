package com.soo.ourstory.service.impl;

import com.soo.ourstory.commons.BoardDuplicatedException;
import com.soo.ourstory.commons.BoardNotFoundException;
import com.soo.ourstory.domain.Board;
import com.soo.ourstory.dto.BoardDto;
import com.soo.ourstory.repository.BoardRepository;
import com.soo.ourstory.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Board createBoard(BoardDto.Create board) {
        Board newBoard= modelMapper.map(board,Board.class);
        Board getBoard= boardRepository.getOne(newBoard.getId());
        if(getBoard!=null){
            throw new BoardDuplicatedException(newBoard.getId());
        }
        newBoard.setPassword(passwordEncoder.encode(board.getPassword()));
        Date now= new Date();
        newBoard.setJoined(now);
        newBoard.setUpdated(now);
        return boardRepository.save(newBoard);
    }

    @Override
    public Board getBoard(Long id) {
        Board board= boardRepository.getOne(id);
        System.out.println("**************" +board+ "***************");
        if(board==null) throw new BoardNotFoundException(id);
        return board;
    }

    @Override
    public Board updateBoard(Long id, BoardDto.Update board){
        Board getBoard= boardRepository.getOne(id);
        getBoard.setUpdated(new Date());
        getBoard.setContents(board.getContents());
        return boardRepository.save(getBoard);
    }


}
