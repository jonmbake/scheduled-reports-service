package com.jonbake.report.tasks;

import com.jonbake.report.core.ReportDatasource;
import com.jonbake.report.exception.ReportRunningException;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Jasper report generation task.
 */
public final class JasperReportGenerationTask extends ReportGenerationTask {
    private Supplier<InputStream> reportDefinition;
    private ReportDatasource reportDatasource;

    /**
     * Construct the task.
     *
     * @param reportDefinition - report definition supplier
     * @param reportDatasource - report datasource
     */
    public JasperReportGenerationTask (final Supplier<InputStream> reportDefinition,
                                       final ReportDatasource reportDatasource
    ) {
        this.reportDefinition = reportDefinition;
        this.reportDatasource = reportDatasource;
    }

    /**
     * Run the report generation task.
     */
    @Override
    public void run () {
        try {
            final JasperReport jasperReport = JasperCompileManager.compileReport(reportDefinition.get());
            JasperFillManager.fillReport(jasperReport, Collections.emptyMap(), reportDatasource.getConnection());
        } catch (Exception e) {
            throw new ReportRunningException("Unable to fill jasper report.");
        }
    }
}
