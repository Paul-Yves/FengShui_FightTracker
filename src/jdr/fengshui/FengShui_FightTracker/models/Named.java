package jdr.fengshui.FengShui_FightTracker.models;

/**
 * Created by paulyves on 6/29/14.
 */
public class Named extends Character {
    private int wounds;
    private int toughness;

    public Named(String name, int mainVA, int secondaryVA, int speed, int toughness) {
        super(name, mainVA, secondaryVA, speed);
        this.wounds = 0;
        this.toughness = toughness;
    }

    public int eatThat(int dmg){
        int eat = dmg - this.toughness;
        if (eat > 0){
            wounds += eat;
        }
        return wounds;
    }

    public int getWounds() {
        return wounds;
    }

    public void setWounds(int wounds) {
        this.wounds = wounds;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }
}
