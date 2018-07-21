package com.jonbake.report.stores;

import java.io.InputStream;

/**
 * A file store.
 */
public interface FileStore {
    /**
     * Get a file from the store by name.
     *
     * @param name - name of file
     * @return file input stream
     * @throws Exception - if file can not be retrieved for any reason
     */
    InputStream getFile (String name);
}
