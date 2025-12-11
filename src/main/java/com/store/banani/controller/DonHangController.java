package com.store.banani.controller;

import com.store.banani.config.CookieUtils;
import com.store.banani.config.Helpers;
import com.store.banani.dto.*;
import com.store.banani.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class DonHangController {
    private final DonHangRepository repository;
    private final SanPhamRepository sanPhamRepository;
    private final BanRepository banRepository;
    private final NhanVienRepository nhanVienRepository;
    private final ChiNhanhRepository chiNhanhRepository;
    private final KhachHangRepository khachHangRepository;
    private final NguyenLieuRepository nguyenLieuRepository;

    public DonHangController(DonHangRepository repository,SanPhamRepository sanPhamRepository,
                             BanRepository banRepository,NhanVienRepository nhanVienRepository,
                             ChiNhanhRepository chiNhanhRepository, KhachHangRepository khachHangRepository,NguyenLieuRepository nguyenLieuRepository){
        this.repository = repository;
        this.sanPhamRepository = sanPhamRepository;
        this.banRepository = banRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.chiNhanhRepository = chiNhanhRepository;
        this.khachHangRepository = khachHangRepository;
        this.nguyenLieuRepository = nguyenLieuRepository;
    }

    @GetMapping("/order")
    public String order(Model model, HttpServletRequest request) throws ParseException {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        var list = repository.getAllDonDat();
        var result = new ArrayList<DonDatDTO>();
        for (var i : list){
            var dd = new DonDatDTO();
            dd.setMaDD((String) i[0]);
            String ns = formatter.format(i[1]);
            dd.setThoiGianDat(ns);
            dd.setMaKH((String) i[2]);
            dd.setTenKH((String) i[7]);
            dd.setSDT((String) i[8]);
            dd.setMaBan((String) i[4]);
            dd.setTrangThai((String) i[5]);
            result.add(dd);
        }

        model.addAttribute("result", result);
        return "dondat/order";
    }

    @GetMapping("/order/create")
    public String create(Model model, HttpServletRequest request){
        var vaiTro = CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", vaiTro);
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var maChiNhanh = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

//        var listKH = repository.getListKH();
//        var listKH1 = new ArrayList<KhachHangDTO>();
//        for (var kh : listKH){
//            var item = new KhachHangDTO();
//            item.setMaKH((String) kh[0]);
//            item.setTenKH((String) kh[1]);
//            listKH1.add(item);
//        }

        var result = banRepository.findAllItem(vaiTro.equals("Admin") ? vaiTro : maChiNhanh);
        var listBan = new ArrayList<BanDTO>();
        for (var item : result){
            var b = new BanDTO();
            b.setMaBAN((String) item[0]);
            b.setTenBAN((String) item[1]);
            b.setKhuVuc((String) item[2]);
            b.setTrangThai((String) item[3]);
            b.setMaCN((String) item[4]);
            b.setChinhanh((String) item[7]);
            listBan.add(b);
        }

        //var maChiNhanh = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

        var list = nhanVienRepository.getAllNhanVien(maChiNhanh);
        var resultNV = new ArrayList<NhanVienDTO>();
        for (var i : list){
            var nhanvien = new NhanVienDTO();
            nhanvien.setMaNV((String) i[0]);
            nhanvien.setTenNV((String) i[1]);
            nhanvien.setSdtNV((String) i[4]);
            nhanvien.setEmailNV((String) i[5]);
            nhanvien.setVaiTro((String) i[6]);
            resultNV.add(nhanvien);
        }

        var donDat = new DonDatDTO();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = now.format(formatter);
        donDat.setThoiGianDat(formattedDate);
        //model.addAttribute("listKH",listKH1);
        model.addAttribute("listBan",listBan);
        model.addAttribute("listNV",resultNV);
        model.addAttribute("dondatDTO", donDat);
        model.addAttribute("hoaDonDTO",new HoaDonDTO());
        model.addAttribute("isPayment",false);

        return "dondat/create";
    }

    @PostMapping("/order/create")
    public String create(Model model, HttpServletRequest request,DonDatDTO donDatDTO){
        donDatDTO.setMaNV(CookieUtils.getCookieValue(request,CookieUtils.maNV));
        donDatDTO.setMaDD(Helpers.generateId());
        donDatDTO.setTrangThai("Đang xử lý");

        var maKH = Helpers.generateId();
        khachHangRepository.insert(maKH,donDatDTO.getTenKH(),donDatDTO.getSDT(),donDatDTO.getEmailKH());
        donDatDTO.setMaKH(maKH);
        repository.createDondat(donDatDTO.getMaDD(),maKH,donDatDTO.getMaNV(),donDatDTO.getMaBan(),donDatDTO.getTrangThai());
        String url = "redirect:/order/detail/" + donDatDTO.getMaDD();

        repository.editDonDat(donDatDTO.getTrangThai(),donDatDTO.getMaDD());
        return url;
    }

    @PostMapping("/order/getKH")
    public ResponseEntity<KhachHangDTO> getKhachHang(@RequestParam String sdt){
        var found = khachHangRepository.getKhachHang(sdt);
        if(found.isEmpty()) return ResponseEntity.ok(null);
        var kh = found.get(0);
        var khDTO = new KhachHangDTO();
        khDTO.setTenKH((String) kh[1]);
        khDTO.setSdtKH(sdt);
        khDTO.setMaKH((String) kh[0]);
        khDTO.setEmailKH((String) kh[3]);
        return ResponseEntity.ok(khDTO);
    }

    @PostMapping("/order/ktTonKho")
    public ResponseEntity<String> ktTonKho(@RequestParam String maSP,@RequestParam int amount){
        var found = sanPhamRepository.getNguyenLieu(maSP);
        if(found.isEmpty()) return ResponseEntity.ok("Không");
        for (var i : found){
            var soLuong = (int) i[6];
            var tonKho = (int) i[4];
            var tenNL = (String) i[5];
            if(tonKho < soLuong * amount) return ResponseEntity.ok("Kho không đủ nguyên liệu " + tenNL);
        }

        return ResponseEntity.ok("Có");
    }

    @GetMapping("/order/detail/{id}")
    public String detail(@PathVariable("id") String id, Model model, HttpServletRequest request){
        var vaiTro = CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", vaiTro);
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var maChiNhanh = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        var data = repository.chitietDonDat(id);
        var i = data.get(0);
        var dd = new DonDatDTO();
        dd.setMaDD((String) i[0]);
        String ns = formatter.format(i[1]);
        dd.setThoiGianDat(ns);
        dd.setMaKH((String) i[2]);
        dd.setTenKH((String) i[7]);
        dd.setSDT((String) i[8]);
        dd.setEmailKH((String) i[9]);
        dd.setMaBan((String) i[4]);
        dd.setTrangThai((String) i[5]);

        float tongTien = 0;
        var listSP = new ArrayList<CTDonDatDTO>();
        for (var c : data){
            if(c[17] == null) continue;
            var item = new CTDonDatDTO();
            item.setMaDD((String) c[0]);
            item.setMaSP((String) c[17]);
            if (c[18] != null) {
                item.setSoLuong((int) c[18]);
            }

            item.setGhiChu((String) c[19]);

            var itemSp = new SanPhamDTO();
            itemSp.setTenSP((String) c[21]);
            itemSp.setMaLSP((String) c[22]);
            if(c[23] != null){
                BigDecimal big = (BigDecimal) c[23];
                itemSp.setDonGia(big.floatValue());
                itemSp.setDonGiaText(Helpers.convertMoney(itemSp.getDonGia()));
                tongTien += (float) (itemSp.getDonGia() * item.getSoLuong());
            }

            itemSp.setDonviTinh((String) c[24]);
            itemSp.setHinhAnh((String) c[26]);
            item.setSanPhamDTO(itemSp);

            listSP.add(item);
        }
        dd.setListSP(listSP);
        dd.setTongTien2(tongTien);

        var r1 = repository.getPayment(dd.getMaDD());
        var isBoolean = false;
        if(!r1.isEmpty()) isBoolean = true;
        model.addAttribute("isPayment",isBoolean);

        model.addAttribute("tongTien","Tổng tiền: " + Helpers.convertMoney(tongTien));
        model.addAttribute("maDD",dd.getMaDD());
        model.addAttribute("dondatDTO", dd);

        var result = banRepository.findAllItem(vaiTro.equals("Admin") ? vaiTro : maChiNhanh);
        var listBan = new ArrayList<BanDTO>();
        for (var item : result){
            var b = new BanDTO();
            b.setMaBAN((String) item[0]);
            b.setTenBAN((String) item[1]);
            b.setKhuVuc((String) item[2]);
            b.setTrangThai((String) item[3]);
            b.setMaCN((String) item[4]);
            b.setChinhanh((String) item[7]);
            listBan.add(b);
        }
        model.addAttribute("listBan",listBan);

        var list = nhanVienRepository.getAllNhanVien(maChiNhanh);
        var resultNV = new ArrayList<NhanVienDTO>();
        for (var i1 : list){
            var nhanvien = new NhanVienDTO();
            nhanvien.setMaNV((String) i1[0]);
            nhanvien.setTenNV((String) i1[1]);
            nhanvien.setSdtNV((String) i1[4]);
            nhanvien.setEmailNV((String) i1[5]);
            nhanvien.setVaiTro((String) i1[6]);
            resultNV.add(nhanvien);
        }
        var hoaDon = new HoaDonDTO();
        hoaDon.setMaDD(id);
        hoaDon.setTongTien(tongTien);

        model.addAttribute("listNV",resultNV);
        model.addAttribute("hoaDonDTO",hoaDon);
        return "dondat/edit";
    }

    @PostMapping("/order/payment")
    public String payment(HoaDonDTO hoaDonDTO){
        hoaDonDTO.setMaHD(Helpers.generateId());
        repository.payment(hoaDonDTO.getMaHD(),hoaDonDTO.getMaDD(),hoaDonDTO.getTongTien(),hoaDonDTO.getPttt(),hoaDonDTO.getMaNV());
        return "redirect:/order";
    }

    @GetMapping("/payment")
    public String payment(Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var result = repository.getAllHoaDon();
        var list = new ArrayList<HoaDonDTO>();
        for (var i : result){
            var hd = new HoaDonDTO();
            hd.setMaHD((String) i[16]);
            hd.setMaDD((String) i[0]);
            String ns = formatter.format(i[18]);
            hd.setNgayLap(ns);
            hd.setTenKH((String) i[7]);
            BigDecimal big = (BigDecimal) i[19];
            hd.setTongTien(big.floatValue());
            hd.setPttt((String) i[20]);
            hd.setNhanVien((String) i[23]);
            list.add(hd);
        }
        model.addAttribute("listHD",list);
        return "payment";
    }


    @PostMapping("/order/detail/createctSP")
    public String createctSP(Model model, HttpServletRequest request, CTDonDatDTO donDatDTO){

        var obj = repository.getDonDat(donDatDTO.getMaDD(),donDatDTO.getMaSP());
        if(obj.isEmpty()){
            repository.insertChiTiet(donDatDTO.getMaDD(),donDatDTO.getMaSP(),donDatDTO.getSoLuong(),donDatDTO.getGhiChu());
        }
        else{
            repository.updateChiTiet(donDatDTO.getMaDD(),donDatDTO.getMaSP(),donDatDTO.getSoLuong(),donDatDTO.getGhiChu());
        }

        var url = "redirect:/order/detail/" + donDatDTO.getMaDD();
        return url;
    }

    @GetMapping("/statisticalFilter/{maCN}")
    public String statisticalFilter(@PathVariable("maCN") String maCN, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model, HttpServletRequest request) {
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var result = chiNhanhRepository.findAllChiNhanh();

        var chinhanhs = new ArrayList<ChiNhanhDTO>();
        var cn1 = new ChiNhanhDTO();
        cn1.setMaCN("0");
        cn1.setTenCN("Doanh thu toàn chi nhánh");
        cn1.setDiaChi("");
        chinhanhs.add(cn1);
        for (var item : result){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            cn.setSdt((String) item[3]);
            chinhanhs.add(cn);
        }

        var chinhanh2 = chiNhanhRepository.get(maCN);
        var chinhanh1 = !chinhanh2.isEmpty() ? chinhanh2.get(0) : null;
        var tenCn = "";
        var dcHi = "";
        if(chinhanh1 != null){
            tenCn = (String) chinhanh1[1];
            dcHi = (String) chinhanh1[2];
        }
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);

        model.addAttribute("tenCN",tenCn);
        model.addAttribute("diaChi",dcHi);

        model.addAttribute("chinhanhs",chinhanhs);
        model.addAttribute("maCN",maCN);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        var list = new ArrayList<ThongKeDTO>();
        var labels = new ArrayList<String>();
        var values = new ArrayList<Float>();
        if(Objects.equals(maCN, "0")){
            var data = repository.thongKeToanchiNhanhByDate(startDate,endDate);
            for (var i : data){
                var tk = new ThongKeDTO();
                String ns = formatter.format(i[0]);
                BigDecimal big = (BigDecimal) i[1];
                tk.setValue(big.floatValue());
                labels.add(ns);
                values.add(big.floatValue());
                tk.setLabel(ns);
                tk.setValue(big.floatValue());
                list.add(tk);
            }
        }
        else{
            var data = repository.thongKechiNhanhbyDate(maCN,startDate,endDate);
            for (var i : data){
                var tk = new ThongKeDTO();
                String ns = formatter.format(i[0]);
                BigDecimal big = (BigDecimal) i[1];
                tk.setValue(big.floatValue());
                labels.add(ns);
                values.add(big.floatValue());
                tk.setLabel(ns);
                tk.setValue(big.floatValue());
                list.add(tk);
            }
        }

        var listInHoaDon = new ArrayList<BaoCaoDTO>();
        var result1 = repository.inHoaDonByDate(maCN,startDate,endDate);
        if(maCN.equals("0")) result1 = repository.inHoaDonAllByDate(startDate,endDate);
        for (var i : result1){
            var ite1 = new BaoCaoDTO();
            ite1.setMaHD((String) i[0]);
            ite1.setMaDD((String) i[1]);
            String ns = formatter.format(i[2]);
            ite1.setNgayLap(ns);
            BigDecimal big = (BigDecimal) i[3];
            ite1.setTongTien(Helpers.convertMoney(big.floatValue()));
            ite1.setTenKH((String) i[4]);
            ite1.setSoluong((int) i[5]);
            listInHoaDon.add(ite1);
        }

        model.addAttribute("listInHoaDon",listInHoaDon);

        model.addAttribute("list",list);
        model.addAttribute("labels",labels);
        model.addAttribute("values",values);
        return "statistical";
    }

    @GetMapping("/statistical/{maCN}")
    public String statistical(@PathVariable("maCN") String maCN, Model model, HttpServletRequest request) {
        var vaiTro = CookieUtils.getCookieValue(request,CookieUtils.vaiTro);
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", vaiTro);
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var maCn = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);
        if(!vaiTro.equals("Admin") && maCN.equals("0")){
            return "redirect:/statistical/" + maCn;
        }

        var result = chiNhanhRepository.findAllChiNhanh();

        var chinhanhs = new ArrayList<ChiNhanhDTO>();
        var cn1 = new ChiNhanhDTO();
        cn1.setMaCN("0");
        cn1.setTenCN("Doanh thu toàn chi nhánh");
        cn1.setDiaChi("");
        chinhanhs.add(cn1);
        for (var item : result){
            var cn = new ChiNhanhDTO();
            cn.setMaCN((String) item[0]);
            cn.setTenCN((String) item[1]);
            cn.setDiaChi((String) item[2]);
            cn.setSdt((String) item[3]);
            chinhanhs.add(cn);
        }

        var chinhanh2 = chiNhanhRepository.get(maCN);
        var chinhanh1 = !chinhanh2.isEmpty() ? chinhanh2.get(0) : null;
        var tenCn = "";
        var dcHi = "";
        if(chinhanh1 != null){
            tenCn = (String) chinhanh1[1];
            dcHi = (String) chinhanh1[2];
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateNow = currentDate.format(formatter1);
        model.addAttribute("startDate","yyyy-MM-dd");
        model.addAttribute("endDate","yyyy-MM-dd");

        model.addAttribute("tenCN",tenCn);
        model.addAttribute("diaChi",dcHi);

        model.addAttribute("chinhanhs",chinhanhs);
        model.addAttribute("maCN",maCN);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        var list = new ArrayList<ThongKeDTO>();
        var labels = new ArrayList<String>();
        var values = new ArrayList<Float>();
        if(Objects.equals(maCN, "0")){
            var data = repository.thongKeToanchiNhanh();
            for (var i : data){
                var tk = new ThongKeDTO();
                String ns = formatter.format(i[0]);
                BigDecimal big = (BigDecimal) i[1];
                tk.setValue(big.floatValue());
                labels.add(ns);
                values.add(big.floatValue());
                tk.setLabel(ns);
                tk.setValue(big.floatValue());
                list.add(tk);
            }
        }
        else{
            var data = repository.thongKechiNhanh(maCN);
            for (var i : data){
                var tk = new ThongKeDTO();
                String ns = formatter.format(i[0]);
                BigDecimal big = (BigDecimal) i[1];
                tk.setValue(big.floatValue());
                labels.add(ns);
                values.add(big.floatValue());
                tk.setLabel(ns);
                tk.setValue(big.floatValue());
                list.add(tk);
            }
        }

        var listInHoaDon = new ArrayList<BaoCaoDTO>();
        var result1 = repository.inHoaDon(maCN);
        if(maCN.equals("0")) result1 = repository.inHoaDonAllByDate(formattedDateNow,formattedDateNow);
        for (var i : result1){
            var ite1 = new BaoCaoDTO();
            ite1.setMaHD((String) i[0]);
            ite1.setMaDD((String) i[1]);
            String ns = formatter.format(i[2]);
            ite1.setNgayLap(ns);
            BigDecimal big = (BigDecimal) i[3];
            ite1.setTongTien(Helpers.convertMoney(big.floatValue()));
            ite1.setTenKH((String) i[4]);
            ite1.setSoluong((int) i[5]);
            listInHoaDon.add(ite1);
        }

        model.addAttribute("listInHoaDon",listInHoaDon);
        model.addAttribute("list",list);
        model.addAttribute("labels",labels);
        model.addAttribute("values",values);
        return "statistical";
    }



    @GetMapping("/order/detail/{id}/createSP")
    public String createSP(@PathVariable("id") String id, Model model, HttpServletRequest request){
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var listSP = sanPhamRepository.findAllSanPham();
        var listResultSP = new ArrayList<SanPhamDTO>(){};
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

            listResultSP.add(sp);
        }

        var chiTiet = new CTDonDatDTO();
        chiTiet.setMaDD(id);

        model.addAttribute("listResultSP", listResultSP);
        model.addAttribute("ctSanPhamDTO",chiTiet );

        return "dondat/createSP";
    }


    @PostMapping("/order/edit")
    public String editSP( Model model, DonDatDTO dondatDTO,HttpServletRequest request){
        var maCN = CookieUtils.getCookieValue(request,CookieUtils.machiNhanh);
        dondatDTO.setMaNV(CookieUtils.getCookieValue(request,CookieUtils.maNV));
        if(dondatDTO.getMaKH().isEmpty()){
            dondatDTO.setMaKH(Helpers.generateId());
            khachHangRepository.insert(dondatDTO.getMaKH(),dondatDTO.getTenKH(),dondatDTO.getSDT(),dondatDTO.getEmailKH());
        }
        else{
            khachHangRepository.update(dondatDTO.getMaKH(),dondatDTO.getTenKH(),dondatDTO.getSDT(),dondatDTO.getEmailKH());
        }

        repository.editDonDat2(dondatDTO.getTrangThai(),dondatDTO.getMaKH(),dondatDTO.getMaDD());

        if(dondatDTO.getTrangThai().equals("Hoàn thành")){
            var idHoaHon = Helpers.generateId();
            repository.payment(idHoaHon,dondatDTO.getMaDD(),dondatDTO.getTongTien2(),dondatDTO.getThanhToan(),dondatDTO.getMaNV());
            banRepository.update("Trống",dondatDTO.getMaBan());
            var maPX = Helpers.generateId();
            nguyenLieuRepository.xuatKho(maPX,dondatDTO.getMaNV(),maCN);
            var chiTiet = repository.chitietDonDat2(dondatDTO.getMaDD());
            for (var i : chiTiet){
                var maNL = (String) i[0];
                var tonKho = (int) i[1];
                var tongSoLuong = (int) i[2];
                nguyenLieuRepository.xuatKhoCT(maPX,maNL,tongSoLuong);
                sanPhamRepository.updateKho(tonKho - tongSoLuong,maNL);
            }
        }

        if(dondatDTO.getTrangThai().equals("Đang xử lý")){
            return "redirect:/table";
        }

        if(dondatDTO.isInBill()){
            return "redirect:/order/bill/" + dondatDTO.getMaDD();
        }

        return "redirect:/order";
    }

    @GetMapping("/order/bill/{id}")
    public String inBill(@PathVariable("id") String id,Model model, HttpServletRequest request){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));
        model.addAttribute("dateNow",formatter.format(new Date()));

        var data = repository.chitietDonDat(id);
        var i = data.get(0);
        var dd = new DonDatDTO();
        dd.setMaDD((String) i[0]);
        String ns = formatter.format(i[1]);
        dd.setThoiGianDat(ns);
        dd.setMaKH((String) i[2]);
        dd.setTenKH((String) i[7]);
        dd.setSDT((String) i[8]);
        dd.setEmailKH((String) i[9]);
        dd.setMaBan((String) i[4]);
        dd.setTrangThai((String) i[5]);

        float tongTien = 0;
        var listSP = new ArrayList<CTDonDatDTO>();
        for (var c : data){
            if(c[17] == null) continue;
            var item = new CTDonDatDTO();
            item.setMaDD((String) c[0]);
            item.setMaSP((String) c[17]);
            if (c[18] != null) {
                item.setSoLuong((int) c[18]);
            }

            item.setGhiChu((String) c[19]);

            var itemSp = new SanPhamDTO();
            itemSp.setTenSP((String) c[21]);
            itemSp.setMaLSP((String) c[22]);
            if(c[23] != null){
                BigDecimal big = (BigDecimal) c[23];
                itemSp.setDonGia(big.floatValue());
                itemSp.setDonGiaText(Helpers.convertMoney(itemSp.getDonGia()));
                tongTien += (float) (itemSp.getDonGia() * item.getSoLuong());
            }

            itemSp.setDonviTinh((String) c[24]);
            itemSp.setHinhAnh((String) c[26]);
            item.setSanPhamDTO(itemSp);

            listSP.add(item);
        }
        dd.setListSP(listSP);
        dd.setTongTien2(tongTien);

        model.addAttribute("tongTien","Tổng tiền: " + Helpers.convertMoney(tongTien));
        model.addAttribute("maDD",dd.getMaDD());
        model.addAttribute("dondatDTO", dd);
        model.addAttribute("khachHang", dd.getTenKH());

        return "dondat/bill";
    }

    @GetMapping("/order/detail/{id}/removeSP/{maSP}")
    public String removeSP(@PathVariable("id") String id,@PathVariable("maSP") String maSP, Model model, HttpServletRequest request){
        repository.removeChiTiet(id,maSP);
        var url = "redirect:/order/detail/" + id;
        return url;
    }

    @GetMapping("/order/detail/{id}/editSP/{maSP}")
    public String editSP(@PathVariable("id") String id,@PathVariable("maSP") String maSP, Model model, HttpServletRequest request){
        model.addAttribute("tenNV", CookieUtils.getCookieValue(request,CookieUtils.tenNV));
        model.addAttribute("vaiTro", CookieUtils.getCookieValue(request,CookieUtils.vaiTro));
        model.addAttribute("chiNhanh", CookieUtils.getCookieValue(request,CookieUtils.chiNhanh));

        var listSP = sanPhamRepository.findAllSanPham();
        var listResultSP = new ArrayList<SanPhamDTO>(){};
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

            listResultSP.add(sp);
        }

        var obj = repository.getDonDat(id,maSP).get(0);


        var chiTiet = new CTDonDatDTO();
        chiTiet.setMaDD(id);
        chiTiet.setMaSP(maSP);
        chiTiet.setSoLuong((int) obj[2]);
        chiTiet.setGhiChu((String) obj[3]);

        model.addAttribute("listResultSP", listResultSP);
        model.addAttribute("ctSanPhamDTO",chiTiet );
        model.addAttribute("maSP",maSP );

        return "dondat/editSP";
    }
}
