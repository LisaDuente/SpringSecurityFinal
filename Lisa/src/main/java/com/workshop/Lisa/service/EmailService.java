package com.workshop.Lisa.service;


import com.workshop.Lisa.Dto.SendEmailDto;
import com.workshop.Lisa.Entity.Contact;
import com.workshop.Lisa.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserService userService;
    private final ContactService contactService;

    public String sendEmail(String username, SendEmailDto dto) throws AddressException, MessagingException, IOException {

        User sender = this.userService.findUserByUsername(username);
        if (sender == null) {
            return "You need to logg in to send an email.";
        }

        User recipient = this.userService.findById(Long.parseLong(dto.getUserID()));
        if (recipient == null) {
            return "recipient could not be found!";
        }

        Contact contact1 = this.contactService.findContactForUserOneUserTwo(sender.getUserId(), recipient.getUserId());
        Contact contact2 = this.contactService.findContactForUserOneUserTwo(recipient.getUserId(), sender.getUserId());

        //check how we accept friend request, need to have two contacts sender/recipient/status and recipient/sender/status
        //LOOK AT MIRO!
        if(contact1 != null && contact2 == null){
            return "You must be friends to send emails to each other";
        }

        if(contact2 == null || contact1 == null){
            return "You must be friends to send emails to each other";
        }

        if (!contact1.getStatus().toString().equals("FRIENDS") || !contact2.getStatus().toString().equals("FRIENDS")) {
            return "You must be friends to send emails to each other";
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // logging into below email
                return new PasswordAuthentication("matchingAppMsgService@gmail.com", System.getenv("PASSWORD"));
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("NO_REPLY", false)); // from this email address

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.getContactInformation().getEmail())); // email address of recipient
        msg.setSubject("Message from Hobby Matching App");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        String contentText = "You received a message from: \n\n"
                + sender.getUsername()
                + "\n\nMessage:\n\n"
                + dto.getContent()
                + "\n\nPlease login to the Matching App to respond to:\n"
                + "\n" + sender.getUsername()
                + "\n"
                + "\n[Please do not reply to this email]";

        messageBodyPart.setContent(contentText, "text/plain"); // content of message

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        Transport.send(msg);
        return "message sent!";
    }
}
