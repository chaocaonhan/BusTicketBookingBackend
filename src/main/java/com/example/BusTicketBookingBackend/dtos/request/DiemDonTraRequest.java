package com.example.BusTicketBookingBackend.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class DiemDonTraRequest {
    String tenDiemDonTra;
    String diaChi;
    int maTinh;
}
