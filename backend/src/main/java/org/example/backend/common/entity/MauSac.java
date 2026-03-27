package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table (name = "mau_sac")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MauSac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "ma_mau_sac")
    private String maMauSac;

    @Column (name = "ten_mau_sac")
    private String tenMauSac;
}
