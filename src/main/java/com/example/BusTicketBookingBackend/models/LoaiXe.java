package com.example.BusTicketBookingBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "loaixe")
public class LoaiXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tenloaixe")
    private String tenLoaiXe;

    @Column(name = "soluongghe")
    private int soLuongGhe;

    @Column(name = "mota")
    private String moTa;


    @JsonIgnore
    @OneToMany(mappedBy = "loaiXe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChoiNgoi> choiNgoi;
}
