package com.jonbake.report.core;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class ScheduledReportServiceTest {
    @Test
    public void shouldSetTimer () {
        System.setProperty("REPORT_DIRECTORY", this.getClass().getClassLoader().getResource("reports/").getPath());
        ScheduledReportService scheduledReportService = new ScheduledReportService();
        assertNotNull(scheduledReportService.getScheduledReportsTimer());
    }

}