package com.taskassignment.emailalert;

import com.taskassignment.kafkatopicwriter.KafkaTopicWriter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class EmailAlert {
    public void SendMail(String timeStamp, String containerNumber) throws IOException {
        Logger logger = Logger.getLogger(
                KafkaTopicWriter.class.getName());
        FileHandler fh = new FileHandler("emailAlertLog.log");
        logger.addHandler(fh);

        final String username = "satishycr@gmail.com";
        final String password = "9036422435";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.post", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sryc123@gmail.com"));
            message.setSubject("[URGENT] - ACTION NEEDED !!\n");
            message.setText("We detected a dysfunctionality in the container number " + containerNumber + " and rapid fluctuation of temperature\nYou have been notified of the current problem, \n\n lastupdate on" + timeStamp + "\n\nEmergency helpline: xxxx-xxxx\n" +
                    "\n" +
                    "Resolve the issue at the earliest.\n" +
                    "\n" +
                    "Team excelship.\n" +
                    "\n" +
                    "\n" +
                    "\n");
            Transport.send(message);
            logger.info("email alert is Sent for Container ID "+containerNumber);

        } catch (AddressException e) {
            logger.warning("AddressException");
            e.printStackTrace();
        } catch (MessagingException e) {
            logger.warning("MessagingException");
            e.printStackTrace();
        }


    }

}
