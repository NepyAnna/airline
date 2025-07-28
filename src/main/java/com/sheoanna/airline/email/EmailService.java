package com.sheoanna.airline.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendRegistrationEmail(String to, String username) {
        Context context = new Context();
        context.setVariable("username", username);
        String html = templateEngine.process("registration-email", context);
        sendHtmlEmail(to, "Registration successfully", html);
    }

    public void sendBookingConfirmation(String to, String username, String departure, String arrival, String date, int seats) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("departure", departure);
        context.setVariable("arrival", arrival);
        context.setVariable("date", date);
        context.setVariable("seats", seats);
        String html = templateEngine.process("booking-confirmation", context);
        sendHtmlEmail(to, "Booking confirmation", html);
    }

    private void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

