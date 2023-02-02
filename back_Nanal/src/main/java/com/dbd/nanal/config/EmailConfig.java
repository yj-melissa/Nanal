package com.dbd.nanal.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:/application-email.properties")
public class EmailConfig {
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.required}")
    private boolean isStarttls_required;
    @Value("${mail.smtp.starttls.enable}")
    private boolean isStarttls_enable;
    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;
    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.socketFactory.port}")
    private int socketPort;
    @Value("${mail.smtp.socketFactory.class}")
    private String socketClass;

    @Value("${AdminMail.id}")
    private String id;
    @Value("${AdminMail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.socketFactory.port", socketPort);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", isStarttls_enable);
        properties.put("mail.smtp.starttls.required", isStarttls_required);
        properties.put("mail.smtp.socketFactory.fallback", fallback);
        properties.put("mail.smtp.socketFactory.class", socketClass);
        return properties;
    }

}
