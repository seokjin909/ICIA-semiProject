package com.iciaproject.icia_library.service;

import com.iciaproject.icia_library.entity.Board;
import com.iciaproject.icia_library.entity.Book;
import com.iciaproject.icia_library.entity.Manager;
import com.iciaproject.icia_library.entity.Member;
import com.iciaproject.icia_library.entity.Rent;
import com.iciaproject.icia_library.repository.BoardRepository;
import com.iciaproject.icia_library.repository.BookRepository;
import com.iciaproject.icia_library.repository.ManagerRepository;
import com.iciaproject.icia_library.repository.MemberRepository;
import com.iciaproject.icia_library.repository.RentRepository;
import com.iciaproject.icia_library.util.PagingUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;

import java.io.File;
import java.util.Optional;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Log
public class MemberService {
    @Autowired
    public MemberRepository mRepo;

    @Autowired
    private BookRepository bRepo;

    @Autowired
    private BoardRepository boRepo;

    @Autowired
    private RentRepository rRepo;


    @Autowired
    private ManagerRepository mnRepo;


//    @Autowired
//    private BoardFileRepository bfRepo;

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
        mv.clear();
        String searchName = "%" + bname + "%";
        try {
            List<Book> gbook = (List<Book>) bRepo.findByBnameLike(searchName);
            mv.addObject("gbook", gbook);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.setViewName("booklist");
        return mv;
    }

    public ModelAndView getAuthorBook(String bauthor) {
        log.info("getAuthorBook()");
        mv = new ModelAndView();
        mv.clear();
        String searchAuthor = "%" + bauthor + "%";
        try {
            List<Book> gbook = bRepo.findByBauthorLike(searchAuthor);
            mv.addObject("gbook", gbook);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.setViewName("booklist");
        return mv;
    }

    public ModelAndView getRentList(Member member){
        log.info("getRentList()");
    try {
        Member m = mRepo.findByMname(member.getMname());
        List<Rent> rentList = rRepo.findAllByRmember(m);
        mv = new ModelAndView();
        mv.addObject("rentList",rentList);
    }
    catch (Exception e){
        e.printStackTrace();
    }
        return mv;
    }


    public String memberLogin(Member member, HttpSession session, RedirectAttributes rttr) {
        log.info("memberLogin()");
        String msg = null;
        String view = null;

        Optional<Member> mem = mRepo.findById(member.getMid());
        try {
            Member m2 = mem.get();
            if (m2.getMpwd().equals(member.getMpwd())) {
                msg = "로그인 성공";
                member = m2;
                session.setAttribute("mem", member);
                view = "redirect:/";
            } else {
                msg = "비밀번호 오류";
                view = "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "로그인 불가능";
            view = "redirect:/";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // 도서 추가 메소드
    public String inputBook(Book book) {
        String view = null;
        try {
            bRepo.save(book);
            view = "redirect:bookInput";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:bookInput";
        }
        return view;
    }

    // 도서 목록 출력 메소드
    public ModelAndView getBookList() {
        mv = new ModelAndView();
        mv.setViewName("manager/bookcrud");

        List<Book> bList = new ArrayList<>();
        Iterable<Book> bIter = bRepo.findAll();

        for (Book b : bIter) {
            bList.add(b);
        }

        mv.addObject("blist", bList);
        return mv;
    }

    // 도서 삭제 메소드
    @Transactional
    public String deleteBook(Book bid) {
        String msg = null;
        try {
            bRepo.delete(bid);
            msg = "삭제 성공";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "삭제 실패";
        }
        return msg;
    }


    @Transactional
    public String insertBoard(Board board, HttpSession session, RedirectAttributes rttr) {
        log.info("insertBoard()");
        String msg = null;
        String view = null;

        try {
            boRepo.save(board);
            log.info("bnum : " + board.getBnum());
//            fileUpload(files, session, board);

            view = "redirect:part";
            msg = "저장 성공";

        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:writeFrm";
            msg = "저장 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    public ModelAndView getBoardList(Integer pageNum, HttpSession session) {
        log.info("getBoardList()");
        mv = new ModelAndView();

        if (pageNum == null) {
            pageNum = 1;
        }

        int listCnt = 5;    // 페이지당 보여질 게시글
        //페이징 조건 생성
        Pageable pb = PageRequest.of((pageNum - 1), listCnt,
                Sort.Direction.DESC, "bnum");

        Page<Board> result = boRepo.findByBnumGreaterThan(0L, pb);
        List<Board> bList = result.getContent();
        int totalPage = result.getTotalPages();
        String paging = getPaging(pageNum, totalPage);
        mv.addObject("bList", bList);
        mv.addObject("paging", paging);

        session.setAttribute("pageNum", pageNum);

        return mv;
    }

    //페이징 처리 메소드
    private String getPaging(Integer pageNum, int totalPage) {
        String pageHtml = null;
        int pageCnt = 2; //보여질 페이지 개수
        String listName = "?";

        PagingUtil paging = new PagingUtil(totalPage, pageNum, pageCnt, listName);

        pageHtml = paging.makePaging();

        return pageHtml;
    }

    public ModelAndView getBoard(long bnum) {
        log.info("getBoard()");
        mv = new ModelAndView();
        Board board = boRepo.findById(bnum).get();
        mv.addObject("board", board);

        return mv;
    }

    @Transactional
    public String boardUpdate(Board board, HttpSession session, RedirectAttributes rttr) {
        log.info("boardUpdate()");
        String msg = null;
        String view = null;

        try {
            boRepo.save(board);

            msg = "수정 성공";
            view = "redirect:detail?bnum=" + board.getBnum();
        } catch (Exception e) {
            e.printStackTrace();
            msg = "수정 실패";
            view = "redirect:updateFrm?bnum=" + board.getBnum();
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    @Transactional
    public String boardDelete(long bnum, HttpSession session, RedirectAttributes rttr) {
        log.info("boardDelete()");
        String msg = null;
        String view = null;

        try {
            boRepo.deleteById(bnum);
            msg = "삭제 성공";
            view = "redirect:part";
        } catch (Exception e) {
            msg = "삭제 실패";
            view = "redirect:detail?bnum=" + bnum;
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }


    private void fileUpload(List<MultipartFile> files, HttpSession session, Board board) {
        log.info("fileUpload()");
        String realPath = session.getServletContext().getRealPath("/");
        log.info("realPath: " + realPath);

        realPath += "upload/";
        File folder = new File(realPath);
        if (folder.isDirectory() == false) {
            folder.mkdir();
        }
        for (MultipartFile mf : files) {
            String orname = mf.getOriginalFilename();
            if (orname.equals("")) {
                return;
            }
            Board b = new Board();
        }
    }


    @Transactional
    public ModelAndView getTagList(String tag) {
        log.info("getTagList()");
        mv = new ModelAndView();
        mv.clear();
        if (tag != null) {
            try {
                List<Book> gbook = bRepo.findByBtag(tag);
                mv.addObject("gbook", gbook);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            List<Book> gbook = (List<Book>) bRepo.findAll();
            mv.addObject("gbook", gbook);
        }
        mv.setViewName("booklist");
        return mv;
    }


    // Book Rental Function
    @Transactional
    public String bookRent(String mname, String bname, RedirectAttributes rttr) {
        log.info("bookRent()");
        String msg = null;
        String view = null;

        Member member = mRepo.findByMname(mname);
        Book book = bRepo.findByBname(bname);
        Rent rent = new Rent();
        if (member.getCount() < 5) {
            try {
                // 대여일 계산
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                String sdate = sdf.format(cal.getTime());

                rent.setRmember(member);
                rent.setRbook(book);
                rent.setRsdate(sdate);

                // 반납일 계산
                cal.add(Calendar.DATE, 7);
                String edate = sdf.format(cal.getTime());
                rent.setRedate(edate);

                // 현재 user의 대여수 증가
                member.setCount(member.getCount() + 1);

                rRepo.save(rent);
                mRepo.save(member);
                bRepo.save(book);
                msg = "대여 성공";
                view = "redirect:/";
            } catch (Exception e) {
                e.printStackTrace();
                msg = "대여 실패";
                view = "redirect:/";
            }
        } else {
            msg = "대여 가능 수를 초과하였습니다.";
            view = "redirect:/";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // Book Return Function
    @Transactional
    public String bookReturn(String mname, String bname, RedirectAttributes rttr) {
        log.info("bookReturn()");
        String msg = null;
        String view = null;
        Member member = mRepo.findByMname(mname);
        Book book = bRepo.findByBname(bname);
        Rent rent = rRepo.findByRmember(member);

        if (member.getCount() > 0) {
            try {
                book.setBlent(false);

                member.setCount(member.getCount() - 1);
                bRepo.save(book);
                mRepo.save(member);
                rRepo.delete(rent);
                msg = "반납 성공";
                view = "redirect:/";
            } catch (Exception e) {
                e.printStackTrace();
                msg = "반납 실패";
                view = "redirect:/";
            }
        } else {
            msg = "대여하신 책이 없습니다.";
            view = "redirect:/";
        }

        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // 관리자 로그인 메소드
    public String managerLogin(Manager manager, HttpSession session, RedirectAttributes rttr) {
        log.info("managerLogin()");
        String msg = null;
        String view = null;

        Optional<Manager> man = mnRepo.findById(manager.getManagerid());
        try {
            Manager man2 = man.get();
            if (man2.getManagerpwd().equals(manager.getManagerpwd())) {
                msg = "관리자 로그인 성공";
                manager = man2;
                session.setAttribute("man", manager);
                view = "redirect:home_manager";
            } else {
                msg = "비밀번호 오류";
                view = "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "로그인 불가능";
            view = "redirect:/";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public ModelAndView getDetailBook(int bid) {
        log.info("getDetailBook()");
        mv = new ModelAndView();
        Book book = bRepo.findById(bid).get();
        mv.addObject("book", book);
        return mv;
    }

    public String bookUpdate(Book book, RedirectAttributes rttr, HttpSession session) {
        log.info("bookUpdate()");
        String msg = null;
        String view = null;

        try {
            bRepo.save(book);
            msg = "수정 성공";
            view = "redirect:detailbook?bid=" + book.getBid();
        } catch (Exception e) {
            e.printStackTrace();
            msg = "수정 실패";
            view = "redirect:bookUpdate?bid="+book.getBid();
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public ModelAndView getMemberList() {
        mv = new ModelAndView();
        mv.setViewName("manager/membercrud");

        List<Member> mList = new ArrayList<>();
        Iterable<Member> mIter = mRepo.findAll();

        for (Member m : mIter) {
            mList.add(m);
        }

        mv.addObject("mList", mList);
        return mv;
    }

    public String deleteMember(Member mid) {
        String msg = null;
        try {
            mRepo.delete(mid);
            msg = "삭제 성공";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "삭제 실패";
        }
        return msg;
    }

    public ModelAndView getDetailMember(String mid) {
        log.info("getDetailMember()");
        mv = new ModelAndView();
        Member member = mRepo.findById(mid).get();
        System.out.println(member);
        mv.addObject("member", member);
        return mv;
    }

    public String memberUpdate(Member member, RedirectAttributes rttr) {
        log.info("memberUpdate()");
        String msg = null;
        String view = null;

        try {
            mRepo.save(member);
            msg = "수정 성공";
            view = "redirect:membercrud";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "수정 실패";
            view = "redirect:memberUpdate?mid=" + member.getMid();
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }
}
