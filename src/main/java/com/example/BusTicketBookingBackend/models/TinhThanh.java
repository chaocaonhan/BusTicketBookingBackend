package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tinhthanh")
public class TinhThanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tentinhthanh")
    private String tenTinhThanh;

    @Column(name = "anh1")
    private String anh1;



}
