package com.jonbake.report.parsers;

import com.jonbake.report.core.ScheduledReport;

import java.util.List;

/**
 * Parses scheduled reports.
 */
public interface ScheduledReportsParser {
    /**
     * Parse.
     *
     * @return parsed out list of {@link ScheduledReport}s
     */
    List<ScheduledReport> parse ();
}
