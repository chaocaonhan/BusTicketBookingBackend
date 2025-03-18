package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "loaixe")
public class LoaiXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tenloaixe")
    private String tenLoaiXe;

    @Column(name = "soluongghe")
    private int soLuongGhe;

    @Column(name = "mota")
    private String moTa;
}
