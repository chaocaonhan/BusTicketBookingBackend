package com.example.BusTicketBookingBackend.models;

import com.example.BusTicketBookingBackend.enums.TrangThaiVe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vexe")
public class Vexe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDonDatVe")
    private DonDatVe maDonDatVe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaChuyenXe")
    private ChuyenXe maChuyenXe;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaChoNgoi")
    private DatGhe maDatGhe;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "TrangThaiVe", nullable = false, length = 20)
    private TrangThaiVe trangThaiVe;
}
