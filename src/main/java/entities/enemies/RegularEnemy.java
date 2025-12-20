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
        this.canCallReinforcements = elite; // 精英怪可以召唤援军

        if (elite) {
            // 精英怪属性增强
            maxHP += 50;
            currentHP = maxHP;
            attack += 10;
            defense += 5;
            speed += 3;
            dropRate += 0.2; // 精英怪掉落率更高
        }
    }

    public void callReinforcements() {
        if (!canCallReinforcements) {
            System.out.println(name + " 无法召唤援军");
            return;
        }

        System.out.println(name + " 召唤了援军！");

        if (isElite) {
            System.out.println("召唤了3个普通敌人作为援军！");
            // 在实际游戏中，这里会创建新的敌人对象
        } else {
            System.out.println("试图召唤援军但失败了！");
        }
    }

    @Override
    public void useSkill() {
        if (isElite && random.nextDouble() < 0.4) { // 40%概率召唤援军
            callReinforcements();
        } else {
            super.useSkill();
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("敌人类型: " + (isElite ? "精英" : "普通"));
        System.out.println("可召唤援军: " + (canCallReinforcements ? "是" : "否"));
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",%b,%b", isElite, canCallReinforcements);
    }

    // Getter 和 Setter
    public boolean isElite() { return isElite; }
    public void setElite(boolean elite) { isElite = elite; }
    public boolean canCallReinforcements() { return canCallReinforcements; }
    public void setCanCallReinforcements(boolean canCallReinforcements) {
        this.canCallReinforcements = canCallReinforcements;
    }
}