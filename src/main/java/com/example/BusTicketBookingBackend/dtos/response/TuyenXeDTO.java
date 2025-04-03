package com.example.BusTicketBookingBackend.dtos.response;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TuyenXeDTO {

    private int id;
    private String tenTuyen;
    private String tinhDi;
    private String tinhDen;
    private int khoangCach;
    private String thoiGianDiChuyen;

}
