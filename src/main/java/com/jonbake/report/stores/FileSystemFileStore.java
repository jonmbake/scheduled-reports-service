package com.jonbake.report.stores;

import com.jonbake.report.configuration.EnvironmentVariables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * File store for files stored on the file system.
 */
public final class FileSystemFileStore implements FileStore {
    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getFile (final String name) throws Exception {
        return new FileInputStream(new File(EnvironmentVariables.REPORT_DIRECTORY.getValue(), name));
    }
}
