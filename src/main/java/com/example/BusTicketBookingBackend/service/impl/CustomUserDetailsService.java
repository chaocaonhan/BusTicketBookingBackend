package com.example.BusTicketBookingBackend.service.impl;

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
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email);
        if (nguoiDung == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + nguoiDung.getVaiTro().getTenvaitro());

        return new User(
                nguoiDung.getEmail(),
                nguoiDung.getMatKhau(),
                nguoiDung.getTrangThai() == NguoiDung.trangthai.ACTIVE,
                true,
                true,
                true,
                Collections.singletonList(authority)
        );
    }
}