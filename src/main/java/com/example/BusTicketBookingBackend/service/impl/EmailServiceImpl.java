package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.models.DonDatVe;
import com.example.BusTicketBookingBackend.repositories.DonDatVeRepository;
import com.example.BusTicketBookingBackend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final DonDatVeRepository donDatVeRepository;


    @Override
    public void sendVerificationEmail(String to, String code) throws MessagingException {
        Context context = new Context();
        context.setVariable("verificationCode", code);

        String content = templateEngine.process("verify", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


        helper.setFrom("ccnhan1288@gmail.com");
        helper.setTo(to);
        helper.setText(content, true);
        helper.setSubject("Xác Nhận Email Đăng Ký");

        mailSender.send(message);
    }
    @Override
    public void sendForgotPasswordEmail(String email, String newPassword) throws MessagingException {
        Context context = new Context();
        context.setVariable("newPassword", newPassword);

        String content = templateEngine.process("forget", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


        helper.setFrom("roadlineboooking@gmail.com");
        helper.setTo(email);
        helper.setText(content, true);
        helper.setSubject("Xác Nhận Email Quên Mật khẩu");

        mailSender.send(message);
    }

    @Override
    public void sendBookingDetailsEmail(Integer maDonDatVe) throws MessagingException {

        DonDatVe donDatVeCanGui = donDatVeRepository.findById(maDonDatVe).get();
        Context context = new Context();
        context.setVariable("donDatVe", donDatVeCanGui);
        int a = 8;

        // Xử lý template email
        String content = templateEngine.process("booking", context);

        // Tạo và cấu hình email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setFrom("cnhan1288@gmail.com");
        helper.setTo(donDatVeCanGui.getEmail());
        helper.setText(content, true);
        helper.setSubject("Xác nhận đặt vé thành công");

        // Gửi email
        mailSender.send(message);
    }

    @Override
    public void sendSimpleCancelBookingEmail(Integer maDonDatVe) throws MessagingException {
        DonDatVe donDatVeCanGui = donDatVeRepository.findById(maDonDatVe).get();

        // Nội dung email đơn giản
        String content = String.format(
                "<html><body>" +
                        "<h3>Thông báo hủy vé</h3>" +
                        "<p>Chúng tôi đã hủy đơn đặt vé <strong>%d</strong> của bạn!</p>" +
                        "<p>Nếu bạn đã thanh toán, chúng tôi sẽ hoàn tiền cho bạn!</p>" +
                        "<br><p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>" +
                        "</body></html>",
                maDonDatVe
        );

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setFrom("cnhan1288@gmail.com");
        helper.setTo(donDatVeCanGui.getEmail());
        helper.setText(content, true);
        helper.setSubject("Thông báo hủy vé từ Sao Việt");

        // Gửi email
        mailSender.send(message);
    }


}
