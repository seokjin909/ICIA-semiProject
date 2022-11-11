package com.iciaproject.icia_library.service;


import com.iciaproject.icia_library.entity.Board;
import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.repository.BoardRepository;
import com.iciaproject.icia_library.repository.BookRepository;
import com.iciaproject.icia_library.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
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

    @Autowired
    private BoardRepository boRepo;

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


//    @Transactional
//    public String insertBoard(List<MultipartFile> files, Board board, HttpSession session, RedirectAttributes rttr) {
//        log.info("insertBoard()");
//        String msg = null;
//        String view = null;
//
//        try {
//            boRepo.save(board);
//            log.info("bnum : " + board.getBnum());
//            fileUpload(files, session, board);
//
//            view = "redirect:/";
//            msg = "저장 성공";
//
//        }catch (Exception e){
//            e.printStackTrace();
//            view = "redirect:writeFrm";
//            msg = "저장 실패";
//        }
//        rttr.addFlashAttribute("msg", msg);
//
//        return view;
//    }
//
//    private void fileUpload(List<MultipartFile> files, HttpSession session, Board board) {
//        log.info("fileUpload()");
//        String realPath = session.getServletContext().getRealPath("/");
//        log.info("realPath: " + realPath);
//
//        realPath +="upload/";
//        File folder = new File(realPath);
//        if (folder.isDirectory() == false){
//            folder.mkdir();
//        }
//        for (MultipartFile mf : files){
//            String orname = mf.getOriginalFilename();
//            return;
//        }
//
//        Board b = new Board();
//
//    }
}
