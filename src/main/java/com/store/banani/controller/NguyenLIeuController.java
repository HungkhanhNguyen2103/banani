package com.store.banani.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.*;
import com.store.banani.repository.ChiNhanhRepository;
import com.store.banani.repository.NguyenLieuRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NguyenLIeuController {
    private final NguyenLieuRepository repository;
    private final ChiNhanhRepository chinhanhRepo;

    public NguyenLIeuController(NguyenLieuRepository repository,ChiNhanhRepository chinhanhRepo){
        this.repository = repository;
        this.chinhanhRepo = chinhanhRepo;
    }


    @GetMapping("/material")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var listSP = repository.findAllItem();
        var listResult = new ArrayList<NguyenLieuDTO>(){};
        for(var item : listSP){
            var sp = new NguyenLieuDTO();
            sp.setMaNL((String) item[0]);
            sp.setTenNL((String) item[1]);
            sp.setDonViTinh((String) item[2]);
            sp.setTonKho((int) item[3]);
            sp.setMaCN((String) item[4]);
            listResult.add(sp);
        }
        model.addAttribute("listResult", listResult);

        return "nguyenlieu/material";
    }

    @GetMapping("/material/nhapkho")
    public String nhapkho(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var maCN = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

        var list = repository.findAllItemNhapKho(maCN);

        var result = new ArrayList<NhapKhoDTO>();
        for (var i : list){
            var itm = new NhapKhoDTO();
            itm.setMaPN((String) i[0]);
            itm.setNgayNhap((String) i[1]);
            itm.setTenNV((String) i[3]);
            itm.setSoLuong((int) i[4]);
            var donGia = (double) i[5];
            itm.setDonGiaShow(Helpers.convertMoney(donGia));
            result.add(itm);
        }

        model.addAttribute("results", result);
        return "nguyenlieu/nhapkho";
    }

    @PostMapping("/material/importExcel")
    public String importExcel(Model model, HttpServletRequest request, String dataImportJson) throws JsonProcessingException {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var maNV = CookieUtils.getCookieValue(request,CookieUtils.maNV);
        var maCN = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

        ObjectMapper mapper = new ObjectMapper();

        List<CTNhapKhoDTO> list = mapper.readValue(
                dataImportJson,
                mapper.getTypeFactory().constructCollectionType(List.class, CTNhapKhoDTO.class)
        );

        var maPN = Helpers.generateId();
        repository.nhapKho(maPN,maNV,maCN);
        for (var i : list){
            var maNL = i.getMaNL();
            repository.nhapKhoCT(maPN,maNL,i.getSoLuong(),i.getDonGia());
            var ngLieu = repository.get(maNL).get(0);
            var tonKho = (int) ngLieu[3];
            repository.updateKho2(tonKho + i.getSoLuong(), maNL);
        }
        return "redirect:/material/nhapkho";
    }

    @GetMapping("/material/create")
    public String create(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var resultInfo = chinhanhRepo.findAllChiNhanh();
        var chinhanhs = new ArrayList<ChiNhanhDTO>();

        for (var item : resultInfo){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            cn.setSdt((String) item[3]);
            chinhanhs.add(cn);
        }

        model.addAttribute("chinhanhs", chinhanhs);
        model.addAttribute("nguyenLieuDTO", new NguyenLieuDTO());
        return "nguyenlieu/create";
    }

    @GetMapping("/material/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var resultInfo = chinhanhRepo.findAllChiNhanh();
        var chinhanhs = new ArrayList<ChiNhanhDTO>();

        for (var item : resultInfo){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            cn.setSdt((String) item[3]);
            chinhanhs.add(cn);
        }

        model.addAttribute("chinhanhs", chinhanhs);


        var item = repository.get(id).get(0);
        var sp = new NguyenLieuDTO();
        sp.setMaNL((String) item[0]);
        sp.setTenNL((String) item[1]);
        sp.setDonViTinh((String) item[2]);
        sp.setTonKho((int) item[3]);
        sp.setMaCN((String) item[4]);

        model.addAttribute("nguyenLieuDTO", sp);
        return "nguyenlieu/edit";
    }

    @PostMapping("/material/edit")
    public String editModel(Model model, HttpServletRequest request, NguyenLieuDTO nguyenLieuDTO) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        repository.update(nguyenLieuDTO.getTenNL(),nguyenLieuDTO.getDonViTinh(),nguyenLieuDTO.getTonKho(),nguyenLieuDTO.getMaCN(),nguyenLieuDTO.getMaNL());
        return "redirect:/material";
    }

    @GetMapping("/material/delete/{id}")
    public String delete(@PathVariable("id") String id){
        repository.delete(id);
        return "redirect:/material";
    }

    @PostMapping("/material/create")
    public String createModel(Model model, HttpServletRequest request, NguyenLieuDTO nguyenLieuDTO) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        repository.insert(Helpers.generateId(),nguyenLieuDTO.getTenNL(),nguyenLieuDTO.getDonViTinh(),nguyenLieuDTO.getTonKho(),nguyenLieuDTO.getMaCN());
        return "redirect:/material";

    }
}
