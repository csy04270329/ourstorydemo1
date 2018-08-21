package com.soo.ourstory.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDuplicatedException extends RuntimeException {

    private Long boardId;

    public BoardDuplicatedException(Long id) {
        boardId=id;
    }
}
