package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.ChiNhanhDTO;
import com.store.banani.dto.LoaiSanPhamDTO;
import com.store.banani.dto.NguoiDungDTO;
import com.store.banani.dto.SanPhamDTO;
import com.store.banani.model.NHANVIEN;
import com.store.banani.repository.ChiNhanhRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
public class ChiNhanhController {
    private final ChiNhanhRepository repository;
    public  ChiNhanhController(ChiNhanhRepository repository){
        this.repository = repository;
    }

    @GetMapping("/branch")
    public String branch(Model model, HttpServletRequest request) {

        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));

        var list = repository.findAllChiNhanh();
        var chinhanhs = new ArrayList<ChiNhanhDTO>();

        for (var item : list){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            cn.setSdt((String) item[3]);
            chinhanhs.add(cn);
        }

        model.addAttribute("chinhanhs",chinhanhs );
        return "chinhanh/branch";
    }


    @GetMapping("/branch/create")
    public String create(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chinhanhDTO", new ChiNhanhDTO());
        return "chinhanh/create";
    }

    @PostMapping("/branch/create")
    public String createModel(Model model,HttpServletRequest request ,@ModelAttribute("chinhanhDTO") ChiNhanhDTO chiNhanhDTO){
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanhDTO", chiNhanhDTO);

        repository.insert(Helpers.generateId(),chiNhanhDTO.getTenCN(),chiNhanhDTO.getDiaChi(),chiNhanhDTO.getSdt(),chiNhanhDTO.getEmailCN(),chiNhanhDTO.getTrangThai());
        return "redirect:/branch";
    }

    @GetMapping("/branch/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));

        var result = repository.get(id).get(0);
        var cn = new ChiNhanhDTO();
        cn.setMaCN((String) result[0]);
        cn.setTenCN((String) result[1]);
        cn.setDiaChi((String) result[2]);
        cn.setSdt((String) result[3]);
        cn.setEmailCN((String) result[4]);
        cn.setTrangThai((String) result[5]);

        model.addAttribute("chinhanhDTO", cn);
        return "chinhanh/edit";
    }

    @PostMapping("/branch/edit")
    public String editModel(Model model, HttpServletRequest request,ChiNhanhDTO chiNhanhDTO) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));

        repository.update(chiNhanhDTO.getTenCN(),chiNhanhDTO.getDiaChi(),chiNhanhDTO.getSdt(),chiNhanhDTO.getEmailCN(),chiNhanhDTO.getMaCN(),chiNhanhDTO.getTrangThai());

        model.addAttribute("chinhanhDTO", chiNhanhDTO);
        return "redirect:/branch";
    }

    @GetMapping("/branch/delete/{id}")
    public String delete(@PathVariable("id") String id){
        repository.delete(id);
        return "redirect:/branch";
    }
}
