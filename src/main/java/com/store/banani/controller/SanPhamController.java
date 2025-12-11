package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.*;
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
        var vaiTro = CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", vaiTro);
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

    @GetMapping("/product/deleteNL/{maSP}/{maCTSP}")
    public String deleteNL(@PathVariable("maSP") String maSP,@PathVariable("maCTSP") String maCTSP){

        sanPhamRepository.deleteNL(maCTSP);
        return "redirect:/product/dsNguyenLieu/" + maSP;
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

    @GetMapping("/product/dsNguyenLieu/{id}")
    public String dsNguyenLieu(@PathVariable("id") String id,Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var result = sanPhamRepository.getNguyenLieu(id);
        var list = new ArrayList<CTSanPhamDTO>();
        for (var i : result){
            var item = new CTSanPhamDTO();
            item.setMaCTSP((String) i[0]);
            item.setMaSP((String) i[1]);
            item.setTenSP((String) i[2]);
            item.setMaNL((String) i[3]);
            item.setTonKho((int) i[4]);
            item.setTenNL((String) i[5]);
            item.setSoLuong((int) i[6]);
            item.setDonviTinh((String) i[7]);
            list.add(item);
        }
        model.addAttribute("list", list);
        model.addAttribute("maSP", id);
        return "sanpham/nguyenlieu";

    }

    @GetMapping("/product/createNL/{id}")
    public String createNL(@PathVariable("id") String id,Model model, HttpServletRequest request) {

        var vaiTro =  CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", vaiTro);
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        var maCN = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

        var result = sanPhamRepository.findAllItemNL(vaiTro.equals("Admin") ? vaiTro : maCN);
        var list = new ArrayList<NguyenLieuDTO>();
        for (var i : result){
            var item = new NguyenLieuDTO();
            item.setMaNL((String) i[0]);
            item.setTenNL((String) i[1]);
            item.setTonKho((int) i[3]);
            list.add(item);
        }
        var chiTiet = new CTSanPhamDTO();
        chiTiet.setMaSP(id);

        model.addAttribute("ctSanphamDTO", chiTiet);
        model.addAttribute("list", list);
        model.addAttribute("maSP", id);
        return "sanpham/taonguyenlieu";

    }

    @GetMapping("/product/editNL/{maSP}/{maCTSP}")
    public String editNL(@PathVariable("maSP") String maSP,@PathVariable("maCTSP") String maCTSP,Model model, HttpServletRequest request) {
        var vaiTro = CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", vaiTro);
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        var maCN = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

        var result = sanPhamRepository.findAllItemNL(vaiTro.equals("Admin") ? vaiTro : maCN);
        var list = new ArrayList<NguyenLieuDTO>();
        for (var i : result){
            var item = new NguyenLieuDTO();
            item.setMaNL((String) i[0]);
            item.setTenNL((String) i[1]);
            item.setTonKho((int) i[3]);
            list.add(item);
        }
        var chiTiet2 = sanPhamRepository.getCTNL(maCTSP).get(0);
        var chiTiet = new CTSanPhamDTO();
        chiTiet.setMaCTSP((String) chiTiet2[0]);
        chiTiet.setSoLuong((int) chiTiet2[1]);
        chiTiet.setMaSP(maSP);
        chiTiet.setMaNL((String) chiTiet2[3]);
        chiTiet.setMaNLCu((String) chiTiet2[3]);

        model.addAttribute("ctSanphamDTO", chiTiet);
        model.addAttribute("list", list);
        model.addAttribute("maSP", maSP);
        return "sanpham/taonguyenlieu";

    }

    @PostMapping("/product/updateNL")
    public String updateNL(Model model, HttpServletRequest request,CTSanPhamDTO ctSanphamDTO) {
        var item = sanPhamRepository.getCTNL(ctSanphamDTO.getMaCTSP());
        //var nlieu = sanPhamRepository.getNguyenLieu2(ctSanphamDTO.getMaNLCu()).get(0);
        if(item.isEmpty()){
            ctSanphamDTO.setMaCTSP(Helpers.generateId());
            sanPhamRepository.insertNL(ctSanphamDTO.getMaCTSP(), ctSanphamDTO.getSoLuong(), ctSanphamDTO.getMaSP(),ctSanphamDTO.getMaNL());
            //var tonKho = (int) nlieu[3] - ctSanphamDTO.getSoLuong();
            ///sanPhamRepository.updateKho(tonKho,ctSanphamDTO.getMaNL());
        }
        else {
            //var tonKho = (int) nlieu[3] + ctSanphamDTO.getSoLuong();
            //sanPhamRepository.updateKho(tonKho,ctSanphamDTO.getMaNLCu());
            sanPhamRepository.updateNL(ctSanphamDTO.getSoLuong(), ctSanphamDTO.getMaSP(),ctSanphamDTO.getMaNL(),ctSanphamDTO.getMaCTSP());
//            var nlieu2 = sanPhamRepository.getNguyenLieu2(ctSanphamDTO.getMaNL()).get(0);
//            var tonKho2 = (int) nlieu2[3] - ctSanphamDTO.getSoLuong();
//            sanPhamRepository.updateKho(tonKho2,ctSanphamDTO.getMaNL());
        }

        return "redirect:/product/dsNguyenLieu/" + ctSanphamDTO.getMaSP();

    }
}
