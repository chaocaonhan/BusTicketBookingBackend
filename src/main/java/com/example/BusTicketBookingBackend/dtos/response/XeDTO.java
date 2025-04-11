package com.example.BusTicketBookingBackend.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class XeDTO {
    int id;
    String tenXe;
    String bienSo;
    String loaiXe;
    String TrangThai;
}
