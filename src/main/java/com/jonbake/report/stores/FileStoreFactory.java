package com.jonbake.report.stores;

import com.jonbake.report.configuration.EnvironmentVariables;
import com.jonbake.report.exception.ConfigurationException;

/**
 * Static factories for {@link FileStore}.
 */
public final class FileStoreFactory {
    /**
     * Instantiable.
     */
    private FileStoreFactory () {
    }

    /**
     * Make a file store based on which {@link EnvironmentVariables} are set.
     *
     * @return File Store
     */
    public static FileStore makeFileStore () {
        if (EnvironmentVariables.REPORT_DIRECTORY.isSet()) {
            return new FileSystemFileStore();
        } else if (EnvironmentVariables.S3_REPORT_BUCKET.isSet()) {
            return new S3FileStore();
        } else {
            throw new ConfigurationException(
                    "Either REPORT_DIRECTORY or S3_REPORT_BUCKET environment variable must be set."
            );
        }
    }
}
