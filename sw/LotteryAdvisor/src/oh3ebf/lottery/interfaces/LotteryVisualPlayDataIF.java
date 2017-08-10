/**
 * Software: LotteryAdvisor
 * Module: LotteryVisualPlayDataInterface class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 30.4.2017
 */package oh3ebf.lottery.interfaces;

import oh3ebf.lottery.dao.LotteryNumbers;
import oh3ebf.lottery.dao.LotteryPlayedDraws;

public interface LotteryVisualPlayDataIF {

    public LotteryPlayedDraws getTicketRow(int draw);

    public LotteryNumbers getDrawnNumbers();

    public void setTicketRow(LotteryPlayedDraws drawData, boolean persist);
    
    public void refresh();
}
