package entities.items;
import entities.abs.GameEntity;
import entities.GachaPullable;

import java.io.Serializable;

/**
 * Represents the all items in game, all items have value (in credits), weight, whether it is stackable,
 * and the item type.
 * It is an abstract class therefore subclasses must implement this.
 * Inherited from {@link GameEntity} and implements {@link GachaPullable}
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 *
 * @see GameEntity
 * @see GachaPullable
 */
public abstract class Item extends GameEntity implements GachaPullable, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * The Value of the item in game credits
     */
    protected int value;
    /**
     * The Weight of the item
     */
    protected int weight;
    /**
     * Whether the item is stackable
     */
    protected boolean stackable;
    /**
     * The Item type.
     */
// added on update 25/12/21, new itemType Attribute
    protected String itemType;

    /**
     * Instantiates a new Item with no provided args and all default values.
     */
    public Item() {
        super();
        this.value = 10;
        this.weight = 1;
        this.stackable = true;
        this.itemType = "Standard Item";
    }

    /**
     * Instantiates a new Item with provided name and value
     *
     * @param name  the name of the item
     * @param value the value of the item
     */
    public Item(String name, int value) {
        super(name, nextId);
        this.value = value;
        this.weight = 1;
        this.stackable = true;
        this.itemType = "Standard Item";
    }

    /**
     * Instantiates a new Item with everything provided
     *
     * @param name      the name of the item
     * @param id        the id of the item
     * @param value     the value of the item
     * @param weight    the weight of the item
     * @param stackable whether the item is stackable
     * @param itemType  the item type
     */
    public Item(String name, int id, int value, int weight, boolean stackable, String itemType) {
        super(name, id);
        this.value = value;
        this.weight = weight;
        this.stackable = stackable;
        this.itemType = itemType;
    }


    /**
     * Gets value of the item
     *
     * @return the value of the item
     */
    public int getValue() { return value; }

    /**
     * Sets value of the item
     *
     * @param value the value of the item
     */
    public void setValue(int value) { this.value = value; }

    /**
     * Gets weight of the item
     *
     * @return the weight of the item
     */
    public int getWeight() { return weight; }

    /**
     * Sets weight of the item
     *
     * @param weight the weight of the item
     */
    public void setWeight(int weight) { this.weight = weight; }

    /**
     * Return whether the item is stackable
     *
     * @return whether the item is stackable
     */
    public boolean isStackable() { return stackable; }

    /**
     * Sets whether the item is stackable
     *
     * @param stackable whether the item is stackable
     */
    public void setStackable(boolean stackable) { this.stackable = stackable; }

    /**
     * Gets item type.
     *
     * @return the item type
     */
    public String getItemType() { return itemType; }

    /**
     * Sets item type.
     *
     * @param itemType the item type
     */
    public void setItemType(String itemType) { this.itemType = itemType; }

    /**
     * How to use the item.
     */
    public abstract void use();

    /**
     * Pull the item out.
     * @return the item pulled
     */
    @Override
    public Item pull() {
        System.out.println("Pulled item: " + name);
        return this;
    }

    /**
     * Gets drop rate of the item in game, this is adjusted by the value of the item
     * @return drop rate of the item
     */
    @Override
    public double getDropRate() {
        // base drop rate, adjusted by item value
        return 1.0 / (value / 10.0);
    }

    /**
     * Display(prints) all information about the item
     */
    @Override
    public void displayInfo() {
        System.out.println("=== Item Info ===");
        System.out.println("Item Name: " + name);
        System.out.println("Item Type: " + itemType);
        System.out.println("Item Value: " + value);
        System.out.println("Item Weight: " + weight);
        System.out.println("Stackable Status: " + stackable);
        System.out.println("Description: " + description);
    }

    /**
     * Return everything about the item in CSV format.
     * @return the CSV of all details of the item
     */
    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + "," + value + "," + weight + "," + stackable + "," + itemType + ",";
    }
}
