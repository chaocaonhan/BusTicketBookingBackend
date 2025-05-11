package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.request.ChuyenDiInfo;
import com.example.BusTicketBookingBackend.dtos.response.DatGheResponse;
import com.example.BusTicketBookingBackend.enums.TrangThaiGhe;
import com.example.BusTicketBookingBackend.models.DatGhe;
import com.example.BusTicketBookingBackend.repositories.DatGheRepository;
import com.example.BusTicketBookingBackend.service.DatGheService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatGheServiceImpl implements DatGheService {
    DatGheRepository datGheRepository;
    ModelMapper modelMapper;

    @Override
    public List<DatGheResponse> getByChuyenXe(Integer idChuyenXe) {
        List<DatGhe> datGhes = datGheRepository.findByChuyenXe_Id(idChuyenXe);
        return datGhes.stream().map(
                datGhe -> {
                    DatGheResponse datGheResponse = new DatGheResponse();
                    datGheResponse.setId(datGhe.getId());
                    datGheResponse.setHang(datGhe.getChoNgoi().getHang());
                    datGheResponse.setCot(datGhe.getChoNgoi().getCot());
                    datGheResponse.setTenGhe(datGhe.getChoNgoi().getTenghe());
                    datGheResponse.setTrangThai(datGhe.getTrangThai() == TrangThaiGhe.BOOKED ?1 : 0);
                    datGheResponse.setTang(datGhe.getChoNgoi().getTang());

                    return datGheResponse;
                }).toList();
    }

    @Override
    public boolean seatSelectedIsAvaible(List<Integer> datgheIds) {
        // Lấy danh sách `DatGhe` từ repository theo IDs truyền vào
        List<DatGhe> datGhes = datGheRepository.findAllById(datgheIds);

        // Kiểm tra nếu tất cả `DatGhe` đều có trạng thái AVAILABLE
        return datGhes.stream().allMatch(datGhe -> datGhe.getTrangThai() == TrangThaiGhe.AVAILABLE);

    }

    @Override
    public boolean capNhatTrangThaiGhe(ChuyenDiInfo chuyenXe) {
        List<DatGhe> danhSachGheCanCapNhat = datGheRepository.findAllById(chuyenXe.getDanhSachGheMuonDat());
        danhSachGheCanCapNhat.forEach(datGhe -> datGhe.setTrangThai(TrangThaiGhe.BOOKED));
        datGheRepository.saveAll(danhSachGheCanCapNhat);
        return true;
    }

}
