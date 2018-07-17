package com.jonbake.report.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReportDatasourceTest {
    @Test
    public void shouldProduceValidJDBCConnectionString () {
        ReportDatasource reportDatasource = new ReportDatasource();
        assertEquals("jdbc:postgresql://localhost", reportDatasource.getJDBCConnectionString());
    }

}