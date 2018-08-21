package com.soo.ourstory.repository;

import com.soo.ourstory.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
