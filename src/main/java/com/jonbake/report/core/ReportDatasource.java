package com.jonbake.report.core;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.jonbake.report.configuration.EnvironmentVariables;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Poolable data source for report service.
 *
 * @author jonmbake
 */
public class ReportDatasource {
    private final DataSource dataSource;
    /**
     * Construct with configuration.
     *
     */
    public ReportDatasource () {
        ConnectionFactory cf = new DriverManagerConnectionFactory(
            getJDBCConnectionString(),
            EnvironmentVariables.DB_USERNAME.getValue(),
            EnvironmentVariables.DB_PASSWORD.getValue()
        );
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(cf, new GenericObjectPool(),
                        null, null, true, false);
        GenericObjectPool connectionPool = new GenericObjectPool(poolableConnectionFactory);
        dataSource = new PoolingDataSource(connectionPool);
    }
    /**
     * Get the database connection to run the report.
     *
     * @return database connection
     * @throws SQLException -
     */
    public final Connection getConnection () throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * JDBC Connection String.  For example: jdbc:mysql://localhost:3306/mydatabase.
     *
     * @return JDBC Connection String
     */
    public final String getJDBCConnectionString () {
        StringBuilder connectionStr  = new StringBuilder(String.format("jdbc:%s://%s",
                EnvironmentVariables.DB_DRIVER.getValue(), EnvironmentVariables.DB_URL.getValue()));
        if (EnvironmentVariables.DB_PORT.isSet()) {
            connectionStr.append(":").append(EnvironmentVariables.DB_PORT.getValue());
        }
        if (EnvironmentVariables.DB_NAME.isSet()) {
            connectionStr.append("/").append(EnvironmentVariables.DB_NAME.getValue());

        }
        return connectionStr.toString();
    }
}
