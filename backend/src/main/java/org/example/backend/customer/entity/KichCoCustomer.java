package org.example.backend.customer.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kich_co")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KichCoCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_kich_co", length = 20)
    private String maKichCo;

    @Column(name = "ten_kich_co", length = 20)
    private String tenKichCo;
}
