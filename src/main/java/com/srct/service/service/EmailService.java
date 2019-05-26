package com.srct.service.service;

import org.springframework.core.io.FileSystemResource;

import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Title: EmailService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 22:52
 * @description Project Name: Tanya
 * Package: com.srct.service.service
 */
public interface EmailService {
    void sendEmail(String to, String subject);

    void sendEmail(String to, List<String> cc, String subject);

    void sendEmail(String to, String subject, String text);

    void sendEmail(String to, List<String> cc, String subject, String text);

    void sendEmail(String to, String subject, String text, String attachmentName, FileSystemResource resource);

    void sendEmail(String to, List<String> cc, String subject, String text, String attachmentName,
            FileSystemResource resource);

    void sendEmail(MimeMessage message);
}
