package com.iciaproject.icia_library.repository;


import com.iciaproject.icia_library.entity.Board;
import com.iciaproject.icia_library.entity.BoardFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardFileRepository extends CrudRepository<BoardFile, Long> {
    List<BoardFile> findByBfbid(Board board);
}
