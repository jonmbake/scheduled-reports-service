package com.jonbake.report.stores;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;

import com.jonbake.report.configuration.EnvironmentVariables;

import java.io.InputStream;

/**
 * File store for files stored within S3.
 */
public final class S3FileStore implements FileStore {
    private AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(EnvironmentVariables.AWS_REGION.getValue())
            .withCredentials(new ProfileCredentialsProvider())
            .build();

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getFile (final String name) throws Exception {
        return s3Client.getObject(new GetObjectRequest(EnvironmentVariables.S3_REPORT_BUCKET.getValue(), name))
                .getObjectContent();
    }
}
