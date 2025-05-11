package com.example.BusTicketBookingBackend.dtos.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class ChuyenDiInfo {
    private Integer idChuyenXe;
    private List<Integer> danhSachGheMuonDat;
    // Getters và setters
}