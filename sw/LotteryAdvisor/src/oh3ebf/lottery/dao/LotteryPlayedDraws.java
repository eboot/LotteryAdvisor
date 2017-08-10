/**
 * Software: LotteryAdvisor
 * Module: LotteryPlayedDraws class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 17.4.2017
 *
 */package oh3ebf.lottery.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LotteryPlayedDraws {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private int row;
    private String primaryNumbers;
    private String secondaryNumbers;
    private Long lotteryNumbersId;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the primaryNumbers
     */
    public String getPrimaryNumbers() {
        return primaryNumbers;
    }

    /**
     *
     * @return the secondary numbers as list
     */
    public List<Integer> getPrimaryNumbersAsList() {
        return getNumbersAsList(this.primaryNumbers);
    }

    /**
     * @param primaryNumbers the primaryNumbers to set
     */
    public void setPrimaryNumbers(String primaryNumbers) {
        this.primaryNumbers = primaryNumbers;
    }

    /**
     * @param primaryNumbers the primaryNumbers to set
     */
    public void setPrimaryNumbers(List<Integer> primaryNumbers) {
        this.primaryNumbers = getNumbersAsString(primaryNumbers);
    }

    /**
     * @return the secondaryNumbers
     */
    public String getSecondaryNumbers() {
        return secondaryNumbers;
    }

    /**
     *
     * @return the secondary numbers as list
     */
    public List<Integer> getSecondaryNumbersAsList() {
        return getNumbersAsList(this.secondaryNumbers);
    }

    /**
     * @param secondaryNumbers the secondaryNumbers to set
     */
    public void setSecondaryNumbers(String secondaryNumbers) {
        this.secondaryNumbers = secondaryNumbers;
    }

    /**
     * @param secondaryNumbers the secondaryNumbers to set
     */
    public void setSecondaryNumbers(List<Integer> secondaryNumbers) {
        this.secondaryNumbers = getNumbersAsString(secondaryNumbers);
    }

    /**
     * @return the lotteryNumbersId
     */
    public Long getLotteryNumbersId() {
        return lotteryNumbersId;
    }

    /**
     * @param lotteryNumbersId the lotteryNumbersId to set
     */
    public void setLotteryNumbersId(Long lotteryNumbersId) {
        this.lotteryNumbersId = lotteryNumbersId;
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

    /**
     * Function converts integer list to comma separated string
     *
     * @param numbers
     * @return
     */
    private String getNumbersAsString(List<Integer> numbers) {
        StringBuilder result = new StringBuilder();

        Iterator it = numbers.iterator();

        // create string
        while (it.hasNext()) {
            result.append(it.next());
            // append comma only if there is next number 
            if (it.hasNext()) {
                result.append(",");
            }
        }

        return result.toString();
    }

}
