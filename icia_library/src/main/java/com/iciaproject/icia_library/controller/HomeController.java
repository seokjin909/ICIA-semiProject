package com.iciaproject.icia_library.controller;

import com.iciaproject.icia_library.entity.Board;
import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Manager;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.entity.Rent;
import com.iciaproject.icia_library.service.MemberService;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    @GetMapping("bookrtn")
    public ModelAndView bookrtn(HttpSession session){
        log.info("bookrtn()");
        Member member = (Member) session.getAttribute("mem");
        mv = mSev.getRentList(member);
        mv.setViewName("bookrtn");
        return mv;
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
    public ModelAndView searchProc(String tag, String bname) {
        log.info("searchProc()");
        if(tag == null) {
            tag = " ";
            bname = null;
        }
        switch (tag) {
            case "제목":
                mv = mSev.getBook(bname);
                break;
            case "저자":
                mv = mSev.getAuthorBook(bname);
                break;
            case "장르":
                mv = mSev.getTagList(bname);
                break;
            case " ":
                mv = mSev.getBook(bname);
                break;
        }
        return mv;
    }

    @GetMapping("booklist")
    public String booklist() {
        return "booklist";
    }

    @GetMapping("searchTag")
    public ModelAndView searchTag(String tag) {
        log.info("searchTag()");
        mv = mSev.getTagList(tag);
        return mv;
    }

    @GetMapping("bookRent")
    public String bookRent(HttpSession session, String bname, RedirectAttributes rttr) {
        log.info("bookRent()");
        Optional<Member> member = (Optional<Member>) session.getAttribute("mem");
        String view = mSev.bookRent(member, bname, rttr);
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

    // 회원 관리 페이지
    @GetMapping("membercrud")
    public ModelAndView membercrud() {
        mv = mSev.getMemberList();
        return mv;
    }

    // 도서 추가
    @GetMapping("bookInput")
    public String bookInput() {
        return "manager/bookInput";
    }

    @GetMapping("bookInputProc")
    public String bookInputProc(Book book) {
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
    // 회원 삭제
    @GetMapping("deleteMember")
    public String deleteMember(Member mid){
        log.info("deleteMember()");
        String msg = mSev.deleteMember(mid);
        return msg;
    }

    @GetMapping("bookReturn")
    public String bookReturn(String rmember, String rbook, RedirectAttributes rttr) {
        log.info("bookReturn()");
        String view = mSev.bookReturn(rmember, rbook, rttr);
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

    @GetMapping("detailbook")
    public ModelAndView detailbook(int bid) {
        log.info("detailbook()");
        mv = mSev.getDetailBook(bid);
        mv.setViewName("manager/detailbook");
        return mv;
    }

    @GetMapping("updateFrm")
    public ModelAndView updateFrm(long bnum) {
        log.info("updateFrm()");
        mv = mSev.getBoard(bnum);
        System.out.println(mv);
        mv.setViewName("updateFrm");
        return mv;
    }

    @GetMapping("bookUpdate")
    public ModelAndView bookUpdate(int bid) {
        log.info("bookUpdate()");
        mv = mSev.getDetailBook(bid);
        System.out.println(mv);
        mv.setViewName("manager/bookUpdate");
        return mv;
    }


    @GetMapping("memberUpdate")
    public ModelAndView memberUpdate(String mid){
        log.info("memberUpdate()");
        mv = mSev.getDetailMember(mid);
        mv.setViewName("manager/memberUpdate");
        return mv;
    }
    
    @PostMapping("memberUpdateProc")
    public String memberUpdateProc(Member member, RedirectAttributes rttr){
        log.info("memberUpdateProc()");
        String view = mSev.memberUpdate(member, rttr);
        return view;
    }

    @PostMapping("bookUpdateProc")
    public String bookUpdateProc(Book book, RedirectAttributes rttr, HttpSession session) {
        log.info("bookUpdateProc()");
        String view = mSev.bookUpdate(book, rttr, session);
        return view;
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
