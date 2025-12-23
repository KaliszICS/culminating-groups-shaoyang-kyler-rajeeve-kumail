package entities.enemies;

public class RegularEnemy extends Enemy {
    private boolean isElite;
    private boolean canCallReinforcements;

    public RegularEnemy() {
        super();
        this.isElite = false;
        this.canCallReinforcements = false;
    }

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

    public boolean isElite() { return isElite; }
    public void setElite(boolean elite) { isElite = elite; }
    public boolean canCallReinforcements() { return canCallReinforcements; }
    public void setCanCallReinforcements(boolean canCallReinforcements) {
        this.canCallReinforcements = canCallReinforcements;
    }
    public void callReinforcements() {
        if (!canCallReinforcements) {
            System.out.println(name + " cannot call reinforcements");
            return;
        }

        System.out.println(name + " summoned reinforcements");

        if (isElite) {
            System.out.println("Summoned 3 reinforcements");
            // 在实际游戏中，这里会创建新的敌人对象
        } else {
            System.out.println("Tried to summon reinforcements, but failed");
        }
    }

    @Override
    public void useSkill() {
        if (isElite && random.nextDouble() < 0.4) { // 40% Chance summon reinforcement
            callReinforcements();
        } else {
            super.useSkill();
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Enemy type: " + (isElite ? "Elite" : "Regular"));
        System.out.println("Can call reinforcement: " + canCallReinforcements);
    }

    @Override
    public String toCSVFormat() {
        String var = "," + isElite + "," + canCallReinforcements + ",";
        return super.toCSVFormat() + var;
    }
}