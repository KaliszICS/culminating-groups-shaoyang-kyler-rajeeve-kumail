package entities.abs;

import entities.abs.GameEntity;

public abstract class BattleUnit extends GameEntity {
    protected int maxHP;
    protected int currentHP;
    protected int attack;
    protected int defense;
    protected int speed;

    public BattleUnit() {
        super();
        this.maxHP = 100;
        this.currentHP = maxHP;
        this.attack = 10;
        this.defense = 5;
        this.speed = 10;
    }

    public BattleUnit(String name, int maxHP, int attack) {
        super(name, nextId);
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = 5;
        this.speed = 10;
    }

    public BattleUnit(String name, int maxHP, int attack, int defense, int speed) {
        super(name, nextId);
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public void attack(BattleUnit target) {
        if (target == null) {
            System.out.println(name + " 尝试攻击，但没有目标！");
            return;
        }

        int damage = Math.max(attack - target.defense, 1);
        System.out.println(name + " 对 " + target.name + " 造成 " + damage + " 点伤害！");
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        currentHP = Math.max(currentHP - damage, 0);
        System.out.println(name + " 受到 " + damage + " 点伤害，剩余HP: " + currentHP);

        if (currentHP <= 0) {
            System.out.println(name + " 被击败了！");
        }
    }

    // 抽象方法 - 使用技能
    public abstract void useSkill();

    // Getter 和 Setter 方法
    public int getMaxHP() { return maxHP; }
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
        if (currentHP > maxHP) {
            currentHP = maxHP;
        }
    }

    public int getCurrentHP() { return currentHP; }
    public void setCurrentHP(int currentHP) {
        this.currentHP = Math.min(Math.max(currentHP, 0), maxHP);
    }

    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }

    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public void heal(int amount) {
        currentHP = Math.min(currentHP + amount, maxHP);
        System.out.println(name + " 恢复了 " + amount + " 点HP，当前HP: " + currentHP);
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",%d,%d,%d,%d,%d", maxHP, currentHP, attack, defense, speed);
    }
}