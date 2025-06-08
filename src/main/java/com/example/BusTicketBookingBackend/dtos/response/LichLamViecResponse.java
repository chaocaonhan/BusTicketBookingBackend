package com.example.BusTicketBookingBackend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichLamViecResponse {
    private LocalDate ngay;
    private List<CaLamViec> caLamViec;
}
