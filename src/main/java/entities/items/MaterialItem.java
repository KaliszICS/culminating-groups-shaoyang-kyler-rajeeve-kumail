package entities.items;

import java.io.Serializable;

/**
 * Represents all material items in game.
 * Inherited from {@link Item} therefore they have shared attributes.
 * Unique attributes are material type and rarity (1-5)
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 * 
 * @see Item
 */
public class MaterialItem extends Item implements Serializable {
    //added serializable, so now people can save items to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private String materialType;
    private int rarity; // 1-5

    /**
     * Instantiates a new Material item with no provided args and all default values.
     */
    public MaterialItem() {
        super();
        this.materialType = "Regular Material";
        this.rarity = 1;
        this.stackable = true;
    }

    /**
     * Instantiates a new Material item with provided name and material type
     *
     * @param name the name of the material item
     * @param type the type of the material item
     */
    public MaterialItem(String name, String type) {
        super(name, 10);
        this.materialType = type;
        this.rarity = determineRarity(type);
        this.stackable = true;
    }

    /**
     * Instantiates a new Material item with provided name, type, rarity and value
     *
     * @param name   the name of the material item
     * @param type   the type of the material item
     * @param rarity the rarity of the material item
     * @param value  the value of the material item
     */
    public MaterialItem(String name, String type, int rarity, int value) {
        super(name, value);
        this.materialType = type;
        this.rarity = rarity;
        this.stackable = true;
    }


    /**
     * Gets material type of the material item
     *
     * @return the material type of the material item
     */
    public String getMaterialType() { return materialType; }

    /**
     * Sets material type of the material item
     *
     * @param materialType the material type of the material item
     */
    public void setMaterialType(String materialType) { this.materialType = materialType; }

    /**
     * Gets rarity of the material item
     *
     * @return the rarity of the material item
     */
    public int getRarity() { return rarity; }

    /**
     * Sets rarity of the material item
     *
     * @param rarity the of the material item
     */ 
    public void setRarity(int rarity) { this.rarity = rarity; }

    /**
     * Determine rarity of the item based on material type
     * @param type type of material item
     * @return rarity of the material item
     */

    private int determineRarity(String type) {
        switch (type.toLowerCase()) {
            case "regular material": return 1;
            case "rare material": return 3;
            case "epic material": return 4;
            case "legend material": return 5;
            case "breakthrough material": return 4;
            case "skill material": return 3;
            default: return 1;
        }
    }

    /**
     * Combine material item with another material item
     * only same type and rarity material items can be combined.
     * Also, there will be a 10% chance of upgrade rarity of the item
     *
     * @param other the other material item
     * @return the material item combined
     */
    public MaterialItem combine(MaterialItem other) {
        if (this.materialType.equals(other.materialType) &&
                this.rarity == other.rarity) {
            // same type and rarity can be combined
            String newName = this.name + " combine";
            int newValue = this.value + other.value;
            int newRarity = this.rarity;

            // increase rarity
            if (Math.random() < 0.1) { // 10% chance
                newRarity = Math.min(5, this.rarity + 1);
                newName = "Upgraded" + newName;
            }

            MaterialItem combined = new MaterialItem(newName, this.materialType, newRarity, newValue);
            System.out.println("Successfully combined to: " + combined.getName());
            System.out.println("Rarity " + combined.getRarityLevel());

            return combined;
        } else {
            System.out.println("Cannot combine materials with different types and rarity");
            return null;
        }
    }

    /**
     * Gets rarity level of the material item.
     *
     * @return the rarity level with stars
     */
    public String getRarityLevel() {
        switch (rarity) {
            case 1: return "1★";
            case 2: return "2★";
            case 3: return "3★";
            case 4: return "4★";
            case 5: return "5★";
            default: return "1★";
        }
    }

    /**
     * Use the material item.
     */
    @Override
    public void use() {
        System.out.println("Use Material: " + name);
        System.out.println("Material Type: " + materialType);
        System.out.println("Rarity: " + getRarityLevel());
        System.out.println("Can be combined and upgrades with same rarity and type material");
    }

    /**
     * Get drop rate of the item based on their rarity
     * @return the drop rate
     */
    @Override
    public double getDropRate() {
        // adjust droprate by rarity
        switch (rarity) {
            case 1: return 0.5;   // 50%
            case 2: return 0.25;  // 25%
            case 3: return 0.15;  // 15%
            case 4: return 0.04;  // 4%
            case 5: return 0.01;  // 1%
            default: return 0.1;
        }
    }

    /**
     * Displays(prints) all information about the material item
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: Material");
        System.out.println("Material Type: " + materialType);
        System.out.println("Rarity: " + " (" + getRarityLevel() + ")");
    }

    /**
     * Return all information about this material item in a CSV format
     * @return CSV format of all information about this material item
     */
    @Override
    public String toCSVFormat() {
        String str = ",Material," + materialType + "," + rarity;
        return super.toCSVFormat() + str;
    }
}
