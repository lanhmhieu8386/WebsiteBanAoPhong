package org.example.backend.customer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lich_su_don_hang")
public class LichSuHoaDonCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDonCustomer hoaDon;

    private String hanhDong;

    private String ghiChu;

    private LocalDateTime thoiGian;
}