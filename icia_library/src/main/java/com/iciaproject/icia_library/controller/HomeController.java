package com.iciaproject.icia_library.controller;

import com.iciaproject.icia_library.entity.Board;
import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Manager;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@Log
public class HomeController {
    @Autowired
    private MemberService mSev;

    ModelAndView mv;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 로그인 페이지
    @GetMapping("login")
    public String login() {
        return "login";
    }

    // 관리자 로그인 페이지
    @GetMapping("login_manager")
    public String login_manager() {
        return "manager/login_manager";
    }

    // 로그아웃
    @GetMapping("logoutProc")
    public String logoutProc(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 회원가입 페이지
    @GetMapping("signup")
    public String signup() {
        return "signup";
    }

    // 회원가입
    @PostMapping("joinProc")
    public String joinProc(Member member, HttpSession session, RedirectAttributes rttr) {
        log.info("joinProc()");
        String view = mSev.memberJoin(member, session, rttr);
        return view;
    }

    @GetMapping("searchProc")
    public ModelAndView searchProc(String bname) {
        log.info("searchProc()");
        mv = mSev.getBook(bname);
        return mv;
    }

    @GetMapping("searchTag")
    public ModelAndView searchTag(String tag) {
        log.info("searchTag()");
        mv = mSev.getTagList(tag);
        return mv;
    }

    @GetMapping("bookRent")
    public String bookRent(Member member, Book book, RedirectAttributes rttr) {
        log.info("bookLent()");
        String view = mSev.bookRent(member, book, rttr);
        return view;
    }

    @GetMapping("part")
    public ModelAndView part(Integer pageNum, HttpSession session) {
        log.info("part()");
        mv = mSev.getBoardList(pageNum, session);
        mv.setViewName("part");
        return mv;
    }

    @GetMapping("writeFrm")
    public String writefrm() {
        log.info("writeFrm()");
        return "writeFrm";
    }

    @PostMapping("writeProc")
    public String writeProc(
            Board board, HttpSession session, RedirectAttributes rttr) {
        log.info("writeProc()");
        String view = mSev.insertBoard(board, session, rttr);
        return view;
    }

    // 도서 관리 페이지
    @GetMapping("bookcrud")
    public ModelAndView bookcrud() {
        mv = mSev.getBookList();
        return mv;
    }

    // 도서 추가
    @GetMapping("bookInput")
    public String bookInput() {
        return "manager/bookInput";
    }

    @GetMapping ("bookInputProc")
    public String bookInputProc(Book book){
        String msg = mSev.inputBook(book);
        return msg;
    }


    // 도서 삭제
    @GetMapping("deleteBook")
    public String deleteBook(Book bid) {
        log.info("deleteBook()");
        String msg = mSev.deleteBook(bid);
        return msg;
    }

    @GetMapping("bookReturn")
    public String bookReturn(Member member, Book book, RedirectAttributes rttr) {
        log.info("bookReturn()");
        String view = mSev.bookReturn(member, book, rttr);
        return view;
    }

    // 로그인 처리
    @PostMapping("logProc")
    public String logProc(Member member, HttpSession session, RedirectAttributes rttr) {
        log.info("logProc()");
        String view = mSev.memberLogin(member, session, rttr);
        return view;
    }

    @GetMapping("detail")
    public ModelAndView detail(long bnum) {
        log.info("detail()");
        mv = mSev.getBoard(bnum);
        mv.setViewName("detail");
        return mv;
    }

    @GetMapping("updateFrm")
    public ModelAndView updateFrm(long bnum) {
        log.info("updateFrm()");
        mv =mSev.getBoard(bnum);
        mv.setViewName("updateFrm");
        return mv;
    }

    @PostMapping("updateProc")
    public String updateProc(Board board, HttpSession session, RedirectAttributes rttr) {
        log.info("updateProc");
        String view = mSev.boardUpdate(board, session, rttr);
        return view;
    }

    @GetMapping("delete")
    public String delete(long bnum, HttpSession session, RedirectAttributes rttr) {
        log.info("delete()");
        String view = mSev.boardDelete(bnum, session, rttr);
        return view;
    }

    // 관리자 로그인 처리
    @PostMapping("logProcM")
    public String logProcM(Manager manager, HttpSession session, RedirectAttributes rttr) {
        log.info("logProcM()");
        String view = mSev.managerLogin(manager, session, rttr);
        return view;
    }

    // 관리자 메인페이지
    @GetMapping("home_manager")
    public String home_manager() {
        log.info("home_manager()");
        return "manager/home_manager";
    }


}
