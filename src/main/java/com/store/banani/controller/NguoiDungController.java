package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.ChiNhanhDTO;
import com.store.banani.dto.NguoiDungDTO;
import com.store.banani.model.NHANVIEN;
import com.store.banani.repository.ChiNhanhRepository;
import com.store.banani.repository.NhanVienRepository;
import com.store.banani.repository.UserRepository;
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
import java.util.Date;

@Controller
public class NguoiDungController {
    private final UserRepository repo;
    private final NhanVienRepository nhanvienRepo;
    private final ChiNhanhRepository chinhanhRepo;

    public NguoiDungController(UserRepository repo,NhanVienRepository nhanvienRepo,ChiNhanhRepository chinhanhRepo) {
        this.repo = repo;
        this.nhanvienRepo = nhanvienRepo;
        this.chinhanhRepo = chinhanhRepo;
    }

    @GetMapping("/user")
    public String user(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        var result = repo.findAllUser();
        var listUser = new ArrayList<NguoiDungDTO>(){};
        for(var item : result){
            var user = new NguoiDungDTO();
            user.setMaNV((String) item[0]);
            user.setTenNV((String) item[1]);
            user.setGioiTinh((String) item[2]);
            user.setNgaySinh(sdf.format((Date) item[3]));
            user.setSdtNV((String) item[4]);
            user.setEmail((String) item[5]);
            user.setVaiTro((String) item[6]);
            user.setMaTaiKhoan((String) item[8]);
            user.setMatKhau((String) item[9]);
            listUser.add(user);
        }
        model.addAttribute("users", listUser);
        return "nguoidung/user";
    }

    @GetMapping("/user/create")
    public String createUser(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var chiNhanhs = chinhanhRepo.findAllChiNhanh();
        var listCN = new ArrayList<ChiNhanhDTO>(){};
        for(var item : chiNhanhs){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            listCN.add(cn);
        }

        model.addAttribute("chinhanhs", listCN);
        model.addAttribute("nguoiDungDTO", new NguoiDungDTO());
        model.addAttribute("error", "");
        return "nguoidung/create";
    }


    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var resultInfo = nhanvienRepo.getNhanVien(id);
        Object[] result = resultInfo.get(0);
        var nv = new NguoiDungDTO();
        var maNVDb = (String) result[0];
        nv.setMaNV(maNVDb);
        nv.setTenNV((String) result[1]);
        nv.setGioiTinh((String) result[2]);
        nv.setNgaySinh(formatter.format((Date) result[3]));
        nv.setSdtNV((String) result[4]);
        nv.setEmail((String) result[5]);
        nv.setVaiTro((String) result[6]);
        nv.setMaCN((String) result[7]);
        nv.setMaTaiKhoan((String) result[8]);
        nv.setMatKhau((String) result[9]);


        var chiNhanhs = chinhanhRepo.findAllChiNhanh();
        var listCN = new ArrayList<ChiNhanhDTO>(){};
        for(var item : chiNhanhs){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            listCN.add(cn);
        }

        model.addAttribute("chinhanhs", listCN);
        model.addAttribute("nguoiDungDTO", nv);

        return "nguoidung/edit";
    }

    @PostMapping("/user/create")
    public String createUserModel(Model model,HttpServletRequest request ,@ModelAttribute("nguoiDungDTO") NguoiDungDTO nguoiDungDTO) throws ParseException {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("nguoiDungDTO", nguoiDungDTO);
        var nhanvien = new NHANVIEN();
        nhanvien.setMaNV(Helpers.generateId());
        nhanvien.setGioiTinh(nguoiDungDTO.getGioiTinh());
        nhanvien.setSdtNV(nguoiDungDTO.getSdtNV());
        nhanvien.setNgaySinh(formatter.parse(nguoiDungDTO.getNgaySinh()));
        nhanvien.setVaiTro(nguoiDungDTO.getVaiTro());
        nhanvien.setTenNV(nguoiDungDTO.getTenNV());
        nhanvien.setEmailNV(nguoiDungDTO.getEmail());
        nhanvien.setMaCN(nguoiDungDTO.getMaCN());

        var chiNhanhs = chinhanhRepo.findAllChiNhanh();
        var listCN = new ArrayList<ChiNhanhDTO>(){};
        for(var item : chiNhanhs){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            listCN.add(cn);
        }

        var users = repo.getUser(nguoiDungDTO.getMaTaiKhoan(),nhanvien.getMaNV());
        if(users.size() > 0){
            model.addAttribute("chinhanhs", listCN);
            model.addAttribute("error", "Tài khoản đã tồn tại");
            return "nguoidung/create";
        }

        nhanvienRepo.insertNhanVien(nhanvien.getMaNV(),nhanvien.getTenNV()
                ,nhanvien.getGioiTinh(),nhanvien.getNgaySinh(),nhanvien.getSdtNV(),nhanvien.getEmailNV(),nhanvien.getVaiTro(),nhanvien.getMaCN());

        repo.insertUser(nguoiDungDTO.getMaTaiKhoan(),nguoiDungDTO.getMatKhau(),nhanvien.getMaNV());
        return "redirect:/user";
    }

    @PostMapping("/user/edit")
    public String editUser(Model model, HttpServletRequest request,NguoiDungDTO nguoiDungDTO) throws ParseException {
        var username = CookieUtils.getCookieValue(request,CookieUtils.username);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        var vaiTro = CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        var maCN = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", vaiTro);
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        model.addAttribute("nguoiDungDTO", nguoiDungDTO);

        var chiNhanhs = chinhanhRepo.findAllChiNhanh();
        var listCN = new ArrayList<ChiNhanhDTO>(){};
        for(var item : chiNhanhs){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            listCN.add(cn);
        }

        if(!vaiTro.equals("Admin")) nguoiDungDTO.setMaCN(maCN);

        var users = repo.getUser(nguoiDungDTO.getMaTaiKhoan(),nguoiDungDTO.getMaNV());
        if(users.size() > 0){
            model.addAttribute("chinhanhs", listCN);
            model.addAttribute("error", "Tài khoản đã tồn tại");
            return "nguoidung/edit";
        }

        var user = repo.getUserByMaNV(nguoiDungDTO.getMaTaiKhoan(),nguoiDungDTO.getMaNV());
        if(user.size() > 0){
            repo.updateUser(nguoiDungDTO.getMaTaiKhoan(),nguoiDungDTO.getMatKhau());
        }
        else{
            repo.insertUser(nguoiDungDTO.getMaTaiKhoan(),nguoiDungDTO.getMatKhau(),nguoiDungDTO.getMaNV());
        }

        var ns = formatter.parse(nguoiDungDTO.getNgaySinh());
        nhanvienRepo.update(nguoiDungDTO.getMaNV(),nguoiDungDTO.getTenNV()
                ,nguoiDungDTO.getGioiTinh(),ns,nguoiDungDTO.getSdtNV(),
                nguoiDungDTO.getEmail(),nguoiDungDTO.getVaiTro(),nguoiDungDTO.getMaCN());

        var maNVDb = nguoiDungDTO.getMaTaiKhoan();
        if(maNVDb.equals(username)) return "redirect:/account/logout";
        return "redirect:/user";
    }
}
