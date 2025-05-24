package com.example.BusTicketBookingBackend.dtos.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class ChuyenXeVaGheCanDat {
    private Integer idChuyenXe;
    private List<Integer> danhSachGheMuonDat;
    private String diemDon;
    private String diemTra;
}