package com.jonbake.report.parsers;

import com.google.gson.Gson;
import com.jonbake.report.core.ScheduledReport;
import com.jonbake.report.exception.ConfigurationException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Parses scheduled reports from a JSON-formatted file.
 */
public abstract class JsonScheduledReportsParser implements ScheduledReportsParser {
    protected static final String SCHEDULE_FILE_NAME = "schedule.json";

    /**
     * {@inheritDoc}
     */
    public final List<ScheduledReport> parse () {
        Gson parser = new Gson();
        try {
            return Arrays.asList(parser.fromJson(new InputStreamReader(getScheduledReportsFileStream(), "UTF-8"),
                    ScheduledReport[].class));
        } catch (UnsupportedEncodingException e) {
            throw new ConfigurationException("Unable to read JSON Scheduled Reports file.");
        }
    }

    /**
     * Get the Scheduled Reports file stream.
     *
     * @return file stream
     */
    protected abstract InputStream getScheduledReportsFileStream ();
}
