package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chuyenxe")
public class ChuyenXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_tuyenxe", referencedColumnName = "id")
    private TuyenXe tuyenXe;

    @ManyToOne
    @JoinColumn(name = "id_xe", referencedColumnName = "id")
    private Xe xe;

    @ManyToOne
    @JoinColumn(name = "id_taixe", referencedColumnName = "id")
    private TaiXe taiXe;

    @ManyToOne
    @JoinColumn(name = "id_diemdi", referencedColumnName = "id")
    private DiemDonTra diemDi;

    @ManyToOne
    @JoinColumn(name = "id_diemden", referencedColumnName = "id")
    private DiemDonTra diemDen;

    @Column(name = "ngaykhoihanh")
    private LocalDate ngayKhoiHanh;

    @Column(name = "giokhoihanh")
    private LocalTime gioKhoiHanh;

    @Column(name = "gioketthuc")
    private LocalTime gioKetThuc;

    @Column(name = "giave")
    private int giaVe;

    @Column(name = "soghetrong")
    private int soGheTrong;

    public enum TrangThai {
        SCHEDULED,
        DEPARTED,
        COMPLETED,
        CANCELED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "trangthai", nullable = false)
    private TrangThai trangThai = TrangThai.SCHEDULED;
}
