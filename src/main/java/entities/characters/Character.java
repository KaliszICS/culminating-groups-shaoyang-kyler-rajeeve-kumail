package entities.characters;

import entities.abs.BattleUnit;
import entities.equipment.Equipment;

public class Character extends BattleUnit {
    protected int level;
    protected int experience;
    protected Equipment[] equippedItems;
    protected SkillTree skillTree;

    public Character() {
        super();
        this.level = 1;
        this.experience = 0;
        this.equippedItems = new Equipment[4]; // 假设有4个装备槽
        this.skillTree = new SkillTree();
    }

    public Character(String name, int level) {
        super(name, 100 + (level - 1) * 20, 10 + (level - 1) * 2);
        this.level = Math.max(1, level);
        this.experience = 0;
        this.equippedItems = new Equipment[4];
        this.skillTree = new SkillTree();

        // 根据等级调整属性
        this.maxHP += (level - 1) * 20;
        this.currentHP = this.maxHP;
        this.attack += (level - 1) * 2;
        this.defense += (level - 1);
        this.speed += (level - 1);
    }

    public void levelUp() {
        level++;
        maxHP += 20;
        currentHP = maxHP; // 升级时回满血
        attack += 2;
        defense += 1;
        speed += 1;
        experience = 0;

        System.out.println(name + " 升级到 " + level + " 级！");
        System.out.println("HP: " + maxHP + ", 攻击: " + attack + ", 防御: " + defense + ", 速度: " + speed);
    }

    public void addExperience(int exp) {
        experience += exp;
        int expNeeded = level * 100;
        while (experience >= expNeeded) {
            experience -= expNeeded;
            levelUp();
            expNeeded = level * 100;
        }
    }

    public void equip(Equipment item) {
        if (item == null) {
            System.out.println("无效的装备！");
            return;
        }

        for (int i = 0; i < equippedItems.length; i++) {
            if (equippedItems[i] == null) {
                equippedItems[i] = item;
                System.out.println(name + " 装备了 " + item.getName() + " 在槽位 " + i);

                // 应用装备属性加成
                applyEquipmentStats(item);
                return;
            }
        }

        System.out.println("装备槽已满！");
    }

    public void unequip(int slot) {
        if (slot < 0 || slot >= equippedItems.length) {
            System.out.println("无效的装备槽！");
            return;
        }

        if (equippedItems[slot] != null) {
            System.out.println(name + " 卸下了 " + equippedItems[slot].getName());

            // 移除装备属性加成
            removeEquipmentStats(equippedItems[slot]);
            equippedItems[slot] = null;
        } else {
            System.out.println("该装备槽为空！");
        }
    }

    private void applyEquipmentStats(Equipment item) {
        // 根据装备增加属性
        attack += item.getStat("attack");
        defense += item.getStat("defense");
        maxHP += item.getStat("hp");
        speed += item.getStat("speed");
        currentHP = Math.min(currentHP + item.getStat("hp"), maxHP);
    }

    private void removeEquipmentStats(Equipment item) {
        // 移除装备增加的属性
        attack -= item.getStat("attack");
        defense -= item.getStat("defense");
        maxHP -= item.getStat("hp");
        speed -= item.getStat("speed");
        currentHP = Math.max(currentHP - item.getStat("hp"), 1);
    }

    @Override
    public void useSkill() {
        System.out.println(name + " 使用了普通技能！");
        // 基本技能实现，子类可以重写
    }

    @Override
    public void displayInfo() {
        System.out.println("=== 角色信息 ===");
        System.out.println("名称: " + name);
        System.out.println("等级: " + level);
        System.out.println("经验: " + experience + "/" + (level * 100));
        System.out.println("生命值: " + currentHP + "/" + maxHP);
        System.out.println("攻击力: " + attack);
        System.out.println("防御力: " + defense);
        System.out.println("速度: " + speed);

        System.out.println("已装备:");
        for (int i = 0; i < equippedItems.length; i++) {
            if (equippedItems[i] != null) {
                System.out.println("  槽位 " + i + ": " + equippedItems[i].getName());
            }
        }
    }

    // Getter 和 Setter
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public Equipment[] getEquippedItems() { return equippedItems; }
    public SkillTree getSkillTree() { return skillTree; }

    // SkillTree 内部类
    public class SkillTree {
        private boolean[] unlockedSkills;

        public SkillTree() {
            unlockedSkills = new boolean[5]; // 假设有5个技能
            unlockedSkills[0] = true; // 第一个技能默认解锁
        }

        public void unlockSkill(int skillId) {
            if (skillId >= 0 && skillId < unlockedSkills.length) {
                unlockedSkills[skillId] = true;
                System.out.println("解锁了技能 " + skillId);
            }
        }

        public boolean isSkillUnlocked(int skillId) {
            return skillId >= 0 && skillId < unlockedSkills.length && unlockedSkills[skillId];
        }
    }
}