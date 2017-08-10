/**
 * Software: LotteryAdvisor
 * Module: LotteryVisualPlayModel class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 17.4.2017
 */
package oh3ebf.lottery.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import oh3ebf.lottery.dao.DAOFactory;
import oh3ebf.lottery.dao.LotteryNumbers;
import oh3ebf.lottery.dao.LotteryNumbersDAO;
import oh3ebf.lottery.dao.LotteryPlayedDraws;
import oh3ebf.lottery.dao.LotteryPlayedDrawsDAO;
import oh3ebf.lottery.exceptions.DAOException;
import oh3ebf.lottery.interfaces.LotteryVisualPlayDataIF;
import org.apache.log4j.Logger;

public class LotteryVisualPlayModel extends DefaultListModel<String> implements LotteryVisualPlayDataIF {
    
    private static final long serialVersionUID = 7526472294622771147L;
    private static Logger logger;
    private final LotteryPlayedDrawsDAO lotteryPlayedDrawsDao;
    private final LotteryNumbersDAO lotteryNumbersDao;
    private List<LotteryPlayedDraws> lotteryPlayedDraws;
    private List<LotteryNumbers> lotteryNumbers;
    private String ticketName;
    private long lotteryNumbersId;
    
    public LotteryVisualPlayModel() {
        // get logger instance for this class".
        logger = Logger.getLogger(LotteryVisualPlayModel.class);
        
        lotteryNumbersDao = DAOFactory.getInstance().getLotteryNumbersDAO();
        lotteryPlayedDrawsDao = DAOFactory.getInstance().getLotteryPlayedDraws();
    }

    /**
     * Function sets new draw
     *
     * @param id
     */
    public void setPlayedDraw(long id) {
        lotteryNumbersId = id;
        
        // get played draws
        updateDrawsList();
        
        try {
            // get drawn numbers
            lotteryNumbers = lotteryNumbersDao.find(lotteryNumbersId);
        } catch (DAOException ex) {
            logger.error(ex.getMessage());
        }               
    }
    
    public void setTicketName(String selection) {
        ticketName = selection;
    }           

    /**
     * Function return played draw numbers for single row in ticket
     *
     * @param row
     * @return
     */
    @Override
    public LotteryPlayedDraws getTicketRow(int row) {
        // check game ticket selection
        if (ticketName != null && !ticketName.isEmpty()) {

            // find selected game draw
            for (LotteryPlayedDraws lotteryPlayedDraw : lotteryPlayedDraws) {
                
                if (lotteryPlayedDraw.getName().equalsIgnoreCase(ticketName)
                        && lotteryPlayedDraw.getRow() == row) {
                    return lotteryPlayedDraw;
                }
            }
        }
        
        return null;
    }

    /**
     * Function sets and stores played draw numbers to game
     *
     * @param drawData
     * @param persist
     */
    @Override
    public void setTicketRow(LotteryPlayedDraws drawData, boolean persist) {
        logger.debug(drawData.getRow() + " " + drawData.getPrimaryNumbers());
        
        if (persist) {
            if (drawData.getId() == null) {
                lotteryPlayedDraws.clear();

                // common ticket name to all rows
                drawData.setName(ticketName);
                drawData.setLotteryNumbersId(lotteryNumbersId);
                
                lotteryPlayedDrawsDao.create(drawData);

                // persist new row
                lotteryPlayedDraws.add(drawData);

                // mark as played
                lotteryNumbers.get(0).setPlayed(true);
                lotteryNumbersDao.update(lotteryNumbers.get(0));
                
            } else {
                // persist updated data
                lotteryPlayedDrawsDao.update(drawData);
            }            
        } else {
            logger.debug("Played draws not written to database. persist = " + persist);
        }
    }

    /**
     * Function return drawn numbers
     *
     * @return
     */
    @Override
    public LotteryNumbers getDrawnNumbers() {
        if (lotteryNumbers != null) {
            return lotteryNumbers.get(0);
        }
        
        return null;
    }

    /**
     * Function populates selection list
     *
     */
    private void updateDrawsList() {
        this.clear();
        
        try {
            // get played draws for this round
            lotteryPlayedDraws = lotteryPlayedDrawsDao.findByLotteryNumbersId(lotteryNumbersId);            
        } catch (DAOException ex) {
            logger.error(ex.getMessage());
        }
        
        // create new list containing only played draw ticket names
        for (LotteryPlayedDraws lotteryPlayedDraw : lotteryPlayedDraws) {
            String name = lotteryPlayedDraw.getName();
            
            if(!this.contains(name)) {
                this.addElement(name);
            }            
        }
    }

    /**
     * Function updates JList model
     * 
     */
    @Override
    public void refresh() {
        
        // build played games list
        updateDrawsList();
        
        // update display
        fireContentsChanged(this, 0, this.getSize());
    }
}
