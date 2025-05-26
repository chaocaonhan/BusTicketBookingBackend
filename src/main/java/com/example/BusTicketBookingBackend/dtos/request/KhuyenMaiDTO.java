package com.example.BusTicketBookingBackend.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class KhuyenMaiDTO {
    private String maKhuyenMai;
    private int phanTramGiam;
    private String moTa;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
}
