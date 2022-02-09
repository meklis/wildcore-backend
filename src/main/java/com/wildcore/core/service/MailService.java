package com.wildcore.core.service;

import com.wildcore.core.config.WildcoreConfiguration;
import com.wildcore.core.model.BetaRequest;
import com.wildcore.core.repository.BetaRequestRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final BetaRequestRepository repository;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final WildcoreConfiguration configuration;

    @Value("classpath:/static/logo.png")
    private Resource resourceFile;

    @Scheduled(fixedDelay = 5000)
    public void scheduleBetaRequests() throws MessagingException, UnsupportedEncodingException {
        List<BetaRequest> requests = repository.findAllBySentFalse();

        for (BetaRequest request : requests) {
            sendAdminMessages(request);
            sendUserMessage(request);
            request.setSent(true);
            request.setSentAt(new Date());
            repository.save(request);
            log.info("Request: {} is sent", request);
        }
    }

    private void sendAdminMessages(BetaRequest request) throws MessagingException, UnsupportedEncodingException {
        Map<String, Object> attributes = getThymeleafAttributesFromRequest(request);
        for (String emailAddress : configuration.getMail().getAdmin().getAddresses()) {
            sendMessageUsingThymeleafTemplate(emailAddress, "New beta request", configuration.getMail().getAdmin().getTemplate(), attributes);
        }
    }
    private void sendUserMessage(BetaRequest request) throws MessagingException, UnsupportedEncodingException {
        Map<String, Object> attributes = getThymeleafAttributesFromRequest(request);
        sendMessageUsingThymeleafTemplate(request.getEmail(), "New beta request", configuration.getMail().getUser().getTemplate(), attributes);
    }


    private Map<String, Object> getThymeleafAttributesFromRequest(BetaRequest request) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", request.getEmail());
        attributes.put("company", request.getCompany());
        attributes.put("created_at", request.getCreatedAt());
        attributes.put("name", request.getName());
        attributes.put("message", request.getMessage());
        return  attributes;
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(configuration.getMail().getFrom(), configuration.getMail().getFromName());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("logo.png", resourceFile);
        mailSender.send(message);
    }


    private void sendMessageUsingThymeleafTemplate(
            String to, String subject, String templateName, Map<String, Object> templateModel)
            throws MessagingException, UnsupportedEncodingException {
        Context thymeleafContext = new Context();

        thymeleafContext.setVariables(templateModel);
        String htmlBody = templateEngine.process(templateName, thymeleafContext);
        sendHtmlMessage(to, subject, htmlBody);
    }

}
