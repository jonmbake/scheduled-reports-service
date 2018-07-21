package com.jonbake.report.transports;

import com.jonbake.report.core.ScheduledReport;

import java.io.File;

/**
 * Transport for generated reports.
 */
public interface ReportTransport {
    /**
     * Send the report.
     *
     * @param reportDef - report definition
     * @param generatedReport - generated report
     */
    void send (ScheduledReport reportDef, File generatedReport);
}
