package com.example.BusTicketBookingBackend.dtos.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChuyenXeResponse {

    private Integer id;
    private String tenTuyen;
    private String diemDi;
    private String diemDen;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate ngayKhoiHanh;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime gioKhoiHanh;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime gioKetThuc;


    private String bienSo;
    private String taiXe;

    private int giaVe;
    private String tenLoaiXe;
    private int  soGheTrong;
}
