package com.dbd.nanal.service;

import java.util.Random;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender emailSender;

    public static final String code = createKey();
    
    private MimeMessage createMessage(String receiver)  throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, receiver);
        message.setSubject("[Nanal] 나날 이메일 인증 코드");

        String msg = "";
        msg += "<div>";
        msg += "<strong> 나날 이메일 인증코드 안내 드립니다. </strong>";
        msg += "<br>";
        msg += "<p> 아래 코드를 복사해 입력해주세요 <p>";
        msg += "<br>";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg += "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msg += "<div style='font-size:130%'>";
        msg += "CODE : <strong>";
        msg += code+"</strong><div><br/>";
        msg += "<br>";
        msg += "<p> 감사합니다 <p>";
        msg += "</div>";

        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress("nanal@nanal.com", "Nanal"));

        return message;
    }

    // 인증코드 생성
    private static String createKey() {
        StringBuilder key = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0:
                    key.append((char) (random.nextInt(26) +97));       // a~z
                    continue;
                case 1:
                    key.append((char) (random.nextInt(26) +65));        // A~Z
                    continue;
                case 2:
                    key.append((random.nextInt(10)));       // 0~9\
            }
        }
        return  key.toString();
    }

    public String sendSimpleMessage(String receiver) throws Exception {
        MimeMessage message = createMessage(receiver);
        emailSender.send(message);
        return code;
    }

}
