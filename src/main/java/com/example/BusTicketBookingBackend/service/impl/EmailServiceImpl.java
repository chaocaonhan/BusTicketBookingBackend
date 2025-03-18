package com.example.BusTicketBookingBackend.service.impl;

import com.example.BusTicketBookingBackend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


//    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
//        this.mailSender = mailSender;
//        this.templateEngine = templateEngine;
//    }

    @Override
    public void sendVerificationEmail(String to, String code) throws MessagingException {
        Context context = new Context();
        context.setVariable("verificationCode", code);

        String content = templateEngine.process("verify", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


        helper.setFrom("roadlineboooking@gmail.com");
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


}
