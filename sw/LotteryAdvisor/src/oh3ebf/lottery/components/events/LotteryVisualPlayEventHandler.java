/**
 * Software: LotteryAdvisor
 * Module: LotteryVisualPlayEventHandler class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 30.4.2017
 */
package oh3ebf.lottery.components.events;

import javax.swing.event.EventListenerList;
import oh3ebf.lottery.interfaces.LotteyVisualPlayEventListenerIF;

public class LotteryVisualPlayEventHandler {

    private final EventListenerList LotteryVisialPlayListeners;

    public LotteryVisualPlayEventHandler() {
        LotteryVisialPlayListeners = new EventListenerList();
    }

    /**
     * Function adds new listener
     *
     * @param listener
     */
    public void addLotteryVisialPlayListener(LotteyVisualPlayEventListenerIF listener) {
        LotteryVisialPlayListeners.add(LotteyVisualPlayEventListenerIF.class, listener);
    }

    /**
     * Function removes listener
     *
     * @param listener
     */
    public void removeLotteryVisialPlayListener(LotteyVisualPlayEventListenerIF listener) {
        LotteryVisialPlayListeners.remove(LotteyVisualPlayEventListenerIF.class, listener);
    }

    /**
     * Function fire play selection event
     *
     * @param event
     */
    public void fireLotteryVisialPlaySelectionEvent(LotteryVisualPlayEvent event) {
        Object[] listeners = LotteryVisialPlayListeners.getListenerList();

        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;

        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == LotteyVisualPlayEventListenerIF.class) {
                // pass the event to the listeners event dispatch method
                ((LotteyVisualPlayEventListenerIF) listeners[i + 1]).setSelectionEvent(event);
            }
        }
    }

    /**
     * Function fire show drawn numbers event
     *
     * @param event
     */
    public void fireLotteryVisialPlayDrawnNumbersEvent(LotteryVisualPlayEvent event) {
        Object[] listeners = LotteryVisialPlayListeners.getListenerList();

        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;

        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == LotteyVisualPlayEventListenerIF.class) {
                // pass the event to the listeners event dispatch method
                ((LotteyVisualPlayEventListenerIF) listeners[i + 1]).showDrawnNumbersEvent(event);
            }
        }
    }

    /**
     * Function fire show matching result event
     *
     * @param event
     */
    public void fireLotteryVisialPlayMatchingResultEvent(LotteryVisualPlayEvent event) {
        Object[] listeners = LotteryVisialPlayListeners.getListenerList();

        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;

        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == LotteyVisualPlayEventListenerIF.class) {
                // pass the event to the listeners event dispatch method
                ((LotteyVisualPlayEventListenerIF) listeners[i + 1]).matchingResultEvent(event);
            }
        }
    }

    /**
     * Function fire show matching result event
     *
     * @param event
     */
    public void fireLotteryVisialPlayClearDataEvent(LotteryVisualPlayEvent event) {
        Object[] listeners = LotteryVisialPlayListeners.getListenerList();

        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;

        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == LotteyVisualPlayEventListenerIF.class) {
                // pass the event to the listeners event dispatch method
                ((LotteyVisualPlayEventListenerIF) listeners[i + 1]).clearDataEvent(event);
            }
        }
    }
    
    /**
     * Function fire show matching result event
     *
     * @param event
     */
    public void fireLotteryVisialPlayPersistEvent(LotteryVisualPlayEvent event) {
        Object[] listeners = LotteryVisialPlayListeners.getListenerList();

        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;

        for (int i = 0; i < numListeners; i += 2) {
            if (listeners[i] == LotteyVisualPlayEventListenerIF.class) {
                // pass the event to the listeners event dispatch method
                ((LotteyVisualPlayEventListenerIF) listeners[i + 1]).PersistEvent(event);
            }
        }
    }
}
