package com.example.BusTicketBookingBackend.models;

import com.example.BusTicketBookingBackend.enums.TrangThai;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "nguoidung")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "hoten")
    String hoTen;


    @Column(name = "email")
    String email;


    @Column(name = "matkhau")
    String matKhau;

    @Column(name = "sdt")
    String SDT;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangthai")
    TrangThai trangThai;

    @Column(name = "loaidangki")
    String loaiDangKi;

    @Column(name = "confirmtoken")
    String confirmToken;

    @Column(name = "tokenexpiry")
    LocalDateTime tokenExpiry;

    @ManyToOne
    @JoinColumn(name = "idvaitro", referencedColumnName = "id")
    VaiTro vaiTro;

}
