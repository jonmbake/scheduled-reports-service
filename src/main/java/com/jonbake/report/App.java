package com.jonbake.report;

import com.jonbake.report.core.ScheduledReportService;

/**
 * Entry point for application.
 *
 * @author jonmbake
 */
public final class App {
    /**
     * Non-instantiable class.
     */
    private App () {
    }

    /**
     * Main.
     *
     * @param args - args
     */
    public static void main (final String[] args) {
        new ScheduledReportService();
    }
}
