package com.example.BusTicketBookingBackend.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "diemdontra")
public class DiemDonTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tendiemdon", length = 100, nullable = false)
    private String tenDiemDon;

    @Column(name = "diachi", columnDefinition = "TEXT")
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "id_tinhthanh", referencedColumnName = "id")
    private TinhThanh tinhThanh;

    @Column(name = "trangthai")
    private int trangThai;
}

