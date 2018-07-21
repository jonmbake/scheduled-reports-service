package com.jonbake.report.transports;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ReportTransportFactoryTest {
    @Test
    public void shouldUseAwsRegionSesForHostName () {
        System.setProperty("SES_USERNAME", "tester");
        System.setProperty("SES_PASSWORD", "tester");
        ReportTransport reportTransport = ReportTransportFactory.makeReportTransport();
        if (!(reportTransport instanceof SMTPEmailReportTransport)) {
            fail();
        }
        assertEquals("email-smtp.us-west-2.amazonaws.com", ((SMTPEmailReportTransport)reportTransport).getSession()
                .getProperty("mail.smtp.host"));
    }

}