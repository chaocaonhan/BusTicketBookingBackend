package com.example.BusTicketBookingBackend.models;

import com.example.BusTicketBookingBackend.enums.TrangThaiGhe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "datghe")
public class DatGhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chuyenxe")
    private ChuyenXe chuyenXe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chongoi")
    private ChoiNgoi choNgoi;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangthai", nullable = false)
    TrangThaiGhe trangThai;
}