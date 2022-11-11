package com.iciaproject.icia_library.controller;

import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ModelAndView searchProc(String bname) {
        log.info("searchProc()");
        mv = mSev.getBook(bname);
        mv.setViewName("book");
        return mv;
    }

    // 도서 관리 페이지
    @GetMapping("bookcrud")
    public String bookcrud() {
        return "bookcrud";
    }

    // 도서 추가
    @GetMapping("bookinput")
    public String bookinput(Book book){
        String view = mSev.inputBook(book);
        return view;
    }

    // 도서 목록
    @RequestMapping("booklist")
    public @ResponseBody List<Book> bookList(){
        return mSev.getBookList();
    }

    // 도서 삭제
    @GetMapping("deleteBook")
    public String deleteBook(Book book){
        log.info("deleteBook()");
        String msg = mSev.deleteBook(book);
        return msg;
    }
}
