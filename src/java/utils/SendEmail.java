/*
 * Licensed under the GPL License.  You may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package utils;

/**
 *
 * @author Camila.Riveron
 */
/**
 * INCLUIR LIBRERIAS MAIL Y ACTIVATION
**
 */
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.util.Properties;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;

public class SendEmail {

    private String user = null;
    private String pass = null;
    private String host = null;
    private String port = null;
    private String from = null;
    private boolean auth = false;
    private boolean ttls = false;

    public SendEmail(String host, String port, String user, String pass, String from, boolean auth, boolean ttls) {
        this.user = user;
        this.pass = pass;
        this.host = host;
        this.port = port;
        this.from = from;
        this.auth = auth;
        this.ttls = ttls;
    }

    public SendEmail() {
        ResourceBundle rb = ResourceBundle.getBundle("conf.conf");
//        this.user = rb.getString("SMTP_USUARIO");
//        this.pass = rb.getString("SMTP_PASSWORD");
        this.host = rb.getString("SMTP_SERVIDOR");
        this.port = rb.getString("SMTP_PUERTO");
        this.from = rb.getString("SMTP_DE");
        this.auth = new Boolean(rb.getString("SMTP_AUTH"));
        this.ttls = new Boolean(rb.getString("SMTP_TTLS"));
    }

    public static void main(String[] args) {
        try {
            SendEmail e = new SendEmail();
            List<String> tos = new LinkedList<>();
            tos.add("diego.gonzalez@correo.com.uy");
            e.sendMail("hola", "hoplaaaa", tos, null, null, null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clase encargada de enviar un email
     *
     * @param subject el subject del email
     * @param htmlBody el cuerpo en formato html teniendo en cuenta que las
     * imagenes estan formadas de la forma <img src=\"cid:image1\"> donde el
     * content id cid es unico para cada imagen
     * @param to la lista de direcciones separadas por coma
     * @param cc
     * @param cco
     * @param files el HashMap con clave content id de la imagen ejemplo
     * <imagen1> y el archivo asociado a la imagen
     * @throws java.lang.Exception
     */
    public void sendMail(String subject, String body, List<String> tos, List<String> ccs, List<String> ccos, List<Object[]> files, boolean debug) throws Exception {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
//        props.setProperty("mail.smtp.user", user);
//        props.setProperty("mail.smtp.password", pass);
        props.setProperty("mail.smtp.auth", Boolean.toString(auth));
        //props.setProperty("mail.smtp.ssl.trust", "smtpserver");
        props.setProperty("mail.smtp.starttls.enable", Boolean.toString(ttls));
        props.setProperty("mail.smtp.from", from);

        Session mailSession = Session.getInstance(props);
        mailSession.setDebug(debug);
        Transport transport = mailSession.getTransport("smtp");

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);

        //Para
        InternetAddress[] address = new InternetAddress[tos.size()];
        int i = 0;
        for (String to : tos) {
            InternetAddress iTo = new InternetAddress(to);
            address[i] = iTo;
            i++;
        }
        message.addRecipients(Message.RecipientType.TO, address);

        //Copia
        if (ccs != null) {
            InternetAddress[] addressCC = new InternetAddress[ccs.size()];
            i = 0;
            for (String cc : ccs) {
                InternetAddress iToCC = new InternetAddress(cc, cc);
                addressCC[i] = iToCC;
                i++;
            }
            if (addressCC.length > 0) {
                message.addRecipients(Message.RecipientType.CC, addressCC);
            }
        }

        //Copia oculta
        if (ccos != null) {
            InternetAddress[] addressCCO = new InternetAddress[ccos.size()];
            i = 0;
            for (String cco : ccos) {
                InternetAddress iToCCO = new InternetAddress(cco);
                addressCCO[i] = iToCCO;
                i++;
            }
            if (addressCCO.length > 0) {
                message.addRecipients(Message.RecipientType.BCC, addressCCO);
            }
        }

        MimeMultipart multipart = new MimeMultipart("related");

        //Cuerpo del mensaje
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html; charset=UTF-8");
        multipart.addBodyPart(messageBodyPart);

        if (files != null) {
            for (Object[] file : files) {
                messageBodyPart = new MimeBodyPart();
                DataSource fds = new FileDataSource((File) file[0]);
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", (String) file[1]);
                messageBodyPart.setFileName((String) file[1]);
                messageBodyPart.setHeader("Content-Type", (String) file[2]);
                multipart.addBodyPart(messageBodyPart);
            }
        }

        message.setContent(multipart);

        //transport.connect(user, pass);
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
