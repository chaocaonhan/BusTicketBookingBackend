package com.example.BusTicketBookingBackend.service;

import com.example.BusTicketBookingBackend.dtos.NguoiDungDTO;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.repositories.NguoiDungRepository;
import com.example.BusTicketBookingBackend.service.iservice.INguoiDungService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NguoiDungService implements INguoiDungService {
    private final NguoiDungRepository nguoiDungRepository;
    private final ModelMapper modelMapper;

    @Override
    public NguoiDung createNguoiDung(NguoiDung nguoiDung) {
        return null;
    }

    @Override
    public NguoiDung getNguoiDung(int id) {
        return null;
    }

    @Override
    public List<NguoiDungDTO> getAllNguoiDung() {
        List<NguoiDung> nguoiDungs = nguoiDungRepository.findAll();
        return nguoiDungs.stream().map(nguoiDung -> {
            NguoiDungDTO nguoiDungDTO = modelMapper.map(nguoiDung, NguoiDungDTO.class);
            nguoiDungDTO.setVaiTro(nguoiDung.getVaiTro().getTenVaiTro());
            return nguoiDungDTO;
        }).toList();
    }

    @Override
    public NguoiDung updateNguoiDung(NguoiDung nguoiDung) {
        return null;
    }
}
