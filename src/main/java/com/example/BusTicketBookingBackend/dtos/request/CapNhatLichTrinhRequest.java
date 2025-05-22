package com.example.BusTicketBookingBackend.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CapNhatLichTrinhRequest {
    Integer idTuyen;
    List<Integer> danhSachDiemDonTheoThuTu;
}
