package org.example.backend.admin.service.serviceImpl;

import org.example.backend.admin.service.HoaDonService;
import org.example.backend.common.dto.response.HoaDonResponse;
import org.example.backend.common.entity.HoaDon;
import org.example.backend.common.entity.LichSuDonHang;
import org.example.backend.common.repository.HoaDonRepository;
import org.example.backend.common.repository.LichSuDonHangRepository;
import org.example.backend.customer.enums.TrangThaiHoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private LichSuDonHangRepository lichSuDonHangRepository;

    @Override
    public List<HoaDonResponse> getDanhSachDonHang() {
        return hoaDonRepository.getDanhSachDonHang();
    }

    @Override
    public List<HoaDonResponse> getDanhSachHoaDon() {
        return hoaDonRepository.getDanhSachHoaDon();
    }

    @Override
    public HoaDonResponse getDetail(Integer id) {
        HoaDonResponse response = hoaDonRepository.getDetailResponseById(id);
        if (response == null) {
            throw new RuntimeException("Không tìm thấy hóa đơn với id = " + id);
        }
        return response;
    }

    @Override
    public HoaDonResponse capNhatTrangThaiDonHang(Integer id, Integer trangThaiMoi, String nguoiCapNhat) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với id = " + id));

        Integer trangThaiCu = hoaDon.getTrangThai();

        if (!(trangThaiCu == 1 || trangThaiCu == 2 || trangThaiCu == 3)) {
            throw new RuntimeException("Hóa đơn này không thuộc tab Đơn hàng");
        }

        boolean hopLe =
                (trangThaiCu == 1 && (trangThaiMoi == 2 || trangThaiMoi == 5)) ||
                        (trangThaiCu == 2 && (trangThaiMoi == 3 || trangThaiMoi == 5)) ||
                        (trangThaiCu == 3 && (trangThaiMoi == 4 || trangThaiMoi == 5));

        if (!hopLe) {
            throw new RuntimeException(
                    "Không thể chuyển trạng thái từ "
                            + TrangThaiHoaDon.fromValue(trangThaiCu).getLabel()
                            + " sang "
                            + TrangThaiHoaDon.fromValue(trangThaiMoi).getLabel()
            );
        }

        hoaDon.setTrangThai(trangThaiMoi);
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setNguoiCapNhat(nguoiCapNhat);

        hoaDonRepository.save(hoaDon);

        themLichSuDonHang(hoaDon, trangThaiMoi, nguoiCapNhat);

        return getDetail(id);
    }

    @Override
    public HoaDonResponse capNhatTrangThaiHoaDon(Integer id, Integer trangThaiMoi, String nguoiCapNhat) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với id = " + id));

        Integer trangThaiCu = hoaDon.getTrangThai();

        if (!(trangThaiCu == 4 || trangThaiCu == 5)) {
            throw new RuntimeException("Hóa đơn này không thuộc tab Hóa đơn");
        }

        boolean hopLe = (trangThaiCu == 4 && trangThaiMoi == 5);

        if (!hopLe) {
            throw new RuntimeException(
                    "Không thể chuyển trạng thái từ "
                            + TrangThaiHoaDon.fromValue(trangThaiCu).getLabel()
                            + " sang "
                            + TrangThaiHoaDon.fromValue(trangThaiMoi).getLabel()
            );
        }

        hoaDon.setTrangThai(trangThaiMoi);
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setNguoiCapNhat(nguoiCapNhat);

        hoaDonRepository.save(hoaDon);

        themLichSuDonHang(hoaDon, trangThaiMoi, nguoiCapNhat);

        return getDetail(id);
    }

    private void themLichSuDonHang(HoaDon hoaDon, Integer trangThaiMoi, String nguoiCapNhat) {
        LichSuDonHang lichSu = LichSuDonHang.builder()
                .maLichSu("LS" + System.currentTimeMillis())
                .idTrangThai(trangThaiMoi)
                .thoiGian(LocalDateTime.now())
                .ghiChu("Cập nhật trạng thái sang: " + layTenTrangThai(trangThaiMoi) + " bởi " + nguoiCapNhat)
                .idHoaDon(hoaDon.getId())
                .build();

        lichSuDonHangRepository.save(lichSu);
    }

    private String layTenTrangThai(Integer trangThai) {
        try {
            return TrangThaiHoaDon.fromValue(trangThai).getLabel();
        } catch (Exception e) {
            return "Trạng thái " + trangThai;
        }
    }
}