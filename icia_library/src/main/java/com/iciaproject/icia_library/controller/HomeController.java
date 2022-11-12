package com.iciaproject.icia_library.controller;

import com.iciaproject.icia_library.entity.Board;
import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
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

    // 회원가입 페이지
    @GetMapping("signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("searchProc")
    public ModelAndView searchProc(String bname){
        log.info("searchProc()");
        mv = mSev.getBook(bname);
        mv.setViewName("book");
        return mv;
    }
/*
    @GetMapping("searchTag")
    public ModelAndView searchTag(String tag){
        log.info("searchTag()");
        mv = mSev.getList(tag);
        return mv;
    }*/
    @GetMapping("bookRent")
    public String bookRent(Member member,Book book, RedirectAttributes rttr){
        log.info("bookLent()");
        String view = mSev.bookRent(member, book, rttr);
        return view;
    }
    @GetMapping("part")
    public String part(){
        log.info("part()");
        return "part";
    }

    @GetMapping("writeFrm")
    public String writeFrm(){
        log.info("writeFrm()");
        return "writeFrm";
    }

//    @PostMapping("writeProc")
//    public String writeProc(@RequestPart List<MultipartFile> files,
//                            Board board, HttpSession session, RedirectAttributes rttr){
//        log.info("writeProc()");
//        String view = mSev.insertBoard(files, board, session, rttr);
//
//        return view;
//    }

    @GetMapping("bookReturn")
    public String bookReturn(Member member, Book book, RedirectAttributes rttr){
        log.info("bookReturn()");
        String view = mSev.bookReturn(member, book, rttr);

        return view;
    }
}
