package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.response.XeDTO;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.LoaiXe;
import com.example.BusTicketBookingBackend.models.Xe;
import com.example.BusTicketBookingBackend.repositories.LoaiXeRepository;
import com.example.BusTicketBookingBackend.repositories.XeRepository;
import com.example.BusTicketBookingBackend.service.XeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class XeServiceImpl implements XeService {

    private final XeRepository xeRepository;
    private final ModelMapper modelMapper;
    private final LoaiXeRepository loaiXeRepository;

    @Override
    public List<XeDTO> getAllXe() {
        List<Xe> lstXe = xeRepository.findByTrangThaiNot(2);
        return lstXe.stream().map(xe -> {
            XeDTO xeDTO = modelMapper.map(xe, XeDTO.class);
            xeDTO.setLoaiXe(xe.getLoaiXe().getTenLoaiXe());
            xeDTO.setTrangThai(xe.getTrangThai() == 1 ? "Active" : "Inactive");
            return xeDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public XeDTO suaThongTinXe(Integer idXe, XeDTO xeDTO) {
        Xe xe = xeRepository.findById(idXe).get();
        if (xe == null) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        xe.setLoaiXe(loaiXeRepository.findLoaiXeByTenLoaiXe(xeDTO.getLoaiXe()));
        if(xeDTO.getTrangThai().equals("Active")) {
            xe.setTrangThai(1);
        }

        else xe.setTrangThai(0);
        xe.setBienSo(xeDTO.getBienSo());
        xe.setTenXe(xeDTO.getTenXe());

        xeRepository.save(xe);

        return modelMapper.map(xe, XeDTO.class);
    }

    @Override
    public Object createXe(XeDTO xeDTO) {
        Xe xeMoi = new Xe();
        xeMoi.setTenXe(xeDTO.getTenXe());
        xeMoi.setBienSo(xeDTO.getBienSo());
        xeMoi.setLoaiXe(loaiXeRepository.findLoaiXeByTenLoaiXe(xeDTO.getLoaiXe()));
        xeMoi.setTrangThai(1);
        xeRepository.save(xeMoi);
        return xeMoi;
    }
}
