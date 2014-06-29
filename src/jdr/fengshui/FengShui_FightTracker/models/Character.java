package jdr.fengshui.FengShui_FightTracker.models;

import java.util.Random;

/**
 * Created by paulyves on 6/29/14.
 */
public class Character {
    protected String name;
    protected int segment;
    protected int mainVA;
    protected int secondaryVA;
    protected int speed;
    protected int mainRes;
    protected int secondaryRes;
    private Random RNG;

    public Character(String name, int mainVA, int secondaryVA, int speed) {
        this.name = name;
        this.mainVA = mainVA;
        this.secondaryVA = secondaryVA;
        this.speed = speed;
        this.RNG = new Random(System.currentTimeMillis());
        segment = rollInit();
    }

    /**
     * Just a classic diceroll
     * @return 1d6
     */
    public int rollDice(){
        return (RNG.nextInt(6)+1);
    }

    /**
     * Two explosive diceroll, one positive, one negative
     * @return the result
     */
    public int fengshuiRoll(){
        int pos = this.rollDice();
        while (pos % 6 == 0) {
            pos += this.rollDice();
        }
        int neg = this.rollDice();
        while (neg % 6 == 0) {
            neg += this.rollDice();
        }
        return pos - neg;
    }

    /**
     * A fengshui classical roll with the VA added
     * @param skill, and int 1 is mainVA, 2 or else is secondaryVA
     * @return
     */
    public int rollSkill(int skill){
        int diceRes = this.fengshuiRoll();
        if (skill==1){
            mainRes = mainVA + diceRes;
            return mainRes;
        } else {
            secondaryRes = secondaryVA + diceRes;
            return secondaryRes;
        }
    }

    public int rollInit(){
        segment = speed + rollDice();
        return segment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public int getMainVA() {
        return mainVA;
    }

    public void setMainVA(int mainVA) {
        this.mainVA = mainVA;
    }

    public int getSecondaryVA() {
        return secondaryVA;
    }

    public void setSecondaryVA(int secondaryVA) {
        this.secondaryVA = secondaryVA;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMainRes() {
        return mainRes;
    }

    public void setMainRes(int mainRes) {
        this.mainRes = mainRes;
    }

    public int getSecondaryRes() {
        return secondaryRes;
    }

    public void setSecondaryRes(int secondaryRes) {
        this.secondaryRes = secondaryRes;
    }
}
