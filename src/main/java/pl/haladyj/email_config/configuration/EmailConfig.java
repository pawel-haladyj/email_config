package pl.haladyj.email_config.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.haladyj.email_config.constants.Constants;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.socketFactory.class}")
    private String socket;

    @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
    private String ssl;

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        sender.setProtocol("smtp");
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(userName);
        sender.setPassword(password);

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.socketFactory.class", socket);
        mailProps.put("mail.smtp.ssl.trust", ssl);

        sender.setJavaMailProperties(mailProps);

        return sender;
    }

    public static void sendActivationEmail(JavaMailSenderImpl instance, String activationKey, String to) {
        String activationLink = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/auth/activate/" + activationKey)
                .toUriString();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(Constants.ACTIVATION_MAIL_SENDER);
        mailMessage.setTo(to);
        mailMessage.setSubject(Constants.ACTIVATION_MAIL_SUBJECT);
        mailMessage.setText(Constants.ACTIVATION_MAIL_CONTENT + activationLink);
        instance.send(mailMessage);
    }
}
