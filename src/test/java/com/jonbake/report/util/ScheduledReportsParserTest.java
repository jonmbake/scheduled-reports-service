package com.jonbake.report.util;

import com.jonbake.report.core.ScheduledReport;
import com.jonbake.report.stores.FileStoreFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ScheduledReportsParserTest {
    @Test
    public void shouldParseScheduledReportsFile () {
        System.setProperty("REPORT_DIRECTORY", this.getClass().getClassLoader().getResource("reports/").getPath());
        List<ScheduledReport> parsed = new ScheduledReportsParser(FileStoreFactory.makeFileStore()).parse();
        assertEquals(1, parsed.size());
        ScheduledReport sr = parsed.get(0);
        assertEquals("TestJasper.jrxml", sr.getReport());
        assertEquals("Every 7 days starting on Sunday", sr.getSendAt());
        assertEquals(1, sr.getRecipients().size());
        assertEquals("test@example.com", sr.getRecipients().get(0));
        sr.getStartDelay();
    }
}