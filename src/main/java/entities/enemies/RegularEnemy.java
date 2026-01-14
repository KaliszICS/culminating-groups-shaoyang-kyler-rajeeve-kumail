package entities.enemies;

import java.io.Serializable;

/**
 * Represents a regular enemy of the game, also includes elite regular enemy.
 * A regular enemy includes a boolean indicating whether they are an elite,
 * a boolean indicating whether they can call for reinforcements (more regular enemies)
 * <p>
 * This class is inherited from {@link Enemy}, which means that they will share attributes
 * </p>
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 */
public class RegularEnemy extends Enemy implements Serializable {
    //added serializable, so now people can save characters to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private boolean isElite;
    private boolean canCallReinforcements;

    /**
     * Instantiates a new Regular enemy with default attributes (no parameters provided)
     * in default, this enemy is not an elite and cannot call reinforcements
     */
    public RegularEnemy() {
        super();
        this.isElite = false;
        this.canCallReinforcements = false;
    }

    /**
     * Instantiates a new Regular enemy with provided name and whether it is an elite,
     * elite enemies will have the capability of calling reinforcements
     *
     * @param name  the name of the enemy
     * @param elite the elite status of the enemy
     */
    public RegularEnemy(String name, boolean elite) {
        super(name, elite ? 5 : 1);
        this.isElite = elite;
        this.canCallReinforcements = elite; // elite mobs can summon reinforcements
        if (elite) {
            // enhance elite mob's attributes
            maxHP += 50;
            currentHP = maxHP;
            attack += 10;
            defense += 5;
            speed += 3;
            dropRate += 0.2; // elite mobs have higher drop rate
        }
    }

    /**
     * Is the enemy an elite boolean
     *
     * @return the boolean indicating whether it is an elite enemy
     */
    public boolean isElite() { return isElite; }

    /**
     * Sets elite status of an enemy
     *
     * @param elite the boolean to set whether the enemy will be an elite or not
     */
    public void setElite(boolean elite) { isElite = elite; }

    /**
     * Can call reinforcements boolean, indicating whether the enemy can call for reinforcements
     *
     * @return the boolean of the capability of the enemy can call for reinforcements.
     */
    public boolean canCallReinforcements() { return canCallReinforcements; }

    /**
     * Sets whether the enemy can call reinforcements
     *
     * @param canCallReinforcements the capability to call reinforcements
     */
    public void setCanCallReinforcements(boolean canCallReinforcements) {
        this.canCallReinforcements = canCallReinforcements;
    }

    /**
     * Enemy calls for reinforcements
     */
    public void callReinforcements() {
        if (!canCallReinforcements) {
            System.out.println(name + " cannot call reinforcements");
            return;
        }
        System.out.println(name + " summoned reinforcements");
        if (isElite) {
            System.out.println("Summoned 3 reinforcements");
            // not implemented with the summoning yet, so just printing out information
        } else {
            System.out.println("Tried to summon reinforcements, but failed");
        }
    }

    /**
     * The regular enemy uses skill. If the enemy is an elite, then there will be 40% chance of calling reinforcements
     * If not, then it will only use a standard skill.
     */

    @Override
    public void useSkill() {
        if (isElite && random.nextDouble() < 0.4) { // 40% Chance summon reinforcement
            callReinforcements();
        } else {
            super.useSkill();
        }
    }

    /**
     * Calls the superclass {@link Enemy}'s displayInfo method first,
     * then displays(prints) whether it is an elite and whether it can call for reinforcements
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Enemy type: " + (isElite ? "Elite" : "Regular"));
        System.out.println("Can call reinforcement: " + canCallReinforcements);
    }

    /**
     * Calls the superclass {@link Enemy}'s toCSVFormat method first, then add
     * whether it is an elite and whether it can call for reinforcements to the String CSV
     * @return the CSV format of the detailed information about the enemy
     */
    @Override
    public String toCSVFormat() {
        String var = "," + isElite + "," + canCallReinforcements + ",";
        return super.toCSVFormat() + var;
    }
}