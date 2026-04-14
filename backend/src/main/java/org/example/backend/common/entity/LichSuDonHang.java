package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lich_su_don_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_lich_su", length = 20)
    private String maLichSu;

    @Column(name = "id_trang_thai")
    private Integer idTrangThai;

    @Column(name = "thoi_gian")
    private LocalDateTime thoiGian;

    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(255)")
    private String ghiChu;

    @Column(name = "id_hoa_don")
    private Integer idHoaDon;
}