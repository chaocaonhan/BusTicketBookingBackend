package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.response.XeDTO;
import com.example.BusTicketBookingBackend.models.Xe;
import com.example.BusTicketBookingBackend.repositories.XeRepository;
import com.example.BusTicketBookingBackend.service.XeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class XeServiceImpl implements XeService {

    private final XeRepository xeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<XeDTO> getAllXe() {
        List<Xe> lstXe = xeRepository.findAll();
        return lstXe.stream().map(xe -> {
            XeDTO xeDTO = modelMapper.map(xe, XeDTO.class); // Sửa lại ở đây
            xeDTO.setLoaiXe(xe.getLoaiXe().getTenLoaiXe());
            xeDTO.setTrangThai(xe.getTrangThai() == 1 ? "Active" : "Inactive");
            return xeDTO;
        }).collect(Collectors.toList());
    }

}
