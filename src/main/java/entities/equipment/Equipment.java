package entities.equipment;

import entities.abs.GameEntity;
import entities.characters.Character;

import java.util.HashMap;
import java.util.Map;

public abstract class Equipment extends GameEntity {
    protected int requiredLevel;
    protected String slot; // 装备槽位：武器、头盔、衣服、鞋子等
    protected Map<String, Integer> stats;

    public Equipment() {
        super();
        this.requiredLevel = 1;
        this.slot = "武器";
        this.stats = new HashMap<>();
        initializeStats();
    }

    public Equipment(String name, String slot) {
        super(name, nextId);
        this.requiredLevel = 1;
        this.slot = slot;
        this.stats = new HashMap<>();
        initializeStats();
    }

    public Equipment(String name, String slot, int requiredLevel) {
        this(name, slot);
        this.requiredLevel = requiredLevel;
    }

    private void initializeStats() {
        // 基础属性
        stats.put("attack", 10);
        stats.put("defense", 5);
        stats.put("hp", 50);
        stats.put("speed", 5);
        stats.put("critical_rate", 5); // 暴击率%
        stats.put("critical_damage", 50); // 暴击伤害%
    }

    public void equip(Character character) {
        if (character.getLevel() < requiredLevel) {
            System.out.println("角色等级不足！需要等级 " + requiredLevel);
            return;
        }

        System.out.println(character.getName() + " 装备了 " + name + " 在 " + slot + " 槽位");

        // 检查套装效果
        if (this instanceof Relic) {
            Relic relic = (Relic) this;
            relic.checkSetBonus(character);
        }
    }

    public void unequip() {
        System.out.println("卸下了 " + name);
    }

    public abstract void calculateStats();

    public void enhance() {
        System.out.println("强化 " + name);
        // 基础强化
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            stats.put(entry.getKey(), (int)(entry.getValue() * 1.1));
        }
    }

    public void enhance(int level) {
        System.out.println("将 " + name + " 强化到 " + level + " 级");
        // 根据等级强化
        double multiplier = 1.0 + (level * 0.05);
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            stats.put(entry.getKey(), (int)(entry.getValue() * multiplier));
        }
    }

    public void enhance(String stat, int amount) {
        if (stats.containsKey(stat)) {
            stats.put(stat, stats.get(stat) + amount);
            System.out.println(name + " 的 " + stat + " 增加了 " + amount);
        }
    }

    public int getStat(String statName) {
        return stats.getOrDefault(statName, 0);
    }

    public void setStat(String statName, int value) {
        stats.put(statName, value);
    }

    @Override
    public void displayInfo() {
        System.out.println("=== 装备信息 ===");
        System.out.println("名称: " + name);
        System.out.println("槽位: " + slot);
        System.out.println("需求等级: " + requiredLevel);
        System.out.println("属性:");
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public String toCSVFormat() {
        StringBuilder statsStr = new StringBuilder();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            if (statsStr.length() > 0) statsStr.append(";");
            statsStr.append(entry.getKey()).append(":").append(entry.getValue());
        }

        return super.toCSVFormat() + String.format(",%d,%s,\"%s\"",
                requiredLevel, slot, statsStr.toString());
    }

    // Getter 和 Setter
    public int getRequiredLevel() { return requiredLevel; }
    public void setRequiredLevel(int requiredLevel) { this.requiredLevel = requiredLevel; }
    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }
    public Map<String, Integer> getStats() { return stats; }
    public void setStats(Map<String, Integer> stats) { this.stats = stats; }
}