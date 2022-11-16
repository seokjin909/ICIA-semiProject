package com.iciaproject.icia_library.repository;

import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.entity.Rent;
import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<Rent, Long> {

    Rent findByRmember(Member member);
}
