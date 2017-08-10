/**
 * Software: LotteryAdvisor
 * Module: LotteryPlayedDrawsDAO class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 6.5.2016
 */
package oh3ebf.lottery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static oh3ebf.lottery.dao.DAOUtil.prepareStatement;
import oh3ebf.lottery.exceptions.DAOException;
import org.apache.log4j.Logger;

public class LotteryPlayedDrawsDAO {

    private static Logger logger;
    private HikariPool pool = null;
    private static final String SQL_FIND_BY_ID
            = "SELECT * FROM lottery_played_draws WHERE id = ?";
    private static final String SQL_FIND_BY_LOTTERY_NUMBERS_ID
            = "SELECT * FROM lottery_played_draws WHERE lottery_numbers_id = ?";
    private static final String SQL_LIST_ORDER_BY_ID
            = "SELECT * FROM lottery_numbers ORDER BY id";
    private static final String SQL_INSERT
            = "INSERT INTO lottery_played_draws (name, row, primary_numbers, secondary_numbers, lottery_numbers_id) "
            + "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE
            = "UPDATE lottery_played_draws SET name = ?, row = ?, primary_numbers = ?, secondary_numbers = ?"
            + "WHERE id = ?";
    private static final String SQL_DELETE
            = "DELETE FROM lottery_played_draws WHERE id = ?";

    public LotteryPlayedDrawsDAO(HikariPool pool) {
        // get new logger instance
        logger = Logger.getLogger(LotteryPlayedDrawsDAO.class);

        // save con pool
        this.pool = pool;
    }

    /**
     * Function return lottery draw by id
     *
     * @param id of device to query
     * @return LotteryNumbers if found
     * @throws DAOException
     */
    public List<LotteryPlayedDraws> find(long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    /**
     * Function return lottery draw by lottery numbers id
     *
     * @param id of device to query
     * @return LotteryNumbers if found
     * @throws DAOException
     */
    public List<LotteryPlayedDraws> findByLotteryNumbersId(long id) throws DAOException {
        return find(SQL_FIND_BY_LOTTERY_NUMBERS_ID, id);
    }
    
    /**
     * Function returns lottery draws by game name
     *
     * @param game
     * @return LotteryNumbers if found
     * @throws DAOException
     */
    /*public List<LotteryPlayedDraws> find(String game) throws DAOException {
     return find(SQL_FIND_BY_GAME_NAME, game);
     }*/
    
    /**
     * Function returns the lottery draw from the database matching the given
     * SQL query.
     *
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The device from the database
     * @throws DAOException If something fails at database level.
     */
    private List<LotteryPlayedDraws> find(String sql, Object... values) throws DAOException {
        List<LotteryPlayedDraws> lotteryDraws = new ArrayList<>();

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
     * Function returns all lottery draw results
     *
     * @return list of lotteryDraws
     * @throws DAOException
     */
    public List<LotteryPlayedDraws> list() throws DAOException {
        List<LotteryPlayedDraws> lotteryDraws = new ArrayList<>();

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
     * Function creates new lottery draw in database
     * @param lotteryDraw
     * @throws IllegalArgumentException
     * @throws DAOException 
     */
    public void create(LotteryPlayedDraws lotteryDraw) throws IllegalArgumentException, DAOException {
        if (lotteryDraw.getId() != null) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        Object[] values = {
            lotteryDraw.getName(),
            lotteryDraw.getRow(),
            lotteryDraw.getPrimaryNumbers(),
            lotteryDraw.getSecondaryNumbers(),
            lotteryDraw.getLotteryNumbersId()
        };

        try {
            Connection con = pool.getConnection();
            PreparedStatement stmt = prepareStatement(con, SQL_INSERT, true, values);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new DAOException("Creating alarm failed, no rows affected.");
            }
            
            // get result set
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                // set id to object
                lotteryDraw.setId(generatedKeys.getLong(1));
            } else {
                throw new DAOException("Creating alarm failed, no generated key obtained.");
            }
            
            // close pool con
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    
    /**
     * Function updates given lotteryDraw results object to database
     *
     * @param lotteryDraw
     * @throws DAOException
     */
    public void update(LotteryPlayedDraws lotteryDraw) throws DAOException {
        if (lotteryDraw.getId() == null) {
            throw new IllegalArgumentException("Lottery numbers are not created yet, the numbers ID is null.");
        }

        // prepare updated values
        Object[] values = {
            lotteryDraw.getName(),
            lotteryDraw.getRow(),
            lotteryDraw.getPrimaryNumbers(),
            lotteryDraw.getSecondaryNumbers(),
            lotteryDraw.getId()
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
     * Function deletes given lotteryDraw result from database
     *
     * @param lotteryDraw to delete
     * @throws DAOException
     */
    public void delete(LotteryPlayedDraws lotteryDraw) throws DAOException {
        Object[] values = {
            lotteryDraw.getId()
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
                lotteryDraw.setId(null);
            }

            // close pool connection
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Function map the current row of the given ResultSet to an LotteryPlayedDraws.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped
     * to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static LotteryPlayedDraws map(ResultSet resultSet) throws SQLException {
        LotteryPlayedDraws lottery = new LotteryPlayedDraws();
        lottery.setId(resultSet.getLong("id"));
        lottery.setName(resultSet.getString("name"));
        lottery.setRow(resultSet.getInt("row"));
        lottery.setPrimaryNumbers(resultSet.getString("primary_numbers"));
        lottery.setSecondaryNumbers(resultSet.getString("secondary_numbers"));
        lottery.setLotteryNumbersId(resultSet.getLong("lottery_numbers_id"));

        return lottery;
    }

}
