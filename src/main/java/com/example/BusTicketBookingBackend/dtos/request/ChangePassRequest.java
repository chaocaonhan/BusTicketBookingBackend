package com.example.BusTicketBookingBackend.dtos.request;

import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class ChangePassRequest {
    private String oldPass;
    private String newPass;
}
