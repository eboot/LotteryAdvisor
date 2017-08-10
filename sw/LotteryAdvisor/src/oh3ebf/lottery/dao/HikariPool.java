/**
 * Software: LotteryAdvisor
 * Module: Hikari connection pool class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 22.1.2015
 *
 */
package oh3ebf.lottery.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import oh3ebf.lottery.exceptions.DAOConfigurationException;

public class HikariPool {

    private static HikariPool instance = null;
    private HikariDataSource ds = null;

    static {
        try {
            instance = new HikariPool();
        } catch (Exception ex) {
            throw new DAOConfigurationException("failed to get pool instance", ex);
        }
    }

    /**
     * Function creates new HikariCP instance
     * 
     */
    private HikariPool() {
        HikariConfig config = new HikariConfig("Hikari.properties");
        /*config.setMaximumPoolSize(10);
         config.setJdbcUrl("jdbc:mysql://thorin:3306/datafeed");
         config.setUsername("datafeed");
         config.setPassword("redhat");
         config.addDataSourceProperty("cachePrepStmts", "true");
         config.addDataSourceProperty("prepStmtCacheSize", "250");
         config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
         config.addDataSourceProperty("useServerPrepStmts", "true");
         */

        ds = new HikariDataSource(config);
    }

    /**
     * Function return connection pool instance
     * 
     * @return HikariPool
     */
    public static HikariPool getInstance() {
        return instance;
    }

    /**
     * Function return new data source connection
     * 
     * @return Connection
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
