package com.jonbake.report.core;

import java.util.List;

/**
 * Scheduled Report POJO.
 */
public final class ScheduledReport {
    private String reportName;
    private List<String> recipients;
    private String sendAt;

    public String getReportName () {
        return reportName;
    }

    public List<String> getRecipients () {
        return recipients;
    }

    public String getSendAt () {
        return sendAt;
    }
}
