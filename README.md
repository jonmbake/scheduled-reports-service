# Scheduled Reports Service

An AWS-friendly scheduled reports service. Packaged as an executable Java JAR.

## Features
- Support for sending reports via [AWS SES](https://aws.amazon.com/ses/) (via SMTP) or SMTP.
- Support storing report definitions in an [AWS S3](https://aws.amazon.com/s3/) bucket or on the File System.
- Defaults to using [Jasper Reports Library 6.6.0](http://community.jaspersoft.com/) for report generation, but a different library can be used by implementing `ReportGenerationTask`. 

## Configuration

Configuration values are specified through system properties or environment variables.

Name               | Default Value    | Notes/Description
------------------ | ---------------- | ---------------------------
AWS_REGION         | US_WEST_2        | AWS Region. Needs to be specified if using SES (for report transport) or S3 (for report definition storage).
ADMIN_ADDRESS      | null             | Email address to send error reports. Also used as 'From Adress' when transporting generated reports.
SES_USERNAME       | null             | SES Username. Alias to SMTP_USERNAME. If set, will use SES for report transport.
SES_PASSWORD       | null             | SES Password. Alias to SMTP_PASSWORD. If set, will use SES for report transport.
SMTP_HOST          | null             | SMTP Host. If set, will use SMTP for report transport.
SMTP_USERNAME      | null             | SMTP Username. If set, will use SMTP for report transport.
SMTP_PASSWORD      | null             | SMTP Password. If set, will use SMTP for report transport.
SMTP_PORT          | 25               | SMTP Port. If set, will use SMTP for report transport.
DB_DRIVER          | postgresql       | e.g. 'mysql'. Database driver to use for database connection to run reports against.
DB_URL             | localhost        | Database URL/Host to use for database connection to run reports against.
DB_USERNAME        | null             | Database username to use for database connection to run reports against. If null, will attempt to do unauthenticate connection.
DB_PASSWORD        | null             | Database password to use for database connection to run reports against. If null, will attempt to do unauthenticate connection.
DB_PORT            | null             | Database port to use for database connection to run reports against.
DB_NAME            | null             | Database name.
REPORT_DIRECTORY   | null             | Location where reports definition lives. Either REPORT_DIRECTORY or S3_REPORT_BUCKET must be set.
S3_REPORT_BUCKET   | null             | S3 bucket where reports definition lives. Either REPORT_DIRECTORY or S3_REPORT_BUCKET must be set.


## Report Schedule File Format

There must be a JSON file of the name _schedule.json_ with the following format in the report directory (or S3 bucket):

```json
[
  {
    "report": "TestJasper.jrxml",
    "recipients": ["test@example.com"],
    "outputFormat": "PDF",
    "subject": "Test Jasper Report",
    "sendAt": "Every 7 days starting on Sunday"
  }
]

``` 

# License

MIT
