/*
 * Software: LotteryAdvisor
 * Module: LotteryVisualPlayEvent class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 30.4.2017
 *
 */
package oh3ebf.lottery.components.events;

import java.awt.AWTEvent;
import java.awt.Event;

public class LotteryVisualPlayEvent extends AWTEvent {
    private boolean state;

    public LotteryVisualPlayEvent() {
        super(new Object(), 0);
    }
    
    public LotteryVisualPlayEvent(Event event) {
        super(event);
        
    }
    
    public LotteryVisualPlayEvent(AWTEvent event) {
        super(new Object(), 0);
    }

    /**
     * @return the state
     */
    public boolean isState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(boolean state) {
        this.state = state;
    }
}
