package com.jonbake.report.transports;

import com.jonbake.report.configuration.EnvironmentVariables;
import com.jonbake.report.exception.ConfigurationException;

/**
 * Static factories for {@link ReportTransport}s.
 */
public final class ReportTransportFactory {
    /**
     * Instantiable.
     */
    private ReportTransportFactory () {
    }

    /**
     * Make a {@link ReportTransport} based on which {@link EnvironmentVariables} are set.
     *
     * @return Report Transport
     */
    public static ReportTransport makeReportTransport () {
        String host;
        final String port = EnvironmentVariables.SMTP_PORT.getValue();
        String username;
        String password;
        if (EnvironmentVariables.SES_USERNAME.isSet()) {
            host = String.format("email-smtp.%s.amazonaws.com",
                    EnvironmentVariables.AWS_REGION.getValue().toLowerCase().replace("_", "-"));
            username = EnvironmentVariables.SES_USERNAME.getValue();
            password = EnvironmentVariables.SES_PASSWORD.getValue();
        } else if (EnvironmentVariables.SMTP_HOST.isSet()) {
            host = EnvironmentVariables.SMTP_HOST.getValue();
            username = EnvironmentVariables.SMTP_USERNAME.getValue();
            password = EnvironmentVariables.SMTP_PASSWORD.getValue();
        } else {
            throw new ConfigurationException("Either SES or STMP configuration values must be set");
        }
        return new SMTPEmailReportTransport (host, port, username, password);
    }
}
