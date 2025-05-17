package com.example.BusTicketBookingBackend.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DanhGiaResponse {
    int id;
    String tenKhachHang;
    String noiDung;
    String tenChuyenXe;
    int soSao;
    String anhNguoiDung;

}
