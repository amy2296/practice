package com.Demo.Example.Service;

import com.Demo.Example.Record.Mailbody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private  final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendSimpleMessage(Mailbody mailbody)
    {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(mailbody.to());
        simpleMailMessage.setText(mailbody.text());
        simpleMailMessage.setFrom("Amyter2411@gmail.com");
        simpleMailMessage.setSubject(mailbody.subject());

javaMailSender.send(simpleMailMessage);

    }
}

