package br.com.challenge.newssenderapi.service;

import br.com.challenge.newssenderapi.config.exception.ValidationException;
import br.com.challenge.newssenderapi.domain.Customer;
import br.com.challenge.newssenderapi.domain.News;
import br.com.challenge.newssenderapi.repository.CustomerRepository;
import br.com.challenge.newssenderapi.repository.NewsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class MailSenderService {

    private final JavaMailSender mailSender;

    private final String senderMail = "augustomailsenderapi@gmail.com";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NewsRepository newsRepository;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendNewsletter(){
        String messageHtmlContent = "";
        Integer actualMonth = 0, actualDay = 0, customerBDay = 0, customerMDay = 0;

        MimeMessage message = mailSender.createMimeMessage();

        List<News> newsToSend = newsRepository.findBySentFalse();
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty())
            throw new ValidationException("Não há cliente inscritos na newsletter");

        if (newsToSend.isEmpty())
            throw new ValidationException("Não há novas notícias para enviar");

        log.info(newsToSend.toString());
        log.info(customers.toString());

        for (Customer customer : customers) {

            if (customer.getBirthdate() != null) {
                customerBDay = customer.getBirthdate().getDayOfMonth();
                customerMDay = customer.getBirthdate().getMonthValue();
                actualDay = LocalDate.now().getDayOfMonth();
                actualMonth = LocalDate.now().getMonthValue();
            }

            try {
                message.setFrom(new InternetAddress(senderMail));
                message.setRecipients(MimeMessage.RecipientType.TO, customer.getEmail());
                message.setSubject("Notícias do Dia!", "UTF-8");

                if (customer.getBirthdate() != null && (customerBDay.equals(actualDay) && customerMDay.equals(actualMonth))) {
                    messageHtmlContent= "<h3> Bom dia, " + customer.getName() +"! Feliz Aniversário!</h3>" +
                            "<p>Segue as notícias de hoje.</p>";
                } else {
                    messageHtmlContent = "<h3> Bom dia, " + customer.getName() +"!</h3>" +
                            "<p>Segue as notícias de hoje.</p>";
                }

                for (News news : newsToSend) {
                    if (news.getLink() != null) {

                        messageHtmlContent = messageHtmlContent +
                                "<a href=\"" + news.getLink() + "\">" + news.getTitle() + "</a>" +
                                "<p>" + news.getDescription() + "</p>" +
                                "<br>";
                    } else {
                        messageHtmlContent = messageHtmlContent +
                                "<p>" + news.getTitle() + "</p>" +
                                "<p>" + news.getDescription() + "</p>"+
                                "<br>";
                    }
                    news.setSent(true);
                    newsRepository.save(news);

                    var updatedNews = newsRepository.findById(news.getId().longValue());

                    log.info("Current news state: ID= " + news.getId() + "isSent: " + news.isSent());
                    log.info("Dado retornado do banco: " + String.valueOf(updatedNews));
                }

                messageHtmlContent = messageHtmlContent +
                        "<p>...</p>" +
                        "<br>" +
                        "<p>Até a próxima.</p>";

                message.setContent(messageHtmlContent, "text/html; charset=utf-8");

                mailSender.send(message);
            } catch (MessagingException e) {
                throw new ValidationException("Erro durante o processo de geração do email. Exceção: " + e);
            }
        }
    }
}
