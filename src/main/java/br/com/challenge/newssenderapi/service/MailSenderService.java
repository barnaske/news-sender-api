package br.com.challenge.newssenderapi.service;

import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.domain.News;
import br.com.challenge.newssenderapi.repository.CustomerRepository;
import br.com.challenge.newssenderapi.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NewsRepository newsRepository;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendNewsletter(){

        SimpleMailMessage message = new SimpleMailMessage();

        List<News> newsToSend = newsRepository.findBySentFalse();
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty())
            throw new ValidationException("Não há cliente inscritos na newsletter");

        if (newsToSend.isEmpty())
            throw new ValidationException("Não há novas notícias para enviar");

        log.info(newsToSend.toString());
        log.info(customers.toString());

        customers.stream()
                .forEach( (customer) -> {
                    message.setTo(customer.getEmail());
                    message.setFrom("augustomailsenderapi@gmail.com");
                    message.setSubject("Notícias do Dia");
                    message.setText("Teste");

                    mailSender.send(message);
                });
    }
}
