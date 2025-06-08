package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "khuyenmai")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "makhuyenmai")
    private String maKhuyenMai;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "phantramgiam")
    private int phanTramGiam;

    @Column(name = "ngaybatdau")
    private LocalDate ngayBatDau;

    @Column(name = "ngayketthuc")
    private LocalDate ngayKetThuc;

    @Column(name = "trangthai")
    private int trangThai;
}
