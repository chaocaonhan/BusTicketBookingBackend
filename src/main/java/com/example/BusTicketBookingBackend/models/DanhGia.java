package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "danhgia")
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idnguoidung")
    NguoiDung Nguoidung;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddondatve")
    DonDatVe donDatVe;

    @Column(name = "sosao")
    private Integer soSao;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}