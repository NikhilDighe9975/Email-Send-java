package com.jnotification.jnotifi.cation.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface EmailService {
    String sendMail(MultipartFile[] files, String to, String[] cc, String subject, String body);
    String sendMailWithTemplate(String to, String[] cc, String subject, String templateName, Map<String, Object> templateVariables);
}
