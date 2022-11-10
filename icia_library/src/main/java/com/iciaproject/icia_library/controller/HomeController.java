package com.iciaproject.icia_library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 로그인 페이지
    @GetMapping("lonIn")
    public String login() {
        return "login";
    }

    // 회원가입 페이지
    @GetMapping("signUp")
    public String signup() {
        return "signup";
    }


}
