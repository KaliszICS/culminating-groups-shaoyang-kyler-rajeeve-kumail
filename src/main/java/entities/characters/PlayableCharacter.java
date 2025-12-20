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

    public void switchControl() {
        isPlayerControlled = !isPlayerControlled;
        System.out.println(name + " 的控制状态已切换为: " +
                (isPlayerControlled ? "玩家控制" : "AI控制"));
    }

    public void increaseFriendship() {
        friendshipLevel++;
        System.out.println(name + " 的好感度提升到 " + friendshipLevel + " 级！");

        // 好感度奖励
        if (friendshipLevel % 5 == 0) {
            attack += 5;
            System.out.println("获得好感度奖励：攻击力+5");
        }
    }

    public void increaseFriendship(int amount) {
        friendshipLevel += amount;
        System.out.println(name + " 的好感度提升 " + amount + " 点，当前 " + friendshipLevel + " 级！");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("控制状态: " + (isPlayerControlled ? "玩家控制" : "AI控制"));
        System.out.println("好感度等级: " + friendshipLevel);
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",%d,%b,%d",
                level, isPlayerControlled, friendshipLevel);
    }

    // Getter 和 Setter
    public boolean isPlayerControlled() { return isPlayerControlled; }
    public void setPlayerControlled(boolean playerControlled) { isPlayerControlled = playerControlled; }
    public int getFriendshipLevel() { return friendshipLevel; }
    public void setFriendshipLevel(int friendshipLevel) { this.friendshipLevel = friendshipLevel; }
}