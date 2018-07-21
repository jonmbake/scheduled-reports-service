package com.jonbake.report.transports;

import com.jonbake.report.configuration.EnvironmentVariables;
import com.jonbake.report.core.ScheduledReport;
import com.jonbake.report.exception.ConfigurationException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Email Report Transport.
 */
public abstract class EmailReportTransport implements ReportTransport {
    /**
     * Get the message to send.
     *
     * @param reportDef       - report def.
     * @param generatedReport - generated report
     * @return generated message
     */
    public final Message getMessage (final ScheduledReport reportDef, final File generatedReport) {
        try {
            Message message = new MimeMessage(getSession());

            message.setFrom(new InternetAddress(EnvironmentVariables.ADMIN_ADDRESS.getValue()));

            List<InternetAddress> recipients = reportDef.getRecipients().stream().map(r -> {
                try {
                    return Arrays.asList(InternetAddress.parse(r));
                } catch (AddressException e) {
                    throw new ConfigurationException("Invalid recipient email address: " + r);
                }
            }).flatMap(Collection::stream).collect(Collectors.toList());
            message.setRecipients(Message.RecipientType.TO, recipients.toArray(new InternetAddress[1]));

            message.setSubject(reportDef.getSubject());

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText("");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new DataSource() {
                @Override
                public InputStream getInputStream () throws IOException {
                    return new FileInputStream(generatedReport);
                }

                @Override
                public OutputStream getOutputStream () throws IOException {
                    throw new IOException();
                }

                @Override
                public String getContentType () {
                    return reportDef.getContentType();
                }

                @Override
                public String getName () {
                    return reportDef.getGeneratedReportName();
                }
            };
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(generatedReport.getName());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the mail session.
     *
     * @return mail session
     */
    protected abstract Session getSession ();
}
