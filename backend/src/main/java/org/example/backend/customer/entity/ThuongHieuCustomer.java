package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "thuong_hieu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuongHieuCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_thuong_hieu", length = 20, unique = true)
    private String maThuongHieu;

    @Column(name = "ten_thuong_hieu", length = 150)
    private String tenThuongHieu;

    @Column(name = "mo_ta", length = 255)
    private String moTa;

    @Column(name = "trang_thai")
    private Boolean trangThai = true;
}