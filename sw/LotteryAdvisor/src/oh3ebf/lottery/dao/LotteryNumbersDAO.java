/**
 * Software: LotteryAdvisor
 * Module: LotteryNumbersDAO class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 10.4.2016
 */
package oh3ebf.lottery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static oh3ebf.lottery.dao.DAOUtil.prepareStatement;
import static oh3ebf.lottery.dao.DAOUtil.toSqlDate;
import oh3ebf.lottery.exceptions.DAOException;
import org.apache.log4j.Logger;

public class LotteryNumbersDAO {

    private static Logger logger;
    private HikariPool pool = null;
    private static final String SQL_FIND_BY_ID
            = "SELECT * FROM lottery_numbers WHERE id = ?";
    private static final String SQL_FIND_BY_GAME_NAME
            = "SELECT * FROM lottery_numbers WHERE game_name = ?";
    private static final String SQL_LIST_ORDER_BY_ID
            = "SELECT * FROM lottery_numbers ORDER BY id";
    private static final String SQL_LIST_GAME_NAMES
            = "SELECT DISTINCT game_name FROM lottery_numbers ORDER BY game_name";
    private static final String SQL_INSERT
            = "INSERT INTO lottery_numbers (brand_name, game_name, veikkaus_id, close_time, draw_time, primary_results, secondary_results, played) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE
            = "UPDATE lottery_numbers SET brand_name = ?, game_name = ?, veikkaus_id = ?, close_time = ?, draw_time = ?, "
            + "primary_results = ?, secondary_results = ?, played = ? "
            + "WHERE id = ?";
    private static final String SQL_DELETE
            = "DELETE FROM lottery_numbers WHERE id = ?";

    public LotteryNumbersDAO(HikariPool pool) {
        // get new logger instance
        logger = Logger.getLogger(LotteryNumbersDAO.class);

        // save con pool
        this.pool = pool;
    }

    /**
     * Function return lottery draw results by id
     *
     * @param id of device to query
     * @return LotteryNumbers if found
     * @throws DAOException
     */
    public List<LotteryNumbers> find(long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    /**
     * Function returns lottery draw results by game name
     *
     * @param game
     * @return LotteryNumbers if found
     * @throws DAOException
     */
    public List<LotteryNumbers> find(String game) throws DAOException {
        return find(SQL_FIND_BY_GAME_NAME, game);
    }

    /**
     * Function returns the lottery draw results from the database matching the given
     * SQL query.
     *
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The device from the database
     * @throws DAOException If something fails at database level.
     */
    private List<LotteryNumbers> find(String sql, Object... values) throws DAOException {
        List<LotteryNumbers> lottery = new ArrayList<>();

        try {
            Connection con = pool.getConnection();

            // check for valid database con
            if (con == null) {
                throw new DAOException("No connection to database server!");
            }

            PreparedStatement stmt = prepareStatement(con, sql, false, values);
            ResultSet rs = stmt.executeQuery();

            // map result to object
            while (rs.next()) {
                lottery.add(map(rs));
            }

            // close pool connection
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return lottery;
    }

    /**
     * Function returns all lottery draw results
     *
     * @return list of lotteryDraws
     * @throws DAOException
     */
    public List<LotteryNumbers> list() throws DAOException {
        List<LotteryNumbers> lotteryDraws = new ArrayList<>();

        try {
            Connection con = pool.getConnection();

            // check for valid database connection
            if (con == null) {
                throw new DAOException("No connection to database server!");
            }

            PreparedStatement stmt = con.prepareStatement(SQL_LIST_ORDER_BY_ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lotteryDraws.add(map(rs));
            }

            // close pool connection
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return lotteryDraws;
    }

    /**
     * Function returns lottery game names
     *
     * @return list of names
     * @throws DAOException
     */
    public List<String> listGameNames() throws DAOException {
        List<String> names = new ArrayList<>();

        try {
            Connection con = pool.getConnection();

            // check for valid database connection
            if (con == null) {
                throw new DAOException("No connection to database server!");
            }

            PreparedStatement stmt = con.prepareStatement(SQL_LIST_GAME_NAMES);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                names.add(rs.getString(1));
            }

            // close pool connection
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return names;
    }

    /**
     * Function updates given lottery draw results object to database
     *
     * @param lottery
     * @throws DAOException
     */
    public void update(LotteryNumbers lottery) throws DAOException {
        if (lottery.getId() == null) {
            throw new IllegalArgumentException("Lottery numbers are not created yet, the numbers ID is null.");
        }

        // prepare updated values
        Object[] values = {
            lottery.getBrandName(),
            lottery.getGameName(),
            lottery.getVeikkausId(),
            toSqlDate(lottery.getCloseTime()),
            toSqlDate(lottery.getDrawTime()),
            lottery.getPrimaryResults(),
            lottery.getSecondaryResults(),
            lottery.isPlayed(),
            lottery.getId()
        };

        try {
            Connection con = pool.getConnection();

            // check for valid database connection
            if (con == null) {
                throw new DAOException("No connection to database server!");
            }

            PreparedStatement stmt = prepareStatement(con, SQL_UPDATE, false, values);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating lottery numbers failed, no rows affected.");
            }

            // close pool connection
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Function deletes given lottery draw result from database
     *
     * @param lottery to delete
     * @throws DAOException
     */
    public void delete(LotteryNumbers lottery) throws DAOException {
        Object[] values = {
            lottery.getId()
        };

        try {
            Connection con = pool.getConnection();

            // check for valid database connection
            if (con == null) {
                throw new DAOException("No connection to database server!");
            }

            PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values);

            int affectedRows = stmt.executeUpdate();

            // check operation success
            if (affectedRows == 0) {
                throw new DAOException("Deleting lottery numbers failed, no rows affected.");
            } else {
                lottery.setId(null);
            }

            // close pool connection
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Function map the current row of the given ResultSet to an LotteryNumbers.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped
     * to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static LotteryNumbers map(ResultSet resultSet) throws SQLException {
        LotteryNumbers lottery = new LotteryNumbers();
        lottery.setId(resultSet.getLong("id"));
        lottery.setBrandName(resultSet.getString("brand_name"));
        lottery.setGameName(resultSet.getString("game_name"));
        lottery.setVeikkausId(resultSet.getInt("veikkaus_id"));
        lottery.setCloseTime(resultSet.getDate("close_time"));
        lottery.setDrawTime(resultSet.getDate("draw_time"));
        lottery.setPrimaryResults(resultSet.getString("primary_results"));
        lottery.setSecondaryResults(resultSet.getString("secondary_results"));
        lottery.setPlayed(resultSet.getBoolean("played"));
        
        return lottery;
    }
}
