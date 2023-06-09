package com.iciaproject.icia_library.repository;

import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.entity.Rent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentRepository extends CrudRepository<Rent, Long> {

    Rent findByRmember(Member member);
    Rent findByRbook(Book book);
    List<Rent> findAllByRmember(Member member);


    List<Rent> findAllByRmemberOrderByRsdate(Member member);
}
