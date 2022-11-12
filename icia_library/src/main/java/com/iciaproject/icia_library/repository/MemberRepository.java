package com.iciaproject.icia_library.repository;

import com.iciaproject.icia_library.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface MemberRepository
        extends CrudRepository<Member, String> {
    //String findMpwdByMid(String mid);


    Member findMemberByMid(String mid);
    Optional<Member> findById(String mid);

    String findByMpwd (String mid);
    Member findMemberByMid(String mid);


}
