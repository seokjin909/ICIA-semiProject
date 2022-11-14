package com.iciaproject.icia_library.repository;

import com.iciaproject.icia_library.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Collection<Book> findByBnameLike(String bname);


    //List<Book> findAllByTag(String tag);
}
