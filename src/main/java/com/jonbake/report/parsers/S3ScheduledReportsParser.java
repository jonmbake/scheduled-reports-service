package com.jonbake.report.parsers;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.jonbake.report.configuration.EnvironmentVariables;

import java.io.InputStream;

/**
 * Parses scheduled reports from AWS S3 bucket.
 */
public final class S3ScheduledReportsParser extends JsonScheduledReportsParser {

    @Override
    protected InputStream getScheduledReportsFileStream () {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(EnvironmentVariables.AWS_REGION.getValue())
                .withCredentials(new ProfileCredentialsProvider())
                .build();
        return s3Client.getObject(new GetObjectRequest(EnvironmentVariables.S3_REPORT_BUCKET.getValue(),
                SCHEDULE_FILE_NAME)).getObjectContent();
    }
}
