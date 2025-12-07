package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.dto.NhanVienDTO;
import com.store.banani.repository.NhanVienRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class NhanVienController {
    private final NhanVienRepository nhanVienRepository;
    public NhanVienController(NhanVienRepository nhanVienRepository){
        this.nhanVienRepository = nhanVienRepository;
    }
    @GetMapping("/employee")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var maChiNhanh = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

        var list = nhanVienRepository.getAllNhanVien(maChiNhanh);
        var result = new ArrayList<NhanVienDTO>();
        for (var i : list){
            var nhanvien = new NhanVienDTO();
            nhanvien.setMaNV((String) i[0]);
            nhanvien.setTenNV((String) i[1]);
            nhanvien.setSdtNV((String) i[4]);
            nhanvien.setEmailNV((String) i[5]);
            nhanvien.setVaiTro((String) i[6]);
            result.add(nhanvien);
        }

        model.addAttribute("nhanviens", result);
        return "nhanvien/employee";
    }
}
