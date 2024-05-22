package br.com.challenge.newssenderapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private MailSenderService mailService;


//    @Scheduled(fixedDelay = 60000) -- Para testes
    @Scheduled(cron = "${spring.task.scheduling.cron")
    public void scanAndSendNewsletter(){
        mailService.sendNewsletter();
    }
}
