package entities.characters;

import entities.abs.BattleUnit;
import entities.equipment.*;

import java.io.Serializable;

/**
 * Represents a character entity in the game
 * level progression, equipment management, and skill development are included in all characters
 * Extends {@link BattleUnit} since characters are a part of a battle unit.
 *
 * @author Shaoyang Chen
 * @see BattleUnit
 * @see Equipment
 */
public class Character extends BattleUnit implements Serializable {
    //added serializable, so now people can save characters to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    /**
     * The Level of character
     */
    protected int level;
    /**
     * The Experience amount of character
     */
    protected int experience;
    /**
     * The Equipped items list of a character
     */
    protected Equipment[] equippedItems;
    /**
     * The Skill tree of character
     */
    protected SkillTree skillTree;

    /**
     * Constructs a character with default values.
     * Level 1, 0 experience, 4 empty equipment slots, and a new skill tree with the first skill unlocked by default.
     */
    public Character() {
        super();
        this.level = 1;
        this.experience = 0;
        this.equippedItems = new Equipment[4];
        this.skillTree = new SkillTree();
    }

    /**
     * Constructs a character with specified name and level.
     * Base attributes are scaled based on the provided level.
     * Experience starts at lv 0, 4 empty equipment slots, and a new skill tree is initialized.
     *
     * @param name  the name of this character
     * @param level the starting level for this character (min 1)
     */
    public Character(String name, int level) {
        super(name, 100 + (level - 1) * 20, 10 + (level - 1) * 2);
        this.level = Math.max(1, level);
        this.experience = 0;
        this.equippedItems = new Equipment[4];
        this.skillTree = new SkillTree();
        // adjust character attributes with levels
        this.maxHP += (level - 1) * 20;
        this.currentHP = this.maxHP;
        this.attack += (level - 1) * 2;
        this.defense += (level - 1);
        this.speed += (level - 1);
    }

    /**
     * Returns the character's level.
     *
     * @return the character's level
     */
    public int getLevel() { return level; }

    /**
     * Returns the character's experience points.
     *
     * @return the character's experience points
     */
    public int getExperience() { return experience; }

    /**
     * Returns the array of equipped items.
     *
     * @return the array of equipped items.
     */
    public Equipment[] getEquippedItems() { return equippedItems; }

    /**
     * Returns the character's skill tree
     *
     * @return the character's skill tree
     */
    public SkillTree getSkillTree() { return skillTree; }

    /**
     * Increases the character's level by 1 and add in attributes.
     * HP will be maxed when leveling up, experience is reset to 0.
     * This class also prints out the leveling up message.
     */
    public void levelUp() {
        level++;
        maxHP += 20;
        currentHP = maxHP; // full hp when level up
        attack += 2;
        defense += 1;
        speed += 1;
        experience = 0;

        System.out.println(name + " upgraded to level " + level);
        System.out.println("HP: " + maxHP + ", attack: " + attack + ", defense: " + defense + ", speed: " + speed);
    }

    /**
     * Adds experience to this character and handles level progression.
     * Automatically levels up when experience reaches or exceeds the required amount (level * 100). Multiple level-ups may occur if enough experience is added.
     *
     * @param exp the amount of experience points to add
     */
    public void addExperience(int exp) {
        experience += exp;
        int expNeeded = level * 100;
        while (experience >= expNeeded) {
            experience -= expNeeded;
            levelUp();
            expNeeded = level * 100;
        }
    }

    /**
     * Equips an item to the first available equipment slot.
     * Applies the item's stat bonuses to the character's attributes.
     * This class also prints out the equipping message.
     *
     * @param item the equipment to equip (cannot be null)
     */
    public void equip(Equipment item) {
        if (item == null) {
            System.out.println("Invalid equipment!");
            return;
        }
        for (int i = 0; i < equippedItems.length; i++) {
            if (equippedItems[i] == null) {
                equippedItems[i] = item;
                System.out.println(name + " equipped " + item.getName() + " on slot " + i);
                // apply equipment attributes
                applyEquipmentStats(item);
                return;
            }
        }
        System.out.println("Slots are full! ");
    }

    /**
     * Unequips an item from a specific equipment slot.
     * Removes the item's stat bonuses from the character's attributes.
     * This class also prints out the unequipping message.
     *
     * @param slot the slot index to unequip (0, 1, 2, 3)
     */
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

    /**
     * Applies an equipment's bonuses to this character's attributes.
     * Increases attack, defense, max HP, speed, and adjusts current HP.
     *
     * @param item the equipment whose stats should be applied
     */
    private void applyEquipmentStats(Equipment item) {
        // apply increased different attributes (equipments)
        attack += item.getStat("attack");
        defense += item.getStat("defense");
        maxHP += item.getStat("hp");
        speed += item.getStat("speed");
        currentHP = Math.min(currentHP + item.getStat("hp"), maxHP);
    }

    /**
     * Removes an equipment's stat bonuses from this character's attributes.
     * Decreases attack, defense, max HP, speed, and adjusts current HP.
     *
     * @param item the equipment whose stats should be removed
     */
    private void removeEquipmentStats(Equipment item) {
        // remove increased different attributes (equipments)
        attack -= item.getStat("attack");
        defense -= item.getStat("defense");
        maxHP -= item.getStat("hp");
        speed -= item.getStat("speed");
        currentHP = Math.max(currentHP - item.getStat("hp"), 1);
    }

    /**
     * Runs the character's basic skill.
     */
    @Override
    public void useSkill() {
        System.out.println(name + " used basic skill!");
        // base skill, subclasses can override
    }

    /**
     * Displays detailed information about this character.
     * Shows character statistics, level information, experience progress, and equipped items.
     */
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

    /**
     * Represents a skill tree for a character, tracking unlocked abilities.
     * Each character has a skill tree with 5 skills.
     * The first skill is unlocked by default.
     */
    public class SkillTree implements Serializable {
        //added serializable, so now people can save SkillTree to a file (updated on 2026/1/13 emergency update)
        private static final long serialVersionUID = 1L;
        private boolean[] unlockedSkills;

        /**
         * Constructs a SkillTree with default state.
         * Creates 5 skill slots and unlocks the first skill by default.
         */
        public SkillTree() {
            unlockedSkills = new boolean[5]; // in HSR, characters may have more than 5 skills, but fixed to 5 here
            unlockedSkills[0] = true; // unlock 1st skill as default
        }

        /**
         * Unlocks a specific skill by skillId.
         * Displays unlock information to standard output.
         *
         * @param skillId the ID of the skill to unlock (0, 1, 2, 3, 4)
         */
        public void unlockSkill(int skillId) {
            if (skillId >= 0 && skillId < unlockedSkills.length) {
                unlockedSkills[skillId] = true;
                System.out.println("Unlocked skill: " + skillId);
            }
        }

        /**
         * Checks if a specific skill is unlocked.
         *
         * @param skillId the ID of the skill to check (0-4)
         * @return true if the skill is unlocked, false otherwise or if ID is invalid
         */
        public boolean isSkillUnlocked(int skillId) {
            return skillId >= 0 && skillId < unlockedSkills.length && unlockedSkills[skillId];
        }
    }
}