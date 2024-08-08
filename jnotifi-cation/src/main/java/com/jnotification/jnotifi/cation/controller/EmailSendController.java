package com.jnotification.jnotifi.cation.controller;

import com.jnotification.jnotifi.cation.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/mail")
public class EmailSendController {

    private final EmailService emailService;

    public EmailSendController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @RequestParam String to,
            @RequestParam(required = false) String[] cc,
            @RequestParam String subject,
            @RequestParam String body) {

        if (to == null || to.isEmpty()) {
            return ResponseEntity.badRequest().body("Recipient email address is required");
        }

        if (subject == null || subject.isEmpty()) {
            return ResponseEntity.badRequest().body("Subject is required");
        }
        if (body == null || body.isEmpty()) {
            return ResponseEntity.badRequest().body("Body is required");
        }

        try {
            emailService.sendMail(files, to, cc, subject, body);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/send-template")
    public ResponseEntity<String> sendMailWithTemplate(
            @RequestParam String to,
            @RequestParam(required = false) String[] cc,
            @RequestParam String subject,
            @RequestParam String templateName,
            @RequestParam Map<String, Object> templateVariables) {

        if (to == null || to.isEmpty()) {
            return ResponseEntity.badRequest().body("Recipient email address is required");
        }

        if (subject == null || subject.isEmpty()) {
            return ResponseEntity.badRequest().body("Subject is required");
        }
        if (templateName == null || templateName.isEmpty()) {
            return ResponseEntity.badRequest().body("Template name is required");
        }

        try {
            emailService.sendMailWithTemplate(to, cc, subject, templateName, templateVariables);
            return ResponseEntity.ok("Email sent with template successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email with template: " + e.getMessage());
        }
    }
}
