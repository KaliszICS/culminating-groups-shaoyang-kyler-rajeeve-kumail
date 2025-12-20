package entities.characters;

import entities.abs.BattleUnit;
import entities.equipment.*;

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

        // adjust attributes with lvls
        this.maxHP += (level - 1) * 20;
        this.currentHP = this.maxHP;
        this.attack += (level - 1) * 2;
        this.defense += (level - 1);
        this.speed += (level - 1);
    }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public Equipment[] getEquippedItems() { return equippedItems; }
    public SkillTree getSkillTree() { return skillTree; }

    public void levelUp() {
        level++;
        maxHP += 20;
        currentHP = maxHP; // full hp when lvl up
        attack += 2;
        defense += 1;
        speed += 1;
        experience = 0;

        System.out.println(name + " upgraded to level " + level);
        System.out.println("HP: " + maxHP + ", attack: " + attack + ", defense: " + defense + ", speed: " + speed);
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
            System.out.println("Invalid equipment!");
            return;
        }

        for (int i = 0; i < equippedItems.length; i++) {
            if (equippedItems[i] == null) {
                equippedItems[i] = item;
                System.out.println(name + " equipped " + item.getName() + " on slot " + i);

                // apply equipment attributes to character
                applyEquipmentStats(item);
                return;
            }
        }

        System.out.println("Slots are full! ");
    }

    public void unequip(int slot) {
        if (slot < 0 || slot >= equippedItems.length) {
            System.out.println("Invalid slot!");
            return;
        }

        if (equippedItems[slot] != null) {
            System.out.println(name + " unequipped " + equippedItems[slot].getName());

            // remove equipment attributes
            removeEquipmentStats(equippedItems[slot]);
            equippedItems[slot] = null;
        } else {
            System.out.println("This slot does not exist!");
        }
    }

    private void applyEquipmentStats(Equipment item) {
        // apply increase different attributes (equipments)
        attack += item.getStat("attack");
        defense += item.getStat("defense");
        maxHP += item.getStat("hp");
        speed += item.getStat("speed");
        currentHP = Math.min(currentHP + item.getStat("hp"), maxHP);
    }

    private void removeEquipmentStats(Equipment item) {
        // remove increase different attributes (equipments)
        attack -= item.getStat("attack");
        defense -= item.getStat("defense");
        maxHP -= item.getStat("hp");
        speed -= item.getStat("speed");
        currentHP = Math.max(currentHP - item.getStat("hp"), 1);
    }

    @Override
    public void useSkill() {
        System.out.println(name + " used basic skill!");
        // base skill, subclasses can override
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Character info ===");
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("Experience: " + experience + "/" + (level * 100));
        System.out.println("HP: " + currentHP + "/" + maxHP);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Speed: " + speed);

        System.out.println("Equipped:");
        for (int i = 0; i < equippedItems.length; i++) {
            if (equippedItems[i] != null) {
                System.out.println("  Slot " + i + ": " + equippedItems[i].getName());
            }
        }
    }

    // SkillTree
    public class SkillTree {
        private boolean[] unlockedSkills;

        public SkillTree() {
            unlockedSkills = new boolean[5]; // 假设有5个技能
            unlockedSkills[0] = true; // 第一个技能默认解锁
        }

        public void unlockSkill(int skillId) {
            if (skillId >= 0 && skillId < unlockedSkills.length) {
                unlockedSkills[skillId] = true;
                System.out.println("Unlocked skill: " + skillId);
            }
        }

        public boolean isSkillUnlocked(int skillId) {
            return skillId >= 0 && skillId < unlockedSkills.length && unlockedSkills[skillId];
        }
    }
}