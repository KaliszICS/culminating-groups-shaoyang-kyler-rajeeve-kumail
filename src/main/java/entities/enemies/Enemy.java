package entities.enemies;
import entities.abs.BattleUnit;
import entities.items.Item;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents everything that is an enemy, includes BossEnemy and RegularEnemy
 * Inherited from {@link BattleUnit} since enemies will be in a fight
 * Enemy attributes includes difficulty level, item drop rate possible drops
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 * @see BattleUnit
 * @see Item
 *
 */
public class Enemy extends BattleUnit implements Serializable {
    //added serializable, so now people can save characters to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    /**
     * The Difficulty level.
     */
    protected int difficultyLevel;
    /**
     * The Drop rate.
     */
    protected double dropRate;
    /**
     * The Possible drops.
     */
    protected Item[] possibleDrops;
    /**
     * The constant random will be used multiple times in this class
     */
    protected static final Random random = new Random();

    /**
     * Instantiates a new Enemy with no parameters, with default values
     * difficulty level: 1, item drop rate: 30%, have 4 things to drop
     */
    public Enemy() {
        super();
        this.difficultyLevel = 1;
        this.dropRate = 0.3; // 30% drop rate
        this.possibleDrops = new Item[3];
    }

    /**
     * Instantiates a new Enemy with provided name, and difficulty, enemy attributes adjusted by difficulty,
     * including drop rates
     *
     * @param name       the name of the enemy
     * @param difficulty the difficulty of the enemy
     */
    public Enemy(String name, int difficulty) {
        super(name, 50 + difficulty * 10, 5 + difficulty * 2);
        this.difficultyLevel = difficulty;
        this.dropRate = 0.1 + difficulty * 0.05; // higher the difficulty, higher the drop rate
        this.possibleDrops = new Item[3];
        // Adjust attributes using levels
        this.maxHP += difficulty * 10;
        this.currentHP = this.maxHP;
        this.attack += difficulty * 2;
        this.defense += difficulty;
    }

    /**
     * Gets difficulty level of the enemy
     *
     * @return the difficulty level of the enemy
     */
    public int getDifficultyLevel() { return difficultyLevel; }

    /**
     * Sets difficulty level of the enemy
     *
     * @param difficultyLevel the difficulty level of the enemy
     */
    public void setDifficultyLevel(int difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    /**
     * Gets drop rate of the enemy
     *
     * @return the drop rate of the enemy
     */
    public double getDropRate() { return dropRate; }

    /**
     * Sets item drop rate of the enemy
     *
     * @param dropRate the item drop rate of the enemy
     */
    public void setDropRate(double dropRate) { this.dropRate = dropRate; }

    /**
     * Get possible drops items in item array form.
     *
     * @return the array of items that could be dropped by the enemy
     */
    public Item[] getPossibleDrops() { return possibleDrops; }

    /**
     * Calculate which item is dropped by the enemy, calculation uses drop rate and array of possible drop item
     *
     * @return the item dropped by the enemy
     */
    public Item calculateDrop() {
        if (random.nextDouble() < dropRate) {
            if (possibleDrops.length > 0) {
                // Choose drops randomly
                Item drop = possibleDrops[random.nextInt(possibleDrops.length)];
                if (drop != null) {
                    System.out.println(name + " dropped: " + drop.getName());
                    return drop;
                }
            }
        }
        System.out.println(name + " didn't drop anything");
        return null;
    }

    /**
     * The enemy uses skill to attack player or enhance their attributes or heal themselves.
     * Subclasses in this package are overriding this method.
     */
    @Override
    public void useSkill() {
        System.out.println(name + " used enemy skill");
        // Randomly choose skill
        int skillType = random.nextInt(3);
        switch (skillType) {
            case 0:
                System.out.println("Use standard skill");
                break;
            case 1:
                System.out.println("Used defense skill");
                defense += 5;
                break;
            case 2:
                System.out.println("Used healing skill");
                heal(20);
                break;
        }
    }

    /**
     * Sets possible drops of the enemy
     *
     * @param drops the array of items that enemies could drop
     */
    public void setPossibleDrops(Item[] drops) {
        this.possibleDrops = drops;
    }

    /**
     * Add possible dropped item for an enemy, note that if the array is full, then the item will not be added to the list
     *
     * @param drop the dropped item
     */
    public void addPossibleDrop(Item drop) {
        for (int i = 0; i < possibleDrops.length; i++) {
            if (possibleDrops[i] == null) {
                possibleDrops[i] = drop;
                return;
            }
        }
        System.out.println("Drop list is full");
    }

    /**
     * Displays(prints) all information about an enemy
     */
    @Override
    public void displayInfo() {
        System.out.println("=== Enemy info ===");
        System.out.println("Name: " + name);
        System.out.println("Difficulty: " + difficultyLevel);
        System.out.println("Health: " + currentHP + "/" + maxHP);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Speed: " + speed);
        System.out.println("Drop rate: " + (dropRate * 100) + "%");
        System.out.println("Possible drops:");
        for (Item drop : possibleDrops) {
            if (drop != null) {
                System.out.println("  - " + drop.getName());
            }
        }
    }

    /**
     * Return all information about the enemy in CSV format.
     * @return CSV of all information about the enemy.
     */
    @Override
    public String toCSVFormat() {
        StringBuilder drops = new StringBuilder();
        for (Item drop : possibleDrops) {
            if (drop != null) {
                if (drops.length() > 0) drops.append(";");
                drops.append(drop.getName());
            }
        }
        String dropsCSV = drops.toString();
        String var = "," + difficultyLevel + "," + dropRate + ",\"" + dropsCSV.replace("\"", "\"\"") + "\"";
        return super.toCSVFormat() + var;
    }
}