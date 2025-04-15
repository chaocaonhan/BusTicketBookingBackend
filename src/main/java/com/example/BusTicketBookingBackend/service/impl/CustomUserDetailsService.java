package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.enums.TrangThai;
import com.example.BusTicketBookingBackend.exception.AppException;
import com.example.BusTicketBookingBackend.exception.ErrorCode;
import com.example.BusTicketBookingBackend.models.NguoiDung;
import com.example.BusTicketBookingBackend.repositories.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        if (nguoiDung == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + nguoiDung.getVaiTro().getTenvaitro());

        return new User(
                nguoiDung.getEmail(),
                nguoiDung.getMatKhau(),
                nguoiDung.getTrangThai() == TrangThai.ACTIVE,
                true,
                true,
                true,
                Collections.singletonList(authority)
        );
    }
}