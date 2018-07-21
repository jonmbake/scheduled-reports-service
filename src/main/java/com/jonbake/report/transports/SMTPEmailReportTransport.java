package com.jonbake.report.transports;

import com.jonbake.report.core.ScheduledReport;
import com.jonbake.report.exception.TransportException;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import java.io.File;
import java.util.Properties;

/**
 * SMTP transport.
 */
public final class SMTPEmailReportTransport extends EmailReportTransport {
    private final String host;
    private final String port;
    private final String username;
    private final String password;

    /**
     * Construct.
     *
     * @param host - host
     * @param port - port
     * @param username - username
     * @param password - password
     */
    public SMTPEmailReportTransport (final String host, final String port, final String username,
                                     final String password
    ) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send (final ScheduledReport reportDef, final File generatedReport) {
        Message message = getMessage(reportDef, generatedReport);
        try {
            Transport.send(message);
        } catch (Exception e) {
            throw new TransportException("Error while attempting to transport report via SMTP. " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Session getSession () {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        return Session.getInstance(
            props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication () {
                    return new PasswordAuthentication(username, password);
                }
            }
        );
    }
}
