package com.jonbake.report.parsers;

import com.jonbake.report.core.ScheduledReport;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class FileSystemScheduledReportsParserTest {
    @Test
    public void shouldParseScheduledReportsFile () {
        System.setProperty("REPORT_DIRECTORY", this.getClass().getClassLoader().getResource("reports/").getPath());
        List<ScheduledReport> parsed = new FileSystemScheduledReportsParser().parse();
        assertEquals(1, parsed.size());
        ScheduledReport sr = parsed.get(0);
        assertEquals("TestJasper", sr.getReportName());
        assertEquals("Every 7 days starting on Sunday", sr.getSendAt());
        assertEquals(1, sr.getRecipients().size());
        assertEquals("test@example.com", sr.getRecipients().get(0));
    }

}