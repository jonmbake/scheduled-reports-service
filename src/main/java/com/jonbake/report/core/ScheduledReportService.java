package com.jonbake.report.core;

import com.jonbake.report.parsers.ScheduledReportsParserFactory;

import java.util.List;

/**
 * Service for running scheduled reports.
 */
public class ScheduledReportService {
    private List<ScheduledReport> scheduledReports;

    /**
     * Construct service.
     */
    public ScheduledReportService () {
    }

    /**
     * Initialize service.
     */
    public final void init () {
        this.scheduledReports = ScheduledReportsParserFactory.makeParser().parse();
    }
}
