package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vaitro")
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tenVaiTro", nullable = false, length = 50)
    private String tenVaiTro;

    @Column(name = "moTa")
    private String moTa;

    @OneToMany(mappedBy = "vaiTro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NguoiDung> nguoiDungs;

}
