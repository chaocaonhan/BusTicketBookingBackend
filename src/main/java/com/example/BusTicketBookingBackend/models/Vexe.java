package com.example.BusTicketBookingBackend.models;

import com.example.BusTicketBookingBackend.enums.TrangThaiVe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JoinColumn(name = "madondatve")
    private DonDatVe donDatVe;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madatghe")
    private DatGhe datGhe;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "trangthaive", nullable = false, length = 20)
    private TrangThaiVe trangThaiVe;

    @Column(name = "diemdon")
    private String diemDon;

    @Column(name = "diemtra")
    private String diemTra;
}
