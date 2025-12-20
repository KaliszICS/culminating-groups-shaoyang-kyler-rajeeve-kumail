package entities.enemies;

import entities.abs.BattleUnit;
import entities.items.Item;

import java.util.Random;

public class Enemy extends BattleUnit {
    protected int difficultyLevel;
    protected double dropRate;
    protected Item[] possibleDrops;

    protected static final Random random = new Random();

    public Enemy() {
        super();
        this.difficultyLevel = 1;
        this.dropRate = 0.3; // 30%掉落率
        this.possibleDrops = new Item[3];
    }

    public Enemy(String name, int difficulty) {
        super(name, 50 + difficulty * 10, 5 + difficulty * 2);
        this.difficultyLevel = difficulty;
        this.dropRate = 0.1 + difficulty * 0.05; // 难度越高掉落率越高
        this.possibleDrops = new Item[3];

        // 根据难度调整属性
        this.maxHP += difficulty * 10;
        this.currentHP = this.maxHP;
        this.attack += difficulty * 2;
        this.defense += difficulty;
    }

    public Item calculateDrop() {
        if (random.nextDouble() < dropRate) {
            if (possibleDrops.length > 0) {
                // 随机选择一个掉落物品
                Item drop = possibleDrops[random.nextInt(possibleDrops.length)];
                if (drop != null) {
                    System.out.println(name + " 掉落了: " + drop.getName());
                    return drop;
                }
            }
        }
        System.out.println(name + " 没有掉落物品");
        return null;
    }

    @Override
    public void useSkill() {
        System.out.println(name + " 使用敌人技能！");

        // 随机选择技能效果
        int skillType = random.nextInt(3);
        switch (skillType) {
            case 0:
                System.out.println("使用普通攻击");
                break;
            case 1:
                System.out.println("使用防御技能");
                defense += 5;
                break;
            case 2:
                System.out.println("使用恢复技能");
                heal(20);
                break;
        }
    }

    public void setPossibleDrops(Item[] drops) {
        this.possibleDrops = drops;
    }

    public void addPossibleDrop(Item drop) {
        for (int i = 0; i < possibleDrops.length; i++) {
            if (possibleDrops[i] == null) {
                possibleDrops[i] = drop;
                return;
            }
        }
        System.out.println("掉落列表已满");
    }

    @Override
    public void displayInfo() {
        System.out.println("=== 敌人信息 ===");
        System.out.println("名称: " + name);
        System.out.println("难度等级: " + difficultyLevel);
        System.out.println("生命值: " + currentHP + "/" + maxHP);
        System.out.println("攻击力: " + attack);
        System.out.println("防御力: " + defense);
        System.out.println("速度: " + speed);
        System.out.println("掉落率: " + (dropRate * 100) + "%");

        System.out.println("可能掉落:");
        for (Item drop : possibleDrops) {
            if (drop != null) {
                System.out.println("  - " + drop.getName());
            }
        }
    }

    @Override
    public String toCSVFormat() {
        StringBuilder drops = new StringBuilder();
        for (Item drop : possibleDrops) {
            if (drop != null) {
                if (drops.length() > 0) drops.append(";");
                drops.append(drop.getName());
            }
        }

        return super.toCSVFormat() + String.format(",%d,%.2f,\"%s\"",
                difficultyLevel, dropRate, drops.toString());
    }

    // Getter 和 Setter
    public int getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(int difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public double getDropRate() { return dropRate; }
    public void setDropRate(double dropRate) { this.dropRate = dropRate; }
    public Item[] getPossibleDrops() { return possibleDrops; }
}