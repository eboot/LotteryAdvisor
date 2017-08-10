/*
 * Software: LotteryAdvisor
 *
 * Module: DAOFactory class
 *
 * Version: 0.1
 *
 * Licence: GPL2
 *
 * Owner: Kim Kristo
 *
 * Date creation : 5.1.2016
 *
 */
package oh3ebf.lottery.dao;

import org.apache.log4j.Logger;

public class DAOFactory {

    private static DAOFactory instance;
    private static Logger logger;
    HikariPool pool;

    private DAOFactory() {
        // get new logger instance
        logger = Logger.getLogger(DAOFactory.class);

        // create database instances
        pool = HikariPool.getInstance();

    }

    /**
     * Function returns DAO factory object
     *
     * @return factory
     */
    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }

        return instance;
    }

    /**
     * Function returns lottery base object
     *
     * @return database access object
     */
    public LotteryNumbersDAO getLotteryNumbersDAO() {
        return new LotteryNumbersDAO(pool);
    }    
    
    /**
     * Function returns lottery played draws object
     *
     * @return database access object
     */
    public LotteryPlayedDrawsDAO getLotteryPlayedDraws() {
        return new LotteryPlayedDrawsDAO(pool);
    }    
}
