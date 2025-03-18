package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "xe")
public class Xe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tenxe", length = 100, nullable = false)
    private String tenXe;

    @Column(name = "bienso", length = 20, unique = true, nullable = false)
    private String bienSo;

    @ManyToOne
    @JoinColumn(name = "id_loaixe", referencedColumnName = "id")
    private LoaiXe loaiXe;

    @Column(name = "trangthai")
    private int trangThai;
}
