package com.jonbake.report.util;

import com.jonbake.report.core.ScheduledReport;
import com.jonbake.report.stores.FileStoreFactory;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ScheduledReportsParserTest {
    @Test
    public void shouldParseScheduledReportsFile () {
        System.setProperty("REPORT_DIRECTORY", this.getClass().getClassLoader().getResource("reports/").getPath());
        List<ScheduledReport> parsed = new ScheduledReportsParser(FileStoreFactory.makeFileStore()).parse();
        assertEquals(1, parsed.size());
        ScheduledReport sr = parsed.get(0);
        assertEquals("TestJasper.jrxml", sr.getReport());
        assertEquals(1, sr.getRecipients().size());
        assertEquals("test@example.com", sr.getRecipients().get(0));
        assertEquals("PDF", sr.getOutputFormat());
        assertEquals("TestJasper.pdf", sr.getGeneratedReportName());
        assertEquals("Test Jasper Report", sr.getSubject());
        assertEquals("Every 7 days starting on Sunday", sr.getSendAt());
        assertEquals(7, sr.getSendPeriod());
        sr.getStartDelay();
    }
}