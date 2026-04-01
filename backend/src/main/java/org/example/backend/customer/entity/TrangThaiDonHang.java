package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trang_thai_don_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrangThaiDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_trang_thai")
    private String maTrangThai;

    @Column(name = "ten_trang_thai")
    private String tenTrangThai;
}