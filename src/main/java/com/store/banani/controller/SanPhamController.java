package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.LoaiSanPhamDTO;
import com.store.banani.dto.NguoiDungDTO;
import com.store.banani.dto.SanPhamDTO;
import com.store.banani.repository.SanPhamRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class SanPhamController {

    private final SanPhamRepository sanPhamRepository;

    public SanPhamController(SanPhamRepository sanPhamRepository){
        this.sanPhamRepository = sanPhamRepository;
    }

    @GetMapping("/product")
    public String product(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var listSP = sanPhamRepository.findAllSanPham();
        var listResult = new ArrayList<SanPhamDTO>(){};
        for(var item : listSP){
            var sp = new SanPhamDTO();
            sp.setMaSP((String) item[0]);
            sp.setTenSP((String) item[1]);
            sp.setMaLSP((String) item[2]);
            BigDecimal big = (BigDecimal) item[3];
            sp.setDonGia(big.floatValue());
            sp.setDonGiaText(Helpers.convertMoney(sp.getDonGia()));
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

    @GetMapping("/product/create")
    public String create(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var resultInfo = sanPhamRepository.findAllLoaiSanPham();
        var listLoatSP = new ArrayList<LoaiSanPhamDTO>();
        for (var item : resultInfo){
            var loaiSP = new LoaiSanPhamDTO();
            loaiSP.setMaLoaiSP((String) item[0]);
            loaiSP.setTenLoaiSP((String) item[1]);
            loaiSP.setGhiChu((String) item[2]);
            listLoatSP.add(loaiSP);
        }

        model.addAttribute("listLoai", listLoatSP);
        model.addAttribute("sanphamDTO", new SanPhamDTO());
        return "sanpham/create";
    }

    @GetMapping("/product/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var resultInfo = sanPhamRepository.findAllLoaiSanPham();
        var listLoatSP = new ArrayList<LoaiSanPhamDTO>();
        for (var item : resultInfo){
            var loaiSP = new LoaiSanPhamDTO();
            loaiSP.setMaLoaiSP((String) item[0]);
            loaiSP.setTenLoaiSP((String) item[1]);
            loaiSP.setGhiChu((String) item[2]);
            listLoatSP.add(loaiSP);
        }
        var resultSP = sanPhamRepository.get(id).get(0);
        var sanPhamDTO = new SanPhamDTO();
        sanPhamDTO.setMaSP((String) resultSP[0]);
        sanPhamDTO.setTenSP((String) resultSP[1]);
        sanPhamDTO.setMaLSP((String) resultSP[2]);
        BigDecimal big = (BigDecimal) resultSP[3];
        sanPhamDTO.setDonGia(big.floatValue());
        sanPhamDTO.setDonviTinh((String) resultSP[4]);
        sanPhamDTO.setTrangThai((String) resultSP[5]);
        sanPhamDTO.setHinhAnh((String) resultSP[6]);

        model.addAttribute("listLoai", listLoatSP);
        model.addAttribute("sanphamDTO", sanPhamDTO);
        return "sanpham/edit";
    }

    @PostMapping("/product/edit")
    public String editProduct(Model model, HttpServletRequest request, SanPhamDTO sanPhamDTO) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        sanPhamRepository.update(sanPhamDTO.getMaSP(),sanPhamDTO.getTenSP(),sanPhamDTO.getMaLSP(),sanPhamDTO.getDonGia(),sanPhamDTO.getDonviTinh(),sanPhamDTO.getTrangThai(),sanPhamDTO.getHinhAnh());
        return "redirect:/product";
    }

    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable("id") String id){
        sanPhamRepository.delete(id);
        return "redirect:/product";
    }

    @PostMapping("/product/create")
    public String createProduct(Model model, HttpServletRequest request, SanPhamDTO sanPhamDTO) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        sanPhamDTO.setMaSP(Helpers.generateId());
        sanPhamRepository.insert(sanPhamDTO.getMaSP(),sanPhamDTO.getTenSP(),sanPhamDTO.getMaLSP(),sanPhamDTO.getDonGia(),sanPhamDTO.getDonviTinh(),sanPhamDTO.getTrangThai(),sanPhamDTO.getHinhAnh());
        return "redirect:/product";

    }
}
