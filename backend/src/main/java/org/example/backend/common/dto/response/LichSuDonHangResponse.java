package org.example.backend.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class LichSuDonHangResponse {

    private Integer id;
    private String maLichSu;
    private Integer idTrangThai;
    private LocalDateTime thoiGian;
    private String ghiChu;
    private Integer idHoaDon;

    public LichSuDonHangResponse(
            Integer id,
            String maLichSu,
            Integer idTrangThai,
            LocalDateTime thoiGian,
            String ghiChu,
            Integer idHoaDon
    ) {
        this.id = id;
        this.maLichSu = maLichSu;
        this.idTrangThai = idTrangThai;
        this.thoiGian = thoiGian;
        this.ghiChu = ghiChu;
        this.idHoaDon = idHoaDon;
    }
}