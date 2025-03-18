package com.example.BusTicketBookingBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "nguoidung")
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hoten")
    private String hoTen;

    @Column(name = "email")
    private String email;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "sdt")
    private String SDT;

    public enum trangthai{
        ACTIVE,
        INACTIVE
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "trangthai")
    private trangthai trangThai;

    @Column(name = "loaidangki")
    private String loaiDangKi;

    @Column(name = "confirmtoken")
    private String confirmToken;

    @Column(name = "tokenexpiry")
    private LocalDateTime tokenExpiry;

//    @Column(name = "id_vaiTro")
//    private int id_VaiTro;

    @ManyToOne
    @JoinColumn(name = "idvaitro", referencedColumnName = "id")
    private VaiTro vaiTro;


}
