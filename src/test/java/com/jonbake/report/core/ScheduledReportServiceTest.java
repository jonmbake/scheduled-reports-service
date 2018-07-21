package com.jonbake.report.core;

import org.junit.Test;

import java.util.concurrent.ScheduledFuture;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ScheduledReportServiceTest {
    @Test
    public void shouldScheduleReports () {
        System.setProperty("REPORT_DIRECTORY", this.getClass().getClassLoader().getResource("reports/").getPath());
        System.setProperty("SES_USERNAME", "tester");
        System.setProperty("SES_PASSWORD", "tester");
        ScheduledReportService scheduledReportService = new ScheduledReportService();
        assertEquals(1, scheduledReportService.getScheduledFutures().size());
        ScheduledFuture<?> scheduledFuture = scheduledReportService.getScheduledFutures().get(0);
        assertFalse(scheduledFuture.isDone());
    }
}