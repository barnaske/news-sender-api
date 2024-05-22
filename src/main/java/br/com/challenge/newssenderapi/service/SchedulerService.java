package br.com.challenge.newssenderapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private MailSenderService mailService;

    @Scheduled(fixedDelay = 60000)
    public void scanAndSendNewsletter(){
        mailService.sendNewsletter();
    }
}
