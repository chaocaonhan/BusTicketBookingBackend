package com.example.BusTicketBookingBackend.dtos.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class DatVeRequest {
    private Integer userId;
    private String hoTen;
    private String sdt;
    private String email;
    private Integer tongTien;
    private String kieuThanhToan;
    private int trangThaiThanhToan;
    private int loaiChuyenDi;
    private ChuyenDiInfo chuyenDi;
    private ChuyenDiInfo chuyenVe;
}
