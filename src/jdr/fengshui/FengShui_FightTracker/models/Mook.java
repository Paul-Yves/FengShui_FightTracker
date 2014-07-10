package jdr.fengshui.FengShui_FightTracker.models;

/**
 * Created by paulyves on 6/29/14.
 */
public class Mook extends Character {
    private int number;

    public Mook(String name, int mainVA, int secondaryVA, int speed, int number) {
        super(name, mainVA, secondaryVA, speed);
        this.number = number;
    }

    /**
     * Mooks roll skill differently since they are a bunch
     * @param skill, and int 1 is mainVA, 2 or else is secondaryVA
     * @return the result
     */
    public int rollSkill(int skill){
        int diceRes = this.fengshuiRoll();
        segment -= 3;
        if (segment < 0){
            segment = 0;
        }
        if (skill==1){
            mainRes = mainVA + diceRes + number - 1;
            return mainRes;
        } else {
            secondaryRes = secondaryVA + diceRes + number - 1;
            return secondaryRes;
        }
    }

    public int reduce(int deads){
        number = number - deads;
        if (number < 0) {
            number = 0;
        }
        return number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return name+":"+"VAs("+mainVA+";"+secondaryVA+"), speed:"+speed+", number:"+number;
    }
}
