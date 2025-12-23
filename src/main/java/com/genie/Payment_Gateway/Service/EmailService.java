package com.genie.Payment_Gateway.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,String name,String course,double amount){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(" ✅Payment Sucessfull -");
        message.setText("Hi " + name + ",\n\n" +
                "Thank you for order items in our website  " + ".\n\n" +
                "We look forward to seeing you again!\n\n" +
                "Please join telegram group link mentioned below\n\n"+
                "Best regards,\n" +
                "The [E-retailers] Team");
        mailSender.send(message);
    }
}
