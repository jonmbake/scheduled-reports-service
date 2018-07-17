package com.jonbake.report.core;

import com.jonbake.report.exception.ConfigurationException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.TemporalAdjusters.next;

/**
 * Scheduled Report POJO.
 */
public final class ScheduledReport {
    private static final String SEND_AT_REGEX = "Every (\\d+) days starting on (.+)";
    private static final Pattern SEND_AT_PATTERN = Pattern.compile(SEND_AT_REGEX);
    private String report;
    private List<String> recipients;
    private String sendAt;

    public String getReport () {
        return report;
    }

    public List<String> getRecipients () {
        return recipients;
    }

    public String getSendAt () {
        return sendAt;
    }

    /**
     * Get start delay.
     *
     * @return start delay
     */
    public long getStartDelay () {
        LocalDate now = LocalDate.now();
        LocalDate start;
        try {
            start = now.with(next(DayOfWeek.valueOf(parseSendAt(2).toUpperCase())));
        } catch (Exception e) {
            throw new ConfigurationException(String.format(
                    "Invalid day of week specified for sendAt for %s.", report
            ));
        }
        return start.toEpochDay() - now.toEpochDay();
    }

    /**
     * Get send period.
     *
     * @return send period
     */
    public long getSendPeriod () {
        Integer period;
        try {
            period = Integer.valueOf(parseSendAt(1));
        } catch (Exception e) {
            throw new ConfigurationException(String.format(
                    "Invalid number of days specified for sendAt for %s.", report
            ));
        }
        return TimeUnit.DAYS.toMillis(period);
    }

    /**
     * Parses groups out of {@link #sendAt}.
     *
     * @param group - group to parse out
     * @return parsed out group
     */
    private String parseSendAt (final int group) {
        Matcher matcher = SEND_AT_PATTERN.matcher(getSendAt().trim());
        if (!matcher.matches()) {
            throw new ConfigurationException(String.format(
                    "sendAt for %s does not conform to pattern %s.", report, SEND_AT_REGEX
            ));
        }
        return matcher.group(group);
    }
}
