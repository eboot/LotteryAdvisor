/**
 * Software: LotteryAdvisor
 * Module: table model class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 16.8.2016
 *
 */
package oh3ebf.lottery.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import oh3ebf.lottery.dao.DAOFactory;
import oh3ebf.lottery.dao.LotteryNumbers;
import oh3ebf.lottery.dao.LotteryNumbersDAO;
import oh3ebf.lottery.exceptions.DAOException;
import org.apache.log4j.Logger;

public class LotteryResultTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private static Logger logger;
    private List<LotteryNumbers> drawNumbers;
    private LotteryNumbersDAO lotteryDao;
    private static final String columnNames[] = {
        "Veikkaus ID", "Game name", "Brand name", "Close time",
        "Draw time", "Primary results", "Secondary results",
        "Played","Id"};
    private String play;
    private List<String> gameNames;

    public LotteryResultTableModel() {
        // get new logger instance
        logger = Logger.getLogger(LotteryResultTableModel.class);

        lotteryDao = DAOFactory.getInstance().getLotteryNumbersDAO();

        try {
            gameNames = lotteryDao.listGameNames();
            drawNumbers = lotteryDao.find(gameNames.get(0));
            fireTableDataChanged();
        } catch (DAOException ex) {
            logger.error(ex.getMessage());

            // initialize empty lists
            gameNames = new ArrayList<>();
            drawNumbers = new ArrayList<>();
        }
        
    }

    /**
     * Function returns column names
     *
     * @param col
     * @return
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Function returns current row count
     *
     * @return
     */
    @Override
    public int getRowCount() {
        return drawNumbers.size();
    }

    /**
     * Function returns current column count
     *
     * @return
     */
    @Override
    public int getColumnCount() {
        return (columnNames.length);
    }

    /**
     * Function returns cell editable value
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * Function return cell current value
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LotteryNumbers row = drawNumbers.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return row.getVeikkausId();
            case 1:
                return row.getGameName();
            case 2:
                return row.getBrandName();
            case 3:
                if (row.getCloseTime() != null) {
                    return row.getCloseTime();
                } else {
                    return "";
                }
            case 4:
                return row.getDrawTime();
            case 5:
                return row.getPrimaryResults();
            case 6:
                return row.getSecondaryResults();
            case 7:
                return row.isPlayed() ? "Yes" : "No";
            case 8:
                return row.getId();
        }

        return null;
    }

    /**
     * Function gets current play
     *
     * @return the play
     */
    public String getPlay() {
        return play;
    }

    /**
     * Function sets play and updates table
     *
     * @param play the play to set
     */
    public void setPlay(String play) {
        this.play = play;

        try {
            drawNumbers = lotteryDao.find(play);
            fireTableDataChanged();
        } catch (DAOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Function return game names
     *
     * @return
     */
    public List<String> getGameNames() {
        return gameNames;
    }

    /**
     * Function return LotteryNumbers at row
     *
     * @param row
     * @return
     */
    public LotteryNumbers getLotteryNumbers(int row) {
        return drawNumbers.get(row);
    }
}
