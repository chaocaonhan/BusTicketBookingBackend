package com.example.BusTicketBookingBackend.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "diemdungtrentuyen")
public class DiemDungTrenTuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tuyenxe", referencedColumnName = "id")
    private TuyenXe tuyenXe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_diemdontra", referencedColumnName = "id")
    private DiemDonTra diemDonTra;

    @Column(name = "thutudiemdung")
    private int thuTuDiemDung;

    @Column(name = "khoangcachtoidiemdau")
    private int khoangCachToiDiemDau;

    @Column(name = "thoigiantudiemdau")
    private int thoiGianTuDiemDau;

    @Column(name = "trangthai")
    private int trangThai;
}
