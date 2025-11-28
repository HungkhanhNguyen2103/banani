package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.model.TAIKHOAN;
import com.store.banani.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AccountController {
    private UserRepository _repo;

    public AccountController(UserRepository repo){
        _repo = repo;
    }

    @GetMapping("/account/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new TAIKHOAN());
        model.addAttribute("error", "");
        return "account/login";
    }

    @PostMapping("/account/login")
    public String handleLogin(HttpServletResponse response, @ModelAttribute("user") TAIKHOAN user, Model model) {
        try {
            Object[] foundUser = _repo.findByCredentials(user.getMaTaiKhoan(), user.getMatKhau());
            if (foundUser == null) {
                model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
                return "account/login";
            }
            Object[] row = (Object[])foundUser[0];
            CookieUtils.setCookie(response,CookieUtils.loggedInUser,"true");
            CookieUtils.setCookie(response,CookieUtils.username,(String) row[0]);
            CookieUtils.setCookie(response,CookieUtils.maNV,(String) row[1]);
            CookieUtils.setCookie(response,CookieUtils.tenNV, (String) row[2]);
            CookieUtils.setCookie(response,CookieUtils.vaiTro,(String) row[3]);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "account/login";
        }
    }

    @GetMapping("/account/register")
    public String register() {
        return "account/register";
    }
}
