package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "chongoi")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChoiNgoi {
    @Id
    @Column(name = "id", nullable = false)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_loaixe", referencedColumnName = "id")
    LoaiXe loaiXe;

    @Column(name = "ten_ghe")
    String tenghe;

    @Column(name = "hang")
    int hang;

    @Column(name = "cot")
    int cot;

    @Column(name ="tang")
    int tang;
}