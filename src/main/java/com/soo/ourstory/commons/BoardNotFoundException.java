package com.soo.ourstory.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardNotFoundException extends RuntimeException {

    private Long boardId;

    public BoardNotFoundException(Long id) {
        boardId=id;
    }

}
