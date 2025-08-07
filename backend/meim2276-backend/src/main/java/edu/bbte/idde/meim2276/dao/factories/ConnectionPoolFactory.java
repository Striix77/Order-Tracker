package edu.bbte.idde.meim2276.dao.factories;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.meim2276.config.AppConfig;
import edu.bbte.idde.meim2276.config.ConfigLoader;

public class ConnectionPoolFactory {
    private static final HikariDataSource hikariDataSource;
    private static final AppConfig appConfig;

    static {
        hikariDataSource = new HikariDataSource();
        appConfig = ConfigLoader.loadConfig();
        hikariDataSource.setJdbcUrl(appConfig.getJdbcUrl());
        hikariDataSource.setUsername(appConfig.getJdbcUsername());
        hikariDataSource.setPassword(appConfig.getJdbcPassword());
        hikariDataSource.setDriverClassName(appConfig.getJdbcDriver());
        hikariDataSource.setMaximumPoolSize(appConfig.getConnectionPoolSize());

    }

    public static HikariDataSource getDataSource() {
        return hikariDataSource;
    }
}
