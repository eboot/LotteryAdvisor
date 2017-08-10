/*
 * Software: LotteryAdvisor
 * Module: LotteryVisualPlayEventListner interface class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 30.4.2017
 *
 */
package oh3ebf.lottery.interfaces;

import java.util.EventListener;
import oh3ebf.lottery.components.events.LotteryVisualPlayEvent;


public interface LotteyVisualPlayEventListenerIF extends EventListener {
    public void setSelectionEvent(LotteryVisualPlayEvent e);
    public void showDrawnNumbersEvent(LotteryVisualPlayEvent e);
    public void matchingResultEvent(LotteryVisualPlayEvent e);
    public void clearDataEvent(LotteryVisualPlayEvent e);
    public void PersistEvent(LotteryVisualPlayEvent e);
}
