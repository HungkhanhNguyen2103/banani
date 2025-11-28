package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.dto.BanDTO;
import com.store.banani.dto.ChiNhanhDTO;
import com.store.banani.dto.NguyenLieuDTO;
import com.store.banani.repository.BanRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class BanController {
    private final BanRepository repository;

    public BanController(BanRepository repository){
        this.repository = repository;
    }

    @GetMapping("/table")
    public String table(Model model, HttpServletRequest request) {

        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));

        var result = repository.findAllItem();
        var listResult = new ArrayList<BanDTO>();
        for (var item : result){
            var b = new BanDTO();
            b.setMaBAN((String) item[0]);
            b.setTenBAN((String) item[1]);
            b.setKhuVuc((String) item[2]);
            b.setTrangThai((String) item[3]);
            b.setMaCN((String) item[4]);
            b.setChinhanh((String) item[7]);
            listResult.add(b);
        }

        model.addAttribute("listResult",listResult);
        return "ban/table";
    }

    @GetMapping("/table/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));

        var item = repository.findItem(id).get(0);
        var b = new BanDTO();
        b.setMaBAN((String) item[0]);
        b.setTenBAN((String) item[1]);
        b.setKhuVuc((String) item[2]);
        b.setTrangThai((String) item[3]);
        b.setMaCN((String) item[4]);
        b.setChinhanh((String) item[7]);

        model.addAttribute("banDTO", b);

        return "ban/edit";
    }

    @PostMapping("/table/edit")
    public String editModel(Model model, HttpServletRequest request, BanDTO banDTO) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));

        repository.update(banDTO.getTrangThai(),banDTO.getMaBAN());
        return "redirect:/table";
    }
}
