package com.iciaproject.icia_library.controller;

import com.iciaproject.icia_library.entity.Board;
import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    @GetMapping("bookinput")
    public String bookinput(Book book) {
        String view = mSev.inputBook(book);
        return view;
    }

    // 도서 목록
//    @RequestMapping("booklist")
//    public @ResponseBody List<Book> bookList(){
//        return mSev.getBookList();
//    }

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

}
