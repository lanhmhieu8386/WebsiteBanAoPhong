package org.example.backend.customer.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.common.entity.VaiTro;

import java.time.LocalDateTime;

@Entity
@Table(name = "tai_khoan")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaiKhoanCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ma_tai_khoan")
    private String maTaiKhoan;

    @Column(name = "ten_tai_khoan")
    private String tenTaiKhoan;

    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "dia_chi")
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "id_vai_tro")
    private VaiTro vaiTro;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

}