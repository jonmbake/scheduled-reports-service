package com.jonbake.report.util;

import com.google.gson.Gson;
import com.jonbake.report.core.ScheduledReport;
import com.jonbake.report.exception.ConfigurationException;
import com.jonbake.report.stores.FileStore;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Parses scheduled reports.
 */
public final class ScheduledReportsParser {
    private static final String SCHEDULE_FILE_NAME = "schedule.json";
    private final FileStore fileStore;

    /**
     * Construct with file store.
     *
     * @param fileStore - file store
     */
    public ScheduledReportsParser (final FileStore fileStore) {
        this.fileStore = fileStore;
    }

    /**
     * Parse.
     *
     * @return parsed out list of {@link ScheduledReport}s
     */
    public List<ScheduledReport> parse () {
        Gson parser = new Gson();
        try {
            return Arrays.asList(parser.fromJson(new InputStreamReader(fileStore.getFile(SCHEDULE_FILE_NAME), "UTF-8"),
                    ScheduledReport[].class));
        } catch (Exception e) {
            throw new ConfigurationException("Unable to read JSON Scheduled Reports file.");
        }
    }
}
