package com.example.BusTicketBookingBackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "vaitro")
public class VaiTro {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tenvaitro", nullable = false, length = 50)
    private String tenvaitro;

    @Column(name = "mota")
    private String mota;

}
