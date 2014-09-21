/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscacao.util;

import com.siscacao.bean.SolicitudBean;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author Hanzo
 */
public class SendMailSSL {

    public SendMailSSL() {
    }
    private final Logger logger = Logger.getLogger(SolicitudBean.class);

    public void sendEmail(String toEmail, String subject, String msg) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("siscacao@gmail.com", "siscacao1234");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sicacao@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject(subject);
            // message.setText(msg); disable if it's no a rich text
            message.setContent(msg, "text/html");

            Transport.send(message);

            logger.info("Email send successfully");

        } catch (MessagingException e) {
            logger.error(e);
        }

    }
}
