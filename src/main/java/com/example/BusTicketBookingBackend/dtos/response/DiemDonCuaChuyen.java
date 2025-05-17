package com.example.BusTicketBookingBackend.dtos.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class DiemDonCuaChuyen {
    String tenDiemDon;
    int thuTu;
    float khoangCahDenDiemDau;
    LocalTime thoiGianXeDen;
}
