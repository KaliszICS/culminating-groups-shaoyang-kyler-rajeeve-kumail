package entities.items;

import java.io.Serializable;

/**
 * Represents a type consumable item, usually will be a potion or effect.
 * Consumable items have 2 parameters, one is uses remaining, one is effect duration.
 * Effect type is decided by the name of this item.
 * Inherited from {@link Item}, so it will share properties with Item
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 *
 * @see Item
 */
public class ConsumableItem extends Item implements Serializable {
    //added serializable, so now people can save items to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private int usesRemaining;
    private int effectDuration;

    /**
     * Instantiates a new Consumable item, with all default parameters.
     */
    public ConsumableItem() {
        super();
        this.usesRemaining = 3;
        this.effectDuration = 30; // 30s duration
    }

    /**
     * Instantiates a new Consumable item with name and uses provided.
     * Value of the consumable item is set to 50 credits, and duration is set to 30s.
     *
     * @param name the name of the consumable item
     * @param uses the amount of possible uses of this item
     */
    public ConsumableItem(String name, int uses) {
        super(name, 50); // base value 50
        this.usesRemaining = uses;
        this.effectDuration = 30;
    }

    /**
     * Instantiates a new Consumable item with all provided parameters
     *
     * @param name the name of the consumable item
     * @param uses the amount of possible uses of this item
     * @param duration the duration of this consumable item in seconds
     * @param value    the value of this item, in game credits
     */
    public ConsumableItem(String name, int uses, int duration, int value) {
        super(name, value);
        this.usesRemaining = uses;
        this.effectDuration = duration;
    }

    /**
     * Gets uses remaining of this item
     *
     * @return the uses remaining
     */
    public int getUsesRemaining() { return usesRemaining; }

    /**
     * Sets uses remaining of this item
     *
     * @param usesRemaining the uses remaining
     */
    public void setUsesRemaining(int usesRemaining) { this.usesRemaining = usesRemaining; }

    /**
     * Gets effect duration of this item
     *
     * @return the effect duration
     */
    public int getEffectDuration() { return effectDuration; }

    /**
     * Sets effect duration of this item
     *
     * @param effectDuration the effect duration
     */
    public void setEffectDuration(int effectDuration) { this.effectDuration = effectDuration; }

    /**
     * Uses the item, if item uses is less than 1, then item cannot be used.
     *
     */
    @Override
    public void use() {
        if (usesRemaining <= 0) {
            System.out.println(name + " is used up!");
            return;
        }
        usesRemaining--;
        System.out.println("Used " + name);
        System.out.println("Effect Duration: " + effectDuration + " seconds");
        System.out.println("Uses remaining: " + usesRemaining);
        // different effects
        if (name.contains("Heal")) {
            System.out.println("Heal HP");
        } else if (name.contains("Attack")) {
            System.out.println("Increase Attack!");
        } else if (name.contains("Defense")) {
            System.out.println("Increase Defense!");
        } else if (name.contains("Speed")) {
            System.out.println("Increase Speed!");
        }
    }

    /**
     * Refill consumable item by 3
     */
    public void refill() {
        refill(3); // default, if no args is provided for refill
    }

    /**
     * Refill a consumable item with provided amount
     *
     * @param amount the amount to refill
     */
    public void refill(int amount) {
        usesRemaining += amount;
        System.out.println(name + " refilled " + amount + " uses");
        System.out.println("Current uses remaining " + usesRemaining);
    }

    /**
     * Increase duration of the potion
     *
     * @param seconds the seconds to increase.
     */
    public void increaseDuration(int seconds) {
        effectDuration += seconds;
        System.out.println(name + " increased effect duration by " + seconds + " seconds");
        System.out.println("Total Duration: " + effectDuration + " seconds");
    }

    /**
     * Display(prints) all information about this consumable item
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: ConsumableItem");
        System.out.println("Uses Remaining " + usesRemaining);
        System.out.println("Effect Duration " + effectDuration + " Seconds");
    }

    /**
     * Builds a CSV for all information about this item, it also calls the superclass toCSVFormat method
     * @return the CSV for all information about this item
     */
    @Override
    public String toCSVFormat() {
        String str = ",ConsumableItem," + usesRemaining + "," + effectDuration;
        return super.toCSVFormat() + str;
    }
}
