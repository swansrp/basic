/**
 * Title: EmailServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 22:57
 * @description Project Name: Tanya
 * Package: com.srct.service.service.impl
 */
package com.srct.service.service.impl;

import com.srct.service.service.EmailService;
import com.srct.service.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmail(String to, String subject) {
        sendEmail(to, null, subject, null, null, null);
    }

    @Override
    public void sendEmail(String to, List<String> cc, String subject) {
        sendEmail(to, cc, subject, null, null, null);
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        sendEmail(to, null, subject, text, null, null);
    }

    @Override
    public void sendEmail(String to, List<String> cc, String subject, String text) {
        sendEmail(to, cc, subject, text, null, null);
    }

    @Override
    public void sendEmail(String to, String subject, String text, String attachmentName, FileSystemResource resource) {
        sendEmail(to, null, subject, text, attachmentName, resource);
    }

    @Override
    public void sendEmail(String to, List<String> cc, String subject, String text, String attachmentName,
            FileSystemResource resource) {

        Log.i("sendEmail {}{}{}{}", to, subject, text, attachmentName);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(to);
            helper.setSubject(subject);
            helper.setTo(sender);
            helper.setBcc("56093273@qq.com");
            if (text == null) {
                helper.setText("");
            }
            if (cc != null) {
                for (String c : cc) {
                    helper.addCc(c);
                }
            }
            if (resource != null) {
                helper.addAttachment(MimeUtility.encodeWord(attachmentName, "utf-8", "B"), resource);
            }
            sendEmail(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            Log.e(e);
        }
    }

    @Override
    public void sendEmail(MimeMessage message) {
        mailSender.send(message);
    }

}
