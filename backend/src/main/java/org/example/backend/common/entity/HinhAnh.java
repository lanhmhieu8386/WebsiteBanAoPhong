package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table (name = "hinh_anh")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HinhAnh {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "url_anh")
    private String urlAnh;

    @Column (name = "ngay_tao")
    private Date ngayTao;



}
