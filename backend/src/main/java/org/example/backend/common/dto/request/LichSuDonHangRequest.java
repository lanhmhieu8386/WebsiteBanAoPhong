package org.example.backend.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuDonHangRequest {

    private String maLichSu;
    private Integer idTrangThai;
    private LocalDateTime thoiGian;
    private String ghiChu;
    private Integer idHoaDon;
}