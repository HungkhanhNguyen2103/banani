package com.store.banani.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.*;
import com.store.banani.repository.NhanVienRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @PostMapping("/employee/importExcel")
    public String importExcel(Model model, HttpServletRequest request, String dataImportJson) throws JsonProcessingException {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        ObjectMapper mapper = new ObjectMapper();

        List<ChamCong2DTO> list = mapper.readValue(
                dataImportJson,
                mapper.getTypeFactory().constructCollectionType(List.class, ChamCong2DTO.class)
        );

        for (var i : list){
            var check = nhanVienRepository.getLich(i.getMaNV(),i.getMaCa(),i.getNgayLam());
            if(check.isEmpty()){
                nhanVienRepository.insertLich(Helpers.generateId(),i.getMaNV(),i.getNgayLam(),i.getMaCa());
            }
        }
        return "redirect:/employee/schedule";
    }

    @GetMapping("/employee/schedule")
    public String lichlam(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var startDate = LocalDate.now().toString();
        var resultLichLam = nhanVienRepository.lichLamViec(startDate);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(startDate, formatter);

        int year = date.getYear();
        int month = date.getMonthValue();

        List<WeekDTO> weeks = new ArrayList<>();

        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        LocalDate current = firstDay;
        int index = 1;
        Integer selectedWeek = 1;

        while (!current.isAfter(lastDay)) {

            LocalDate monday = current.with(DayOfWeek.MONDAY);
            LocalDate sunday = monday.plusDays(6);

            if (monday.getMonthValue() == month || sunday.getMonthValue() == month) {

                if (!date.isBefore(monday) && !date.isAfter(sunday)) {
                    selectedWeek = index;
                }

                weeks.add(new WeekDTO(
                        index,
                        monday,
                        sunday
                ));

                index++;
            }

            current = current.plusWeeks(1);
        }

        model.addAttribute("selectedWeek", selectedWeek - 1);

        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);

        Map<String, LichNgayDTO> map = new LinkedHashMap<>();

        for (var row : resultLichLam) {

            String ngayLam = (String) row[11];  // YYYY-MM-DD
            String thu = (String) row[1];
            String maCa = (String) row[2];
            String tenCa = (String) row[3];
            String maNV = (String) row[6];
            String tenNV = (String) row[7];

            if (!map.containsKey(ngayLam)) {
                LichNgayDTO dto = new LichNgayDTO();
                dto.setNgayLam(ngayLam);
                dto.setThu(thu);
                map.put(ngayLam, dto);
            }

            LichNgayDTO dto = map.get(ngayLam);

            switch (tenCa) {
                case "Ca sáng":
                    dto.getCaSang().add(tenNV + " (" + maNV + ")");
                    break;
                case "Ca chiều":
                    dto.getCaChieu().add(tenNV + " (" + maNV + ")");
                    break;
                case "Ca tối":
                    dto.getCaToi().add(tenNV + " (" + maNV + ")");
                    break;
            }
        }

        //List<LichTuanDTO> list = new ArrayList<>(map.values());

        List<LichNgayDTO> list = new ArrayList<>(map.values());
        model.addAttribute("lichLamViec", list);
        return "nhanvien/schedule";
    }

    @GetMapping("/employee/schedule2")
    public String lichlamSearch(Model model, HttpServletRequest request,@RequestParam("startDate") String startDate) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        //var startDate = LocalDate.now().toString();
        var resultLichLam = nhanVienRepository.lichLamViec(startDate);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(startDate, formatter);

        int year = date.getYear();
        int month = date.getMonthValue();

        List<WeekDTO> weeks = new ArrayList<>();

        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        LocalDate current = firstDay;
        int index = 1;
        Integer selectedWeek = 1;

        while (!current.isAfter(lastDay)) {

            LocalDate monday = current.with(DayOfWeek.MONDAY);
            LocalDate sunday = monday.plusDays(6);

            if (monday.getMonthValue() == month || sunday.getMonthValue() == month) {

                if (!date.isBefore(monday) && !date.isAfter(sunday)) {
                    selectedWeek = index;
                }

                weeks.add(new WeekDTO(
                        index,
                        monday,
                        sunday
                ));

                index++;
            }

            current = current.plusWeeks(1);
        }

        model.addAttribute("selectedWeek", selectedWeek - 1);

        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);

        Map<String, LichNgayDTO> map = new LinkedHashMap<>();

        for (var row : resultLichLam) {

            String ngayLam = (String) row[11];  // YYYY-MM-DD
            String thu = (String) row[1];
            String maCa = (String) row[2];
            String tenCa = (String) row[3];
            String maNV = (String) row[6];
            String tenNV = (String) row[7];

            if (!map.containsKey(ngayLam)) {
                LichNgayDTO dto = new LichNgayDTO();
                dto.setNgayLam(ngayLam);
                dto.setThu(thu);
                map.put(ngayLam, dto);
            }

            LichNgayDTO dto = map.get(ngayLam);

            switch (tenCa) {
                case "Ca sáng":
                    dto.getCaSang().add(tenNV + "(" + maNV + ")");
                    break;
                case "Ca chiều":
                    dto.getCaChieu().add(tenNV + "(" + maNV + ")");
                    break;
                case "Ca tối":
                    dto.getCaToi().add(tenNV + "(" + maNV + ")");
                    break;
            }
        }

        //List<LichTuanDTO> list = new ArrayList<>(map.values());

        List<LichNgayDTO> list = new ArrayList<>(map.values());
        model.addAttribute("lichLamViec", list);
        return "nhanvien/schedule";
    }
}
