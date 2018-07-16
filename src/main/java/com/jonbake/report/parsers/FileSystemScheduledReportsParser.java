package com.jonbake.report.parsers;

import com.jonbake.report.configuration.EnvironmentVariables;
import com.jonbake.report.exception.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Parses scheduled reports from the File System.
 */
public class FileSystemScheduledReportsParser extends JsonScheduledReportsParser {
    /**
     * {@inheritDoc}
     */
    @Override
    protected final InputStream getScheduledReportsFileStream () {
        try {
            return new FileInputStream(new File(EnvironmentVariables.REPORT_DIRECTORY.getValue(), SCHEDULE_FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new ConfigurationException("Unable to read JSON Scheduled Reports file");
        }
    }
}
