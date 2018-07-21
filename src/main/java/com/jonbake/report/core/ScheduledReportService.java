package com.jonbake.report.core;

import com.jonbake.report.exception.ConfigurationException;
import com.jonbake.report.stores.FileStore;
import com.jonbake.report.stores.FileStoreFactory;
import com.jonbake.report.tasks.JasperReportGenerationTask;
import com.jonbake.report.transports.ReportTransport;
import com.jonbake.report.transports.ReportTransportFactory;
import com.jonbake.report.util.ScheduledReportsParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Service for running scheduled reports.
 */
public final class ScheduledReportService {
    private final ReportDatasource reportDatasource;
    private ScheduledExecutorService scheduledReportsTimer;
    private List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

    /**
     * Construct service.
     */
    public ScheduledReportService () {
        reportDatasource = new ReportDatasource();
        // TODO - Support for specifying number of report threads
        scheduledReportsTimer = Executors.newSingleThreadScheduledExecutor();
        init();
    }

    /**
     * Initialize service.
     */
    public void init () {
        scheduledFutures.stream().forEach(sf -> sf.cancel(false));
        scheduledFutures.clear();
        FileStore reportsFileStore = FileStoreFactory.makeFileStore();
        List<ScheduledReport> scheduledReports = new ScheduledReportsParser(reportsFileStore).parse();
        ReportTransport reportTransport = ReportTransportFactory.makeReportTransport();
        scheduledReports.forEach(sr -> {
            // TODO - Create registry.
            if (sr.getReport().endsWith(".jrxml")) {
                scheduledFutures.add(scheduledReportsTimer.scheduleAtFixedRate(
                    new JasperReportGenerationTask(sr, reportDatasource, reportsFileStore, reportTransport),
                    sr.getStartDelay(),
                    sr.getSendPeriod(),
                    TimeUnit.DAYS
                ));
            } else {
                throw new ConfigurationException("Unknown report type for "  + sr.getReport());
            }
        });
    }

    /**
     * Get {@link #getScheduledFutures()}. Note: added for testability:(.
     *
     * @return scheduled futures
     */
    protected List<ScheduledFuture<?>> getScheduledFutures () {
        return scheduledFutures;
    }
}
