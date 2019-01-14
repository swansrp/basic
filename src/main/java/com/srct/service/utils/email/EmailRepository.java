/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: SpringBootCommon
 * @Package: com.srct.service.utils.email 
 * @author: ruopeng.sha   
 * @date: 2018-08-22 13:35
 */
package com.srct.service.utils.email;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: EmailRepository
 * @Description: TODO
 */

public class EmailRepository {
    private String topic;
    private Object body;
    @NotNull(message = "Must Specific primary recipients")
    private List<String> recipients;
    private List<String> cc;
    private List<String> bcc;
    private List<String> attachmentPath;
    private List<EmailAttachment> attachment;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = Arrays.asList(recipients);
    }

    public void setRecipients(String recipients) {
        this.recipients = Arrays.asList(new String[] { recipients });
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public void setCc(String[] cc) {
        this.cc = Arrays.asList(cc);
    }

    public void setCc(String cc) {
        this.cc = Arrays.asList(new String[] { cc });
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = Arrays.asList(bcc);
    }

    public void setBcc(String bcc) {
        this.bcc = Arrays.asList(new String[] { bcc });
    }

    public List<String> getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(List<String> attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public void setAttachmentPath(String[] attachmentPath) {
        this.attachmentPath = Arrays.asList(attachmentPath);
    }

    public void setAttachmentPath(String attchamentPath) {
        this.attachmentPath = Arrays.asList(new String[] { attchamentPath });
    }

    public List<EmailAttachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<EmailAttachment> attachment) {
        this.attachment = attachment;
    }

    public void setAttachment(EmailAttachment[] attachment) {
        this.attachment = Arrays.asList(attachment);
    }

    public void setAttachment(EmailAttachment attachment) {
        this.attachment = Arrays.asList(new EmailAttachment[] { attachment });
    }

}
