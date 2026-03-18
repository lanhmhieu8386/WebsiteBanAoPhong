package org.example.backend.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table (name = "kich_co")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KichCo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "ma_kich_co")
    private String maKichCo;

    @Column (name = "ten_kich_co")
    private String tenKichCo;
}
