package com.example.BusTicketBookingBackend.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatusCode;

import javax.swing.text.html.HTML;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    private int code;
    private String message;
    private T result;
}
