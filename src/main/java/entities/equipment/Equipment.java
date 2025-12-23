package entities.equipment;

import entities.items.Item;
import entities.characters.Character;

import java.util.HashMap;
import java.util.Map;

public abstract class Equipment extends Item {
    protected int requiredLevel;
    protected String slot;
    protected Map<String, Integer> stats;

    public Equipment() {
        super();
        this.requiredLevel = 1;
        this.slot = "Relic";
        this.stats = new HashMap<>();
        this.itemType = "Equipment";
        initializeStats();
    }

    public Equipment(String name, String slot) {
        super(name, 50); // base value = 50
        this.requiredLevel = 1;
        this.slot = slot;
        this.stats = new HashMap<>();
        this.itemType = "Equipment";
        initializeStats();
    }

    public Equipment(String name, String slot, int requiredLevel) {
        this(name, slot);
        this.requiredLevel = requiredLevel;
    }
    public int getRequiredLevel() { return requiredLevel; }
    public void setRequiredLevel(int requiredLevel) { this.requiredLevel = requiredLevel; }
    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }
    public Map<String, Integer> getStats() { return stats; }
    public void setStats(Map<String, Integer> stats) { this.stats = stats; }

    private void initializeStats() {
        // base attributes
        stats.put("attack", 10);
        stats.put("defense", 5);
        stats.put("hp", 50);
        stats.put("speed", 5);
        stats.put("critical_rate", 5); // rate %
        stats.put("critical_damage", 50); // damage %
    }

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

    public void unequip() {
        System.out.println("Unequipped " + name);
    }

    public abstract void calculateStats();

    public void enhance() {
        System.out.println("Enhanced " + name);
        // Base enhance, multiply all attributes by 1.1
        stats.replaceAll((key, value) -> (int)(value * 1.1));
    }
    

    public void enhance(int level) {
        System.out.println("Enhanced " + name + " to level " + level);
        // enhance using lvls
        double multiplier = 1.0 + (level * 0.05);
        stats.replaceAll((k, v) -> (int) (v * multiplier));
    }

    public void enhance(String stat, int amount) {
        if (stats.containsKey(stat)) {
            stats.put(stat, stats.get(stat) + amount);
            System.out.println(name + "'s " + stat + " increased by " + amount);
        }
    }

    public int getStat(String statName) {
        return stats.getOrDefault(statName, 0);
    }

    public void setStat(String statName, int value) {
        stats.put(statName, value);
    }

    @Override
    public void use() {
        System.out.println("Used equipment: " + name);
        System.out.println("Slot: " + slot);
        System.out.println("Required Level: " + requiredLevel);
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Equipment Info ===");
        System.out.println("Name: " + name);
        System.out.println("Type: " + itemType);
        System.out.println("Slot: " + slot);
        System.out.println("Required Level: " + requiredLevel);
        System.out.println("Value: " + value);
        System.out.println("Attributes:");
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Description: " + description);
    }

    @Override
    public String toCSVFormat() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            if (sb.length() > 0) sb.append(";");
            sb.append(entry.getKey()).append(":").append(entry.getValue());
        }
        String var = "," + requiredLevel + "," + slot + ",\"" + sb.toString().replace("\"", "\"\"") + "\"";
        // expected String for var:   Attack:100;Defense:50;HP:200   ...etc

        return super.toCSVFormat() + var;
    }
}