/**
 * Software: LotteryAdvisor
 * Module: LotteryVisualPlay class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 12.3.2012
 */
package oh3ebf.lottery.components;

import java.awt.BorderLayout;
import oh3ebf.lottery.components.events.LotteryVisualPlayEvent;
import oh3ebf.lottery.interfaces.LotteryVisualPlayBoxSelectionIF;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import oh3ebf.lib.common.utilities.StringUtilities;
import oh3ebf.lottery.dao.LotteryPlayedDraws;
import oh3ebf.lottery.interfaces.LotteryVisualPlayDataIF;
import oh3ebf.lottery.interfaces.LotteyVisualPlayEventListenerIF;
import org.apache.log4j.Logger;

public class LotteryVisualPlay extends javax.swing.JPanel
        implements LotteryVisualPlayBoxSelectionIF, LotteyVisualPlayEventListenerIF {

    private static final long serialVersionUID = 7526472295622776147L;
    private static Logger logger;
    private LotteryVisualPlayBox[] primaryNumbersBox;
    private LotteryVisualPlayBox[] secondaryNumbersBox;
    private int primaryMaxSelection = 0;
    private int secondaryMaxSelection = 0;
    private LotteryVisualPlayDataIF lotteryData;
    LotteryPlayedDraws playedDraw;
    private int game;
    private int play;

    private List<Integer> primarySelections;
    private List<Integer> secondarySelections;
    public static final int JOKERI = 0;
    public static final int KENO = 1;
    public static final int LOTTO = 2;
    public static final int VIKING_LOTTO = 3;

    public LotteryVisualPlay(int game, int play, LotteryVisualPlayDataIF data) {

        // get logger instance for this class".
        logger = Logger.getLogger(LotteryVisualPlay.class);
        primaryNumbersBox = new LotteryVisualPlayBox[80];
        secondaryNumbersBox = new LotteryVisualPlayBox[80];

        int numbersCount = 0;
        lotteryData = data;
        primarySelections = new ArrayList<>();
        secondarySelections = new ArrayList<>();
        this.game = game;
        this.play = play;
        playedDraw = new LotteryPlayedDraws();

        Dimension size = new Dimension(1, 1);
        TitledBorder title;

        initComponents();

        ResourceBundle bundle = ResourceBundle.getBundle("oh3ebf/lottery/properties/Bundle");

        switch (game) {
            case LOTTO:
                // set game title
                title = BorderFactory.createTitledBorder(bundle.getString("Lottery.LottoBorderTitle")
                        + " " + StringUtilities.toAlphabetic(play));
                setBorder(title);

                setLayout(new java.awt.GridLayout(7, 6, 3, 3));
                numbersCount = 40;

                size = new Dimension(170, 170);
                primaryMaxSelection = 7;

                // add primary numbers to play box
                for (int i = 1; i <= numbersCount; i++) {
                    primaryNumbersBox[i - 1] = new LotteryVisualPlayBox(i, true, this);
                    this.add(primaryNumbersBox[i - 1]);
                }

                break;
            case KENO:
                // set game title
                title = BorderFactory.createTitledBorder(bundle.getString("Lottery.KenoBorderTitle")
                        + " " + StringUtilities.toAlphabetic(play));
                setBorder(title);

                setLayout(new java.awt.GridLayout(12, 6, 3, 3));
                numbersCount = 70;
                size = new Dimension(140, 280);
                primaryMaxSelection = 10;

                // add primary numbers to play box
                for (int i = 1; i <= numbersCount; i++) {
                    primaryNumbersBox[i - 1] = new LotteryVisualPlayBox(i, true, this);
                    this.add(primaryNumbersBox[i - 1]);
                }

                break;
            case VIKING_LOTTO:
                // add panel for primary numbers                               
                JPanel primaryPanel = new JPanel(new java.awt.GridLayout(6, 8, 3, 3));
                numbersCount = 48;
                primaryMaxSelection = 6;
                secondaryMaxSelection = 1;

                // set game title
                TitledBorder primaryTitle;
                primaryTitle = BorderFactory.createTitledBorder(bundle.getString("Lottery.VikingLottoBorderTitle")
                        + " " + StringUtilities.toAlphabetic(play));
                primaryPanel.setBorder(primaryTitle);

                // add primary numbers to play box
                for (int i = 1; i <= numbersCount; i++) {
                    primaryNumbersBox[i - 1] = new LotteryVisualPlayBox(i, true, this);
                    primaryPanel.add(primaryNumbersBox[i - 1]);
                }

                // add panel for secondary numbers
                JPanel secondaryPanel = new JPanel(new java.awt.GridLayout(1, 8, 3, 3));

                // set secondary numbers title
                TitledBorder secondaryTitle;
                secondaryTitle = BorderFactory.createTitledBorder(bundle.getString("Lottery.VikingLottoSecondaryNumbersTitle"));
                secondaryPanel.setBorder(secondaryTitle);

                // add secondary numbers to play box
                for (int i = 1; i <= 8; i++) {
                    secondaryNumbersBox[i - 1] = new LotteryVisualPlayBox(i, false, this);
                    secondaryPanel.add(secondaryNumbersBox[i - 1]);
                }

                // main panel setup
                setLayout(new BorderLayout());
                //size.setSize(170, 170);
                size = new Dimension(170, 170);
                add(primaryPanel, java.awt.BorderLayout.CENTER);
                add(secondaryPanel, java.awt.BorderLayout.SOUTH);
                break;
        }

        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        setSize(size);
    }

    /**
     * Function clear selections
     *
     */
    private void clearSelections() {
        primarySelections.clear();
        secondarySelections.clear();

        for (LotteryVisualPlayBox numbersBox : primaryNumbersBox) {
            if (numbersBox != null) {
                numbersBox.setSelection(false);
            }
        }
    }

    /**
     * Function is callback from selection primaryNumbersBox to update selection list
     *
     * @param state
     * @param selection
     * 
     */
    @Override
    public void setSelected(boolean state, boolean primaryNumber, Integer selection) {

        if (primaryNumber) {
            // update primary sections list
            if (state && !primarySelections.contains(selection)) {
                primarySelections.add(selection);
            } else {
                primarySelections.remove(selection);
            }
        } else {
            // update secondary sections list
            if (state && !secondarySelections.contains(selection)) {
                secondarySelections.add(selection);
            } else {
                secondarySelections.remove(selection);
            }
        }
    }

    /**
     * Function is callback from selection primaryNumbersBox
     *
     * @return
     * 
     */
    @Override
    public boolean isSelectionEnabled(boolean primaryNumber) {
        if (primaryNumber) {
            return primarySelections.size() < primaryMaxSelection;
        } else {
            return secondarySelections.size() < secondaryMaxSelection;
        }
    }

    /**
     * Function serves selection event for playedDraw play
     *
     * @param e
     * 
     */
    @Override
    public void setSelectionEvent(LotteryVisualPlayEvent e) {
        // clear old selections
        clearSelections();

        playedDraw = lotteryData.getTicketRow(play + 1);

        // check if play has data
        if (playedDraw != null) {
            // get rows
            //TODO muuta käyttämään LotteryPlayedRows objektia saadaan id talteen
            primarySelections = playedDraw.getPrimaryNumbersAsList();
            secondarySelections = playedDraw.getSecondaryNumbersAsList();
        }

        // any draws found
        if (primarySelections != null) {
            for (int index : primarySelections) {
                // update display
                primaryNumbersBox[index - 1].setSelection(true);
            }
        } else {
            logger.info("No played draws found: " + (play + 1));
        }
    }

    /**
     * Function serves event to show drawn numbers
     *
     * @param e event
     * 
     */
    @Override
    public void showDrawnNumbersEvent(LotteryVisualPlayEvent e) {
        // get drawn primary numbers
        List<Integer> numbers = lotteryData.getDrawnNumbers().getPrimaryResultsAsList();

        for (int index : numbers) {
            // copy state from check box to show or hide selection
            primaryNumbersBox[index - 1].setPrimaryResult(e.isState());
        }

        // get drawn secondary numbers
        numbers = lotteryData.getDrawnNumbers().getSecondaryResultsAsList();

        for (int index : numbers) {
            if (game == KENO || game == LOTTO || game == VIKING_LOTTO) {
                // copy state from check box to show or hide selection
                primaryNumbersBox[index - 1].setSecondaryResult(e.isState());
            }
        }
    }

    /**
     * Function serves event to show matching results at game
     *
     * @param e event
     * 
     */
    @Override
    public void matchingResultEvent(LotteryVisualPlayEvent e) {
        // TODO calculate result
        if(!primarySelections.isEmpty()) {
            
        }
    }

    /**
     * Function removes selected numbers
     *
     * @param e
     * 
     */
    @Override
    public void clearDataEvent(LotteryVisualPlayEvent e) {
        clearSelections();

        playedDraw = new LotteryPlayedDraws();
    }

    /**
     * Function handles play data persist event
     *
     * @param e
     * 
     */
    @Override
    public void PersistEvent(LotteryVisualPlayEvent e) {
        playedDraw.setPrimaryNumbers(primarySelections);
        playedDraw.setSecondaryNumbers(secondarySelections);
        playedDraw.setRow(play + 1);

        lotteryData.setTicketRow(playedDraw, true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 153, 153), null));
        setLayout(new java.awt.GridLayout(9, 6, 3, 3));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
