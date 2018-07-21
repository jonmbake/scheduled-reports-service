package com.jonbake.report.stores;

import com.jonbake.report.configuration.EnvironmentVariables;
import com.jonbake.report.exception.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * File store for files stored on the file system.
 */
public final class FileSystemFileStore implements FileStore {
    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getFile (final String name) {
        try {
            return new FileInputStream(new File(EnvironmentVariables.REPORT_DIRECTORY.getValue(), name));
        } catch (FileNotFoundException e) {
            throw new ConfigurationException("File does not exist: " + name);
        }
    }
}
