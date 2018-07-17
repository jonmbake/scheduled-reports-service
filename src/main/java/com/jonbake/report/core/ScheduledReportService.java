package com.jonbake.report.core;

import com.jonbake.report.exception.ConfigurationException;
import com.jonbake.report.exception.ReportRunningException;
import com.jonbake.report.stores.FileStore;
import com.jonbake.report.stores.FileStoreFactory;
import com.jonbake.report.tasks.JasperReportGenerationTask;
import com.jonbake.report.util.ScheduledReportsParser;

import java.util.List;
import java.util.Timer;

/**
 * Service for running scheduled reports.
 */
public final class ScheduledReportService {
    private final ReportDatasource reportDatasource;
    private Timer scheduledReportsTimer;

    /**
     * Construct service.
     */
    public ScheduledReportService () {
        reportDatasource = new ReportDatasource();
        init();
    }

    /**
     * Initialize service.
     */
    public void init () {
        if (scheduledReportsTimer != null) {
            scheduledReportsTimer.purge();
            scheduledReportsTimer.cancel();
        }
        scheduledReportsTimer = new Timer();
        FileStore reportsFileStore = FileStoreFactory.makeFileStore();
        List<ScheduledReport> scheduledReports = new ScheduledReportsParser(reportsFileStore).parse();
        scheduledReports.forEach(sr -> {
            if (sr.getReport().endsWith(".jrxml")) {
                scheduledReportsTimer.scheduleAtFixedRate(new JasperReportGenerationTask(() -> {
                    try {
                        return reportsFileStore.getFile(sr.getReport());
                    } catch (Exception e) {
                        throw new ReportRunningException("Unable to get report definition for " + sr.getReport());
                    }
                }, reportDatasource), sr.getStartDelay(), sr.getSendPeriod());
            } else {
                throw new ConfigurationException("Unknown report type for "  + sr.getReport());
            }
        });
    }

    /**
     * Get {@link #scheduledReportsTimer}. Note: added for testability.
     *
     * @return timer
     */
    protected Timer getScheduledReportsTimer () {
        return scheduledReportsTimer;
    }
}
