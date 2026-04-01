package org.example.backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_lieu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatLieuCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_chat_lieu", length = 20, unique = true)
    private String maChatLieu;

    @Column(name = "ten_chat_lieu", length = 150)
    private String tenChatLieu;

    @Column(name = "mo_ta", length = 255)
    private String moTa;

    @Column(name = "trang_thai")
    private Boolean trangThai = true;
}
