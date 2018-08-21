package com.soo.ourstory.service;

import com.soo.ourstory.domain.Board;
import com.soo.ourstory.dto.BoardDto;

public interface BoardService {

    Board createBoard(BoardDto.Create board);

    Board getBoard(Long id);

    Board updateBoard(Long id, BoardDto.Update board);
}
