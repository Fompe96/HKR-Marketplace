package Models;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {
    public void sendEmail(String userEmail, String emailSubject, String emailMessage) {
        String from = "HKRMarketplace@gmail.com";

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("HKRMarketplace@gmail.com", "hkrmarketplace2020!");

            }

        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(emailSubject);
            message.setText(emailMessage);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void sendSupportMessage(String userEmail, String subject, String userMessage) {
        String from = userEmail;

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("HKRMarketplace@gmail.com", "hkrmarketplace2020!");

            }

        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("HKRMarketplace@gmail.com"));
            message.setSubject(subject);
            message.setText("Customer email: " + userEmail + "\n\n" + "----------------  User Message  ----------------" +
             "\n" + userMessage);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void SaveToPdf(String Text) throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream("Receipt.pdf"));
        document.open();

        document.add(new Paragraph("Here is your receipt"));
        document.add(new Paragraph("Thanks for using HKR Marketplace!"));
        document.close();

    }



}
