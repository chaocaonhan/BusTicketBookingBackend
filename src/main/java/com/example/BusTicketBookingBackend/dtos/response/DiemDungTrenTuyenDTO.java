package com.example.BusTicketBookingBackend.dtos.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiemDungTrenTuyenDTO {
    private int id;
    private String tenTuyenXe;
    private String tenDiemDon;
    int idDiemDonTra;
    private String diaChi;
    private int thuTuDiemDung;
    private float khoangCachToiDiemDau;
    private int thoiGianTuDiemDau;
}
