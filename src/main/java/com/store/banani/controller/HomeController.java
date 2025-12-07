package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.dto.SanPhamDTO;
import com.store.banani.model.TAIKHOAN;
import com.store.banani.repository.SanPhamRepository;
import com.store.banani.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class HomeController {
    private final SanPhamRepository sanPhamRepository;


    public HomeController(SanPhamRepository sanPhamRepository) {
        this.sanPhamRepository = sanPhamRepository;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        var vaiTro = CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        model.addAttribute("vaiTro", vaiTro);
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        if(vaiTro.equals("Thu ngân")){
            return "redirect:/statistical/0";
        }

        if(vaiTro.equals("Admin")){
            return "redirect:/branch";
        }

        if(vaiTro.equals("Phục vụ")){
            return "redirect:/table";
        }

        var listSP = sanPhamRepository.findAllSanPham();
        var listResult = new ArrayList<SanPhamDTO>(){};
        for(var item : listSP){
            var sp = new SanPhamDTO();
            sp.setMaSP((String) item[0]);
            sp.setTenSP((String) item[1]);
            sp.setMaLSP((String) item[2]);
            BigDecimal big = (BigDecimal) item[3];
            sp.setDonGia(big.floatValue());
            sp.setDonviTinh((String) item[4]);
            sp.setTrangThai((String) item[5]);
            sp.setHinhAnh((String) item[6]);
            sp.setTenLoaiSP((String) item[8]);

            if(sp.getHinhAnh().isEmpty()) sp.setHinhAnh("http://localhost:8080/image/no-image.png");

            listResult.add(sp);
        }
        model.addAttribute("listResult", listResult);

        return "sanpham/product";
    }

    @GetMapping("/account/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.removeCookie(response,CookieUtils.maNV);
        CookieUtils.removeCookie(response,CookieUtils.tenNV);
        CookieUtils.removeCookie(response,CookieUtils.username);
        CookieUtils.removeCookie(response,CookieUtils.vaiTro);
        CookieUtils.removeCookie(response,CookieUtils.loggedInUser);
        CookieUtils.removeCookie(response,CookieUtils.chiNhanh);
        CookieUtils.removeCookie(response,CookieUtils.machiNhanh);
        return "redirect:/";
    }

}
