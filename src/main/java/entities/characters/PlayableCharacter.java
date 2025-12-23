package entities.characters;

import entities.equipment.Equipment;

public class PlayableCharacter extends Character {
    protected boolean isPlayerControlled;
    protected int friendshipLevel;

    public PlayableCharacter() {
        super();
        this.isPlayerControlled = true;
        this.friendshipLevel = 1;
    }

    public PlayableCharacter(String name, boolean playerControlled) {
        super(name, 1);
        this.isPlayerControlled = playerControlled;
        this.friendshipLevel = 1;
    }

    public PlayableCharacter(String name, int level, boolean playerControlled) {
        super(name, level);
        this.isPlayerControlled = playerControlled;
        this.friendshipLevel = 1;
    }

    public boolean isPlayerControlled() { return isPlayerControlled; }
    public void setPlayerControlled(boolean playerControlled) { isPlayerControlled = playerControlled; }
    public int getFriendshipLevel() { return friendshipLevel; }
    public void setFriendshipLevel(int friendshipLevel) { this.friendshipLevel = friendshipLevel; }

    public void switchControl() {
        isPlayerControlled = !isPlayerControlled;
        System.out.println(name + "'s state has changed to: " +
                (isPlayerControlled ? "Player Controlled" : "Program Controlled"));
    }

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

    public void increaseFriendship(int amount) {
        friendshipLevel += amount;
        System.out.println(name + "'s friendship increased to " + amount + " points, current friendship level is: " + friendshipLevel);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Control State:  " + (isPlayerControlled ? "Player" : "Program") + " Controlled");
        System.out.println("Friendship level: " + friendshipLevel);
    }

    @Override
    public String toCSVFormat() {
        String var = "," + level + "," + isPlayerControlled + "," + friendshipLevel;
        return super.toCSVFormat() + var;
    }

}