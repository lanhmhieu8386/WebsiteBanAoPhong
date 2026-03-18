package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table (name = "thuong_hieu")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ThuongHieu {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "ma_thuong_hieu")
    private String maThuongHieu;

    @Column (name = "ten_thuong_hieu")
    private String tenThuongHieu;

    @Column (name = "mo_ta")
    private String moTa;

    @Column (name = "trang_thai")
    private Boolean trangThai;
}
