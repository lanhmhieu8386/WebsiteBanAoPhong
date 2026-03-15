package org.example.backend.common.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vai_tro")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma_vai_tro")
    private String maVaiTro;

    @Column(name = "ten_vai_tro")
    private String tenVaiTro;

    @Column(name = "mo_ta")
    private String moTa;
}
