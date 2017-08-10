/**
 * Software: LotteryAdvisor
 * Module: LotteryNumbersDAO class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 10.4.2016
 *
 */
package oh3ebf.lottery.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LotteryNumbers {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String brandName;
    private String gameName;
    private int veikkausId;
    private Date closeTime;
    private Date drawTime;
    private String primaryResults;
    private String secondaryResults;
    private boolean played;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return the gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @param gameName the gameName to set
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * @return the veikkausId
     */
    public int getVeikkausId() {
        return veikkausId;
    }

    /**
     * @param veikkausId the veikkausId to set
     */
    public void setVeikkausId(int veikkausId) {
        this.veikkausId = veikkausId;
    }

    /**
     * @return the closeTime
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * @param closeTime the closeTime to set
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * @return the drawTime
     */
    public Date getDrawTime() {
        return drawTime;
    }

    /**
     * @param drawTime the drawTime to set
     */
    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }

    /**
     * @return the primaryResults
     */
    public String getPrimaryResults() {
        return primaryResults;
    }

    /**
     *
     * @return the secondary numbers as list
     */
    public List<Integer> getPrimaryResultsAsList() {
        return getNumbersAsList(this.primaryResults);
    }
    
    /**
     * @param primaryResults the primaryResults to set
     */
    public void setPrimaryResults(String primaryResults) {
        this.primaryResults = primaryResults;
    }

    /**
     * @return the secondaryResults
     */
    public String getSecondaryResults() {
        return secondaryResults;
    }

    /**
     *
     * @return the secondary numbers as list
     */
    public List<Integer> getSecondaryResultsAsList() {
        return getNumbersAsList(this.secondaryResults);
    }
    
    /**
     * @param secondaryResults the secondaryResults to set
     */
    public void setSecondaryResults(String secondaryResults) {
        this.secondaryResults = secondaryResults;
    }

    /**
     * @return the played
     */
    public boolean isPlayed() {
        return played;
    }

    /**
     * @param played the played to set
     */
    public void setPlayed(boolean played) {
        this.played = played;
    }
    
    /**
     * Function converts string number list to List of integers
     *
     * @param numbers
     * @return
     */
    private List<Integer> getNumbersAsList(String numbers) throws NumberFormatException {
        List<Integer> result = new ArrayList<>();

        // valid parameter
        if (numbers != null && !numbers.isEmpty()) {

            String a[] = numbers.split(",");

            // create list of numbers
            for (String n : a) {
                result.add(Integer.parseInt(n));
            }
        }
        
        return result;
    }    
}
