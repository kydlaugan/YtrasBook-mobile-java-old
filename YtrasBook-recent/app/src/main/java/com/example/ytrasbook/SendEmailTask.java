package com.example.ytrasbook;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailTask extends AsyncTask<Void, Void, Boolean> {

    private String mRecipient;
    private String mSubject;
    private String mMessage;

    public SendEmailTask(String recipient, String subject, String message) {
        mRecipient = recipient;
        mSubject = subject;
        mMessage = message;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // Crée une nouvelle instance de propriétés
            Properties props = new Properties();
            // Configure le serveur SMTP
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            // Crée une nouvelle session avec les propriétés définies ci-dessus
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("kydlaugan71@gmail.com", "cuynungwdvjusgft");
                        }
                    });

            // Crée un nouveau message électronique
            Message message = new MimeMessage(session);
            // Définit l'expéditeur
            message.setFrom(new InternetAddress("Entry"));
            // Définit le destinataire
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mRecipient));
            // Définit le sujet
            message.setSubject(mSubject);
            // Définit le message
            message.setText(mMessage);

            // Envoie le message
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

   /* @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(getApplicationContext()., "E-mail envoyé", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Erreur lors de l'envoi de l'e-mail", Toast.LENGTH_LONG).show();
        }
    } */
}
