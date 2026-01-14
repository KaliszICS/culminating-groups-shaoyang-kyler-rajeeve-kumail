package entities.characters;

import entities.abs.BattleUnit;
import entities.equipment.Equipment;

import java.io.Serializable;

/**
 * This class describes a playable character, with a boolean of whether the class can be controlled by a player,
 * friendship level, also inherited from the parent class: {@link Character}
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 * @see BattleUnit
 * @see Equipment
 */
public class PlayableCharacter extends Character implements Serializable {
    //added serializable, so now people can save characters to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    /**
     * Is player controllable?
     */
    protected boolean isPlayerControlled;
    /**
     * The Friendship level.
     */
    protected int friendshipLevel;

    /**
     * Instantiates a new Playable character.
     */
    public PlayableCharacter() {
        super();
        this.isPlayerControlled = true;
        this.friendshipLevel = 1;
    }

    /**
     * Instantiates a new Playable character.
     *
     * @param name             the name
     * @param playerControlled the player controlled
     */
    public PlayableCharacter(String name, boolean playerControlled) {
        super(name, 1);
        this.isPlayerControlled = playerControlled;
        this.friendshipLevel = 1;
    }

    /**
     * Instantiates a new Playable character.
     *
     * @param name             the name
     * @param level            the level
     * @param playerControlled the player controlled
     */
    public PlayableCharacter(String name, int level, boolean playerControlled) {
        super(name, level);
        this.isPlayerControlled = playerControlled;
        this.friendshipLevel = 1;
    }

    /**
     * Is player controlled boolean.
     *
     * @return the boolean
     */
    public boolean isPlayerControlled() { return isPlayerControlled; }

    /**
     * Sets player can be controlled.
     *
     * @param playerControlled the state of player controlled
     */
    public void setPlayerControlled(boolean playerControlled) { isPlayerControlled = playerControlled; }

    /**
     * Gets friendship level.
     *
     * @return the friendship level
     */
    public int getFriendshipLevel() { return friendshipLevel; }

    /**
     * Sets friendship level of the character
     *
     * @param friendshipLevel the friendship level to set
     */
    public void setFriendshipLevel(int friendshipLevel) { this.friendshipLevel = friendshipLevel; }

    /**
     * Switch control.
     */
    public void switchControl() {
        isPlayerControlled = !isPlayerControlled;
        System.out.println(name + "'s state has changed to: " +
                (isPlayerControlled ? "Player Controlled" : "Program Controlled"));
    }

    /**
     * Increase friendship of the characters.
     */
    public void increaseFriendship() {
        friendshipLevel++;
        System.out.println(name + "'s friendship level increased to level: " + friendshipLevel + "!");

        // Friendship rewards
        if (friendshipLevel % 5 == 0) {
            attack += 10;
            defense += 5;
            System.out.println("Received friendship reward：ATK + 10, DEF + 5 permanently!");
        }
    }

    /**
     * Increase friendship of the characters.
     *
     * @param amount the amount of friendship to increase
     */
    public void increaseFriendship(int amount) {
        friendshipLevel += amount;
        System.out.println(name + "'s friendship increased to " + amount + " points, current friendship level is: " + friendshipLevel);
    }


    /**
     * Displays (prints) the info of this playable character
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Control State:  " + (isPlayerControlled ? "Player" : "Program") + " Controlled");
        System.out.println("Friendship level: " + friendshipLevel);
    }

    /**
     * Record stats of this playable character into a CSV
     * @return the String of CSV
     */
    @Override
    public String toCSVFormat() {
        String var = "," + level + "," + isPlayerControlled + "," + friendshipLevel;
        return super.toCSVFormat() + var;
    }

}
