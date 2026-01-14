package entities.equipment;

import entities.items.Item;
import entities.characters.Character;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents all equipments in the game. Including relics and light cones.
 * Equipments have the following attributes: required level to equip, slot, stats of equipment (using hashmap).
 * Inherited from {@link Item}, which means they will have shared attributes.
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 * @see Character
 * @see Item
 */
public abstract class Equipment extends Item implements Serializable {
    //added serializable, so now people can save items to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    /**
     * The Required level for a character to equip this item
     */
    protected int requiredLevel;
    /**
     * The Slot to equip.
     */
    protected String slot;
    /**
     * The Stats of the equipment
     */
    protected Map<String, Integer> stats;

    /**
     * Instantiates a new equipment with default attributes and names
     */
    public Equipment() {
        super();
        this.requiredLevel = 1;
        this.slot = "Relic";
        this.stats = new HashMap<>();
        this.itemType = "Equipment";
        initializeStats();
    }

    /**
     * Instantiates a new equipment with provided name and slot
     *
     * @param name the name
     * @param slot the slot
     */
    public Equipment(String name, String slot) {
        super(name, 50); // base value = 50
        this.requiredLevel = 1;
        this.slot = slot;
        this.stats = new HashMap<>();
        this.itemType = "Equipment";
        initializeStats();
    }

    /**
     * Instantiates a new Equipment with the name, slot and required level
     *
     * @param name          the name
     * @param slot          the slot
     * @param requiredLevel the required level
     */
    public Equipment(String name, String slot, int requiredLevel) {
        this(name, slot);
        this.requiredLevel = requiredLevel;
    }

    /**
     * Gets required level
     *
     * @return the required level
     */
    public int getRequiredLevel() { return requiredLevel; }

    /**
     * Sets required level
     *
     * @param requiredLevel the required level
     */
    public void setRequiredLevel(int requiredLevel) { this.requiredLevel = requiredLevel; }

    /**
     * Gets slot to equip
     *
     * @return the slot to equip
     */
    public String getSlot() { return slot; }

    /**
     * Sets slot to equip
     *
     * @param slot the slot to equip
     */
    public void setSlot(String slot) { this.slot = slot; }

    /**
     * Gets stats of the equipment in hashmap form
     *
     * @return the stats of the equipment in hashmap form
     */
    public Map<String, Integer> getStats() { return stats; }

    /**
     * Sets stats of the equipment
     *
     * @param stats the stats of the equipment
     */
    public void setStats(Map<String, Integer> stats) { this.stats = stats; }

    /**
     * Initialize the stats of the equipment with default values.
     * Stats includes attack, defense, hp, speed, critical hit rate in %, critical damage in %
     */
    private void initializeStats() {
        // base attributes
        stats.put("attack", 10);
        stats.put("defense", 5);
        stats.put("hp", 50);
        stats.put("speed", 5);
        stats.put("critical_rate", 5); // rate %
        stats.put("critical_damage", 50); // damage %
    }

    /**
     * Equip the equipment to the character
     *
     * @param character the character to equip this equipment
     */
    public void equip(Character character) {
        if (character.getLevel() < requiredLevel) {
            System.out.println("Character doesn't have required level! Level required: " + requiredLevel);
            return;
        }
        System.out.println(character.getName() + " equipped " + name + " on slot " + slot);
        // check for set bonus
        if (this instanceof Relic) {
            Relic relic = (Relic) this;
            relic.checkSetBonus(character);
        }
    }

    /**
     * Unequip the equipment, this method only prints out the message,
     * the actual unequip process is completed in {@link systems.inventory.Inventory}'s unequipItem method
     */
    public void unequip() {
        System.out.println("Unequipped " + name);
    }

    /**
     * Calculate stats of the equipment (must be overwritten in subclasses)
     */
    public abstract void calculateStats();

    /**
     * Enhance all stats of the equipment by 10%
     */
    public void enhance() {
        System.out.println("Enhanced " + name);
        // Base enhance, multiply all attributes by 1.1
        stats.replaceAll((key, value) -> (int)(value * 1.1));
    }


    /**
     * Enhance the equipment with provided levels, each level give a 5% increase in equipment stats
     *
     * @param level the level
     */
    public void enhance(int level) {
        System.out.println("Enhanced " + name + " to level " + level);
        // enhance using lvls
        double multiplier = 1.0 + (level * 0.05);
        stats.replaceAll((k, v) -> (int) (v * multiplier));
    }

    /**
     * Enhance the equipment with provided stat type and amount.
     * Provided stats must be one of the following: attack, defense, hp, speed, critical_rate, critical_damage
     * provided amount is in int, note that critical_rate, critical_damage are in %.
     *
     * @param stat   the stat to enhance
     * @param amount the amount to add on the stat provided
     */
    public void enhance(String stat, int amount) {
        if (stats.containsKey(stat)) {
            stats.put(stat, stats.get(stat) + amount);
            System.out.println(name + "'s " + stat + " increased by " + amount);
        }
    }

    /**
     * Gets the value of the provided stat name of the equipment
     *
     * @param statName the stat name to check
     * @return the stat value
     */
    public int getStat(String statName) {
        return stats.getOrDefault(statName, 0);
    }

    /**
     * Sets a stat name and value with provided name and value.
     * Provided stats must be one of the following: attack, defense, hp, speed, critical_rate, critical_damage
     * provided amount is in int, note that critical_rate, critical_damage are in %.
     *
     * @param statName the stat name
     * @param value    the value
     */
    public void setStat(String statName, int value) {
        if (stats.containsKey(statName)) {
            stats.put(statName, value);
            return;
        }
        System.out.println("Stat " + statName + " doesn't exist!");
    }

    /**
     * Use the equipment, prints out name, slot, and required level.
     */

    @Override
    public void use() {
        System.out.println("Used equipment: " + name);
        System.out.println("Slot: " + slot);
        System.out.println("Required Level: " + requiredLevel);
    }

    /**
     * Displays(prints) the information of the equipment.
     */
    @Override
    public void displayInfo() {
        System.out.println("=== Equipment Info ===");
        System.out.println("Name: " + name);
        System.out.println("Type: " + itemType);
        System.out.println("Slot: " + slot);
        System.out.println("Required Level: " + requiredLevel);
        System.out.println("Value: " + value);
        System.out.println("Stats:");
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Description: " + description);
    }

    /**
     * Output all the information of the equipment, including equipment stats.
     * @return the CSV format of all information about the equipment.
     */
    @Override
    public String toCSVFormat() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            if (sb.length() > 0) sb.append(";");
            sb.append(entry.getKey()).append(":").append(entry.getValue());
        }
        String var = "," + requiredLevel + "," + slot + ",\"" + sb.toString().replace("\"", "\"\"") + "\"";
        // expected String for var:   attack:100;defense:50;hp:200   ...etc
        return super.toCSVFormat() + var;
    }
}