package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.*;
import com.store.banani.repository.BanRepository;
import com.store.banani.repository.DonHangRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BanController {
    private final BanRepository repository;
    private final DonHangRepository repositoryDH;

    public BanController(BanRepository repository,DonHangRepository repositoryDH){
        this.repository = repository;
        this.repositoryDH = repositoryDH;
    }

    @GetMapping("/table")
    public String table(Model model, HttpServletRequest request) {

        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        var maChiNhanh = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);
        var result = repository.findAllItem(maChiNhanh);
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

        Map<String, List<BanDTO>> listResultGrouped = listResult.stream()
                .sorted(Comparator.comparingInt(item -> {
                    try {
                        return Integer.parseInt(item.getTenBAN().replaceAll("\\D+", ""));
                    } catch (Exception e) {
                        return 0;
                    }
                }))
                .collect(Collectors.groupingBy(
                        BanDTO::getKhuVuc,
                        Collectors.toList()
                ));

        model.addAttribute("listResultGrouped",listResultGrouped);
        return "ban/table";
    }

    @GetMapping("/table/order/{id}")
    public String order(@PathVariable("id") String id,Model model, HttpServletRequest request){

        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var donDatDTO = new DonDatDTO();
        donDatDTO.setMaBan(id);
        donDatDTO.setMaNV(CookieUtils.getCookieValue(request,CookieUtils.maNV));
        donDatDTO.setMaDD(Helpers.generateId());
        donDatDTO.setTrangThai("Đang xử lý");

        var banDTO = new BanDTO();
        var item = repository.findItem(id).get(0);
        banDTO.setMaBAN((String) item[0]);
        banDTO.setTenBAN((String) item[1]);
        banDTO.setKhuVuc((String) item[2]);
        banDTO.setTrangThai((String) item[3]);
        banDTO.setMaCN((String) item[4]);
        banDTO.setChinhanh((String) item[7]);

        if(banDTO.getTrangThai().equals("Trống")){
            banDTO.setTrangThai("Đang sử dụng");
            repository.update(banDTO.getTrangThai(),banDTO.getMaBAN());

            repositoryDH.createDondat(donDatDTO.getMaDD(),null,donDatDTO.getMaNV(),donDatDTO.getMaBan(),donDatDTO.getTrangThai());
            String url = "redirect:/order/detail/" + donDatDTO.getMaDD();

            repositoryDH.editDonDat(donDatDTO.getTrangThai(),donDatDTO.getMaDD());
            return url;
        }
        else{
            var found2 = repositoryDH.getDonDatByBan(id).get(0);
            donDatDTO.setMaDD((String) found2[0]);
            var url2 = "redirect:/order/detail/" + donDatDTO.getMaDD();
            return url2;
        }
        //banDTO.setTrangThai("Đang sử dụng");
        //banDTO.setMaBAN(id);

    }

    @GetMapping("/table/create")
    public String create(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        model.addAttribute("banDTO",new BanDTO());
        return "ban/create";
    }

    @PostMapping("/table/create")
    public String createBan(Model model, HttpServletRequest request, BanDTO banDTO) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        banDTO.setMaCN(CookieUtils.getCookieValue(request,CookieUtils.machiNhanh));
        banDTO.setTrangThai("Trống");
        banDTO.setMaBAN(Helpers.generateId());
        var found = repository.findItem2("Bàn " + banDTO.getTenBAN(), banDTO.getKhuVuc(),banDTO.getMaCN());
        if (!found.isEmpty()) {
            model.addAttribute("error", "Bàn hoặc khu vực đã tồn tại");
            return "ban/create";
        }
        banDTO.setTenBAN("Bàn " + banDTO.getTenBAN());
        banDTO.setKhuVuc(banDTO.getKhuVuc());

        repository.insert(banDTO.getMaBAN(),banDTO.getTenBAN(),banDTO.getKhuVuc(),banDTO.getTrangThai(),banDTO.getMaCN());
        return "redirect:/table";
    }

    @GetMapping("/table/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

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
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        repository.update(banDTO.getTrangThai(),banDTO.getMaBAN());
        return "redirect:/table";
    }
}
