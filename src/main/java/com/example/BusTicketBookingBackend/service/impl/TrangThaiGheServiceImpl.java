package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.service.TrangThaiGheService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrangThaiGheServiceImpl implements TrangThaiGheService {


    @Override
    public String TaoDanhSachGheTrenChuyenXe(Integer maChuyenXe) {
        return "haha";
    }
}
