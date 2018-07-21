package com.jonbake.report.configuration;

/**
 * Environment Variables.
 */
public enum EnvironmentVariables {
    AWS_REGION("US_WEST_2"),
    DB_DRIVER("postgresql"),
    DB_URL("localhost"),
    DB_USERNAME(null),
    DB_PASSWORD(null),
    DB_PORT(null),
    DB_NAME(null),
    REPORT_DIRECTORY(null),
    S3_REPORT_BUCKET(null),
    SES_USERNAME(null),
    SES_PASSWORD(null),
    SMTP_HOST(null),
    SMTP_USERNAME(null),
    SMTP_PASSWORD(null),
    SMTP_PORT("587"),
    ADMIN_ADDRESS(null);

    private final String defaultValue;

    /**
     * Construct.
     *
     * @param defaultValue - value to use if not set
     */
    EnvironmentVariables (final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Get a config value. Checks both System and Environment properties.
     *
     * @return configuration value
     */
    public String getValue () {
        String value = System.getProperty(name());
        if (value == null) {
            value = System.getenv(name());
        }
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public boolean isSet () {
        return getValue() != null;
    }
}
