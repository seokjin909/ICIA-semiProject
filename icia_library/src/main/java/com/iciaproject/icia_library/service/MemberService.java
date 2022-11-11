package com.iciaproject.icia_library.service;


import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.repository.BookRepository;
import com.iciaproject.icia_library.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Log
public class MemberService {
    @Autowired
    public MemberRepository mRepo;

    @Autowired
    private BookRepository bRepo;

    ModelAndView mv;

    public String memberJoin(Member member, HttpSession session, RedirectAttributes rttr) {
        log.info("memberJoin()");
        String msg = null;
        String view = null;
        Optional<Member> dbMem = mRepo.findById(member.getMid());

        if (dbMem.isEmpty()) { // 입력한 아이디가 있을 경우
            try {
                mRepo.save(member);
                msg = "가입 성공";
                view = "redirect:/";
            } catch (Exception e) {
                e.printStackTrace();
                msg = "가입 실패";
                view = "redirect:/";
            }
        } else {
            msg = "중복된 아이디입니다.";
            view = "redirect:/";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }


    public ModelAndView getBook(String bname) {
        log.info("getBook()");
        mv = new ModelAndView();
        Collection<Book> gbook = bRepo.findByBnameLike("%bname%");
        mv.addObject("book", gbook);

        return mv;
    }

    public String memberLogin(Member member, HttpSession session, RedirectAttributes rttr) {
        log.info("memberLogin()");
        String msg = null;
        String view = null;
        String mempwd = mRepo.findMemberByMpwd(member.getMid());

        if (mempwd != null) {
            if (mempwd.equals(member.getMpwd())) {
                member = mRepo.findMemberBy(member.getMid());
                session.setAttribute("mem", member);
                msg = "로그인 성공";
                view = "redirect:/";
            } else {
                msg = "로그인 실패";
                view = "redirect:/";
            }
            rttr.addFlashAttribute("msg", msg);

        }
        return view;
    }
}
