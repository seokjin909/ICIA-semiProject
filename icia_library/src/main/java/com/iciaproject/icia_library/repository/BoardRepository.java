package com.iciaproject.icia_library.repository;
import com.iciaproject.icia_library.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface BoardRepository extends CrudRepository<Board, Long>{
    Page<Board>findByBnumGreaterThan(long bnum, Pageable pageable);
}
