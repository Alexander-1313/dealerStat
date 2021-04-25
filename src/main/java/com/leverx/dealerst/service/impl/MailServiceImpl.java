package com.leverx.dealerst.service.impl;

import com.leverx.dealerst.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(to);
        msg.setFrom("riverdale6613@gmail.com");
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);
    }
}
