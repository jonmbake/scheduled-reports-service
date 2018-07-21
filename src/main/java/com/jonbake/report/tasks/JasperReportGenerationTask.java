package com.jonbake.report.tasks;

import com.jonbake.report.core.ReportDatasource;
import com.jonbake.report.core.ScheduledReport;
import com.jonbake.report.exception.ReportRunningException;
import com.jonbake.report.stores.FileStore;
import com.jonbake.report.transports.ReportTransport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Jasper report generation task.
 */
public final class JasperReportGenerationTask extends ReportGenerationTask {
    private static final Logger LOG = Logger.getLogger(JasperReportGenerationTask.class.getName());
    private final ScheduledReport reportDefinition;
    private final ReportDatasource reportDatasource;
    private final FileStore reportsFileStore;
    private final ReportTransport reportTransport;

    /**
     * Construct the task.
     *
     * @param reportDefinition - report definition supplier
     * @param reportDatasource - report datasource
     * @param reportsFileStore - file store
     * @param reportTransport - report transport
     */
    public JasperReportGenerationTask (
            final ScheduledReport reportDefinition,
            final ReportDatasource reportDatasource,
            final FileStore reportsFileStore,
            final ReportTransport reportTransport
    ) {
        this.reportDefinition = reportDefinition;
        this.reportDatasource = reportDatasource;
        this.reportsFileStore = reportsFileStore;
        this.reportTransport = reportTransport;
    }

    /**
     * Run the report generation task.
     */
    @Override
    public void run () {
        try {
            InputStream reportFileStream = reportsFileStore.getFile(reportDefinition.getReport());
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFileStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(),
                    reportDatasource.getConnection());
            File generatedReportFile = File.createTempFile(UUID.randomUUID().toString(),
                    reportDefinition.getOutputFormat().toLowerCase());
            final OutputStream output = new FileOutputStream(generatedReportFile);
            switch (reportDefinition.getOutputFormat()) {
                case "PDF":
                    JasperExportManager.exportReportToPdfStream(jasperPrint, output);
                    break;
                case "XLS":
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));

                    SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                    configuration.setOnePagePerSheet(true);
                    configuration.setDetectCellType(true);
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                    break;
                default:
                    throw new ReportRunningException("Unable to export report for format "
                            + reportDefinition.getOutputFormat());
            }
            reportTransport.send(reportDefinition, generatedReportFile);
            generatedReportFile.delete();
        } catch (Exception e) {
            throw new ReportRunningException("Unable to fill jasper report. Reason: " + e.getMessage());
        }
    }
}
