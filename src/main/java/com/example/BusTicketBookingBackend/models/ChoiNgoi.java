package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chongoi")
public class ChoiNgoi {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_loaixe", referencedColumnName = "id")
    private LoaiXe loaiXe;

    @Column(name = "ten_ghe")
    private String tenghe;
}