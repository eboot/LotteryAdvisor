/**
 * Software: LotteryAdvisor
 * Module: SelectionInterface class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 15.9.2012
 */
package oh3ebf.lottery.interfaces;

public interface LotteryVisualPlayBoxSelectionIF {

    /**
     * Function sets state of selection
     *
     * @param state
     * @param primaryNumber
     * @param selection
     */
    public void setSelected(boolean state, boolean primaryNumber, Integer selection);

    /**
     * Function returns current state of selection enabling
     *
     * @param primaryNumber
     * @return
     */
    public boolean isSelectionEnabled(boolean primaryNumber);
}
