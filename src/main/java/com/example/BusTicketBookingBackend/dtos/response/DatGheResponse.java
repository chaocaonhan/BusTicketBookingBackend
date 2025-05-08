package com.example.BusTicketBookingBackend.dtos.response;

import com.example.BusTicketBookingBackend.enums.TrangThaiGhe;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatGheResponse {
    Integer id;
    String tenGhe;
    int tang;
    int hang;
    int cot;
    int trangThai;
}
