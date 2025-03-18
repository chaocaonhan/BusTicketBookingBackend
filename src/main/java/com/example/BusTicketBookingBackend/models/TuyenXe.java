package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tuyenxe")
public class TuyenXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tentuyen", length = 100, nullable = false)
    private String tenTuyen;

    @ManyToOne
    @JoinColumn(name = "id_tinhdi", referencedColumnName = "id")
    private TinhThanh tinhDi;

    @ManyToOne
    @JoinColumn(name = "id_tinhden", referencedColumnName = "id")
    private TinhThanh tinhDen;

    @Column(name = "khoangcach")
    private int khoangCach;

    @Column(name = "thoigiandichuyen", length = 50)
    private String thoiGianDiChuyen;
}

