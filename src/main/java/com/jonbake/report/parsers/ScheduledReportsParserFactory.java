package com.jonbake.report.parsers;

import com.jonbake.report.configuration.EnvironmentVariables;
import com.jonbake.report.exception.ConfigurationException;

/**
 * Static factories for {@link ScheduledReportsParser}.
 */
public final class ScheduledReportsParserFactory {
    /**
     * Instantiable.
     */
    private ScheduledReportsParserFactory () {
    }
    /**
     * Factory that checks which {@link EnvironmentVariables} are set.
     *
     * @return {@link ScheduledReportsParser}
     */
    public static ScheduledReportsParser makeParser () {
        if (EnvironmentVariables.REPORT_DIRECTORY.isSet()) {
            return new FileSystemScheduledReportsParser();
        } else if (EnvironmentVariables.S3_REPORT_BUCKET.isSet()) {
            return new S3ScheduledReportsParser();
        } else {
            throw new ConfigurationException(
                    "Either REPORT_DIRECTORY or S3_REPORT_BUCKET environment variable must be set."
            );
        }
    }
}
