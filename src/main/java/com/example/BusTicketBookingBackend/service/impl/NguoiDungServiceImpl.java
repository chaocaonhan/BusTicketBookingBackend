package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.config.JwtUtil;
import com.example.BusTicketBookingBackend.dtos.request.LoginDTO;
import com.example.BusTicketBookingBackend.dtos.response.NguoiDungDTO;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.repositories.NguoiDungRepository;
import com.example.BusTicketBookingBackend.repositories.VaiTroRepository;
import com.example.BusTicketBookingBackend.service.EmailService;
import com.example.BusTicketBookingBackend.service.NguoiDungService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NguoiDungServiceImpl implements NguoiDungService {

    NguoiDungRepository nguoiDungRepository;
    BCryptPasswordEncoder passwordEncoder;
    ModelMapper modelMapper;
    VaiTroRepository vaiTroRepository;
    EmailService emailService;
    AuthenticationManager authenticationManager;
    UserDetailsService userDetailsService;
    JwtUtil jwtUtil;


    @Override
    public NguoiDungDTO createNguoiDung(NguoiDungDTO nguoiDungDTO) {

        NguoiDung savedUser = new NguoiDung();
        if(nguoiDungRepository.existsNguoiDungByEmail(nguoiDungDTO.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXITS);
        }else {
//            để code = 123456 thay vì random
            String confirmToken = "123456";
//            String confirmToken = String.format("%06d", new Random().nextInt(999999));
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setHoTen(nguoiDungDTO.getHoTen());
            nguoiDung.setEmail(nguoiDungDTO.getEmail());
            nguoiDung.setSDT(nguoiDungDTO.getSDT());
            nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDungDTO.getMatKhau()));
            nguoiDung.setVaiTro(vaiTroRepository.findById(3));
            nguoiDung.setConfirmToken(confirmToken);
            nguoiDung.setTokenExpiry(LocalDateTime.now().plusMinutes(5));
            nguoiDung.setTrangThai(NguoiDung.trangthai.INACTIVE);
            savedUser = nguoiDungRepository.save(nguoiDung);


//            comment để tắt gửi mail, thêm dòng dưới để vẫn trả về response
//            try {
//                emailService.sendVerificationEmail(nguoiDungDTO.getEmail(),confirmToken);
//                result = String.valueOf(savedUser.getId());
//            }catch(MessagingException e){
//                result = "Tài khoản đã được tạo nhưng gửi email xác nhận thất bại.";
//            }
        }
        return NguoiDungDTO.builder()
                .id(savedUser.getId())
                .hoTen(savedUser.getHoTen())
                .SDT(savedUser.getSDT())
                .email(savedUser.getEmail())
                .loaiDangKi(savedUser.getLoaiDangKi())
                .vaiTro(savedUser.getVaiTro().getTenvaitro())
                .trangThai(String.valueOf(savedUser.getTrangThai()))
        .build();
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
            nguoiDungDTO.setLoaiDangKi(nguoiDung.getLoaiDangKi());
            nguoiDungDTO.setSDT(nguoiDung.getSDT());
            return nguoiDungDTO;
        }).toList();
    }

    @Override
    public NguoiDungDTO updateNguoiDung(Integer id, NguoiDungDTO nguoiDungDTO) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        modelMapper.map(nguoiDungDTO,nguoiDung);

        // Lưu vào database
        nguoiDung = nguoiDungRepository.save(nguoiDung);

        NguoiDungDTO updatedUser = modelMapper.map(nguoiDung, NguoiDungDTO.class);


        return updatedUser;
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

//    @Override
//    public String login(LoginDTO loginDTO) {
//        NguoiDung nd = nguoiDungRepository.findByEmail(loginDTO.getEmail());
//
//        if (nd == null){
//            return "NULL";// "Không tìm thấy người dùng"
//        }
//
//        if(!passwordEncoder.matches(loginDTO.getMatKhau(), nd.getMatKhau())) {
//            return "PASSWORD";// "Mật khẩu không đúng";
//        }
//        if ( nd.getTrangThai() == NguoiDung.trangthai.INACTIVE) {
//            return "LOCK OR VERIFY" ;
//        }
//        if (nd.getTrangThai() == NguoiDung.trangthai.ACTIVE) {
//            return "đang nhap thanh cong + trả về token ( chưa xử lý )";
//        }
//        return "other case";
//    }

    @Override
    public String login(LoginDTO loginDTO) {
        NguoiDung nd = nguoiDungRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));


        if (!passwordEncoder.matches(loginDTO.getMatKhau(), nd.getMatKhau())) {
            return "PASSWORD"; // "Mật khẩu không đúng"
        }

        if (nd.getTrangThai() == NguoiDung.trangthai.INACTIVE) {
            return "LOCK_OR_VERIFY";
        }

        if (nd.getTrangThai() == NguoiDung.trangthai.ACTIVE) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getMatKhau())
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
                String jwt = jwtUtil.generateToken(userDetails, nd.getVaiTro().getTenvaitro());

                return jwt;
            } catch (Exception e) {
                return "AUTH_ERROR";
            }
        }

        return "OTHER_CASE";
    }

    @Override
    public NguoiDungDTO getMyInfor(){
        var context = SecurityContextHolder.getContext();
        String mail = context.getAuthentication().getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(mail)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));

        return modelMapper.map(nguoiDung, NguoiDungDTO.class);
    }

    @Override
    public Boolean deleteNguoiDungById(Integer id) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)

        );
        nguoiDungRepository.delete(nguoiDung);
        return true;

    }


}
