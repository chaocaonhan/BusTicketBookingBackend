package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.dtos.LoginDTO;
import com.example.BusTicketBookingBackend.dtos.NguoiDungDTO;
import com.example.BusTicketBookingBackend.models.DiemDungTrenTuyen;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.repositories.DiemDungTrenTuyenRepository;
import com.example.BusTicketBookingBackend.repositories.NguoiDungRepository;
import com.example.BusTicketBookingBackend.repositories.VaiTroRepository;
import com.example.BusTicketBookingBackend.service.EmailService;
import com.example.BusTicketBookingBackend.service.NguoiDungService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class NguoiDungServiceImpl implements NguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private  final ModelMapper modelMapper;
    private final VaiTroRepository vaiTroRepository;
    private final EmailService emailService;


    @Override
    public String createNguoiDung(NguoiDungDTO nguoiDungDTO) {
        String result = "";
        if(nguoiDungRepository.existsNguoiDungByEmail(nguoiDungDTO.getEmail())){
            result += "Email đã tồn tại";
        }else {
            String confirmToken = String.format("%06d", new Random().nextInt(999999));
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setHoTen(nguoiDungDTO.getHoTen());
            nguoiDung.setEmail(nguoiDungDTO.getEmail());
            nguoiDung.setSDT(nguoiDungDTO.getSDT());
            nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
            nguoiDung.setVaiTro(vaiTroRepository.findById(3));
            nguoiDung.setConfirmToken(confirmToken);
            nguoiDung.setTokenExpiry(LocalDateTime.now().plusMinutes(5));
            nguoiDung.setTrangThai(NguoiDung.trangthai.INACTIVE);
            NguoiDung savedUser = nguoiDungRepository.save(nguoiDung);
            try {
                emailService.sendVerificationEmail(nguoiDungDTO.getEmail(),confirmToken);
                result = String.valueOf(savedUser.getId());
            }catch(MessagingException e){
                result = "Tài khoản đã được tạo nhưng gửi email xác nhận thất bại.";
            }
        }
        return result;
    }

    @Override
    public NguoiDung getNguoiDung(int id) {
        return null;
    }

    @Override
    public NguoiDung setNguoiDung(NguoiDung nguoiDung){
        return null;
    }

    @Override
    public Optional<NguoiDungDTO> getNguoiDungByID(int id) {
        Optional<NguoiDung> nguoiDung = nguoiDungRepository.findById(id); // Chỉ gọi một lần
        if(nguoiDung.isPresent()){
            NguoiDung nd = nguoiDung.get();
            NguoiDungDTO dto = modelMapper.map(nd, NguoiDungDTO.class);
            dto.setVaiTro(nd.getVaiTro().getTenvaitro());
            return Optional.of(dto);
        }
        else {
            // Ghi log khi không tìm thấy người dùng
            return Optional.empty();
        }
    }


    @Override
    public List<NguoiDungDTO> getAllNguoiDung() {
        List<NguoiDung> nguoiDungs = nguoiDungRepository.findAll();
        return nguoiDungs.stream().map(nguoiDung -> {
            NguoiDungDTO nguoiDungDTO = modelMapper.map(nguoiDung, NguoiDungDTO.class);
            nguoiDungDTO.setVaiTro(nguoiDung.getVaiTro().getTenvaitro());
            return nguoiDungDTO;
        }).toList();
    }

    @Override
    public String updateNguoiDung(NguoiDungDTO nguoiDungDto) {
        Optional<NguoiDung> nguoiDung = nguoiDungRepository.findById(nguoiDungDto.getId());
        if (nguoiDung.isPresent()) {
            NguoiDung nguoiDungModel = nguoiDung.get();

            // Dùng ModelMapper để cập nhật dữ liệu từ DTO vào Entity
            modelMapper.map(nguoiDungDto, nguoiDungModel);

            // Lưu vào database
            nguoiDungRepository.save(nguoiDungModel);

            return "Cập nhật thành công!";
        } else {
            return "Không tìm thấy người dùng!";
        }
    }

    @Override
    public Boolean verifyUser(Integer userId, String inputToken) {
        Optional<NguoiDung> userOptional = nguoiDungRepository.findById(userId);
        if (userOptional.isPresent()) {
            NguoiDung user = userOptional.get();
            if (user.getConfirmToken() != null &&
                    user.getConfirmToken().equals(inputToken) &&
                    LocalDateTime.now().isBefore(user.getTokenExpiry())) {

                // Kích hoạt tài khoản và xóa token
                user.setTrangThai(NguoiDung.trangthai.ACTIVE);
                user.setConfirmToken(null);
                user.setTokenExpiry(null);
                nguoiDungRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        NguoiDung nd = nguoiDungRepository.findByEmail(loginDTO.getEmail());

        if (nd == null){
            return "NULL";// "Không tìm thấy người dùng"
        }

        if(!passwordEncoder.matches(loginDTO.getMatKhau(), nd.getMatKhau())) {
            return "PASSWORD";// "Mật khẩu không đúng";
        }
        if ( nd.getTrangThai() == NguoiDung.trangthai.INACTIVE) {
            return "LOCK OR VERIFY" ;
        }
        if (nd.getTrangThai() == NguoiDung.trangthai.ACTIVE) {
            return "đang nhap thanh cong + trả về token ( chưa xử lý )";
        }
        return "other case";
    }


}
