package entities.items;
import entities.abs.GameEntity;
import entities.GachaPullable;

public abstract class Item extends GameEntity implements GachaPullable {
    protected int value;
    protected int weight;
    protected boolean stackable;
    // added on update 25/12/21, new itemType Attribute
    protected String itemType;
    public Item() {
        super();
        this.value = 10;
        this.weight = 1;
        this.stackable = true;
        this.itemType = "Standard Item";
    }

    public Item(String name, int value) {
        super(name, nextId);
        this.value = value;
        this.weight = 1;
        this.stackable = true;
        this.itemType = "Standard Item";
    }

    public Item(String name, int id, int value, int weight, boolean stackable, String itemType) {
        super(name, id);
        this.value = value;
        this.weight = weight;
        this.stackable = stackable;
        this.itemType = itemType;
    }


    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public boolean isStackable() { return stackable; }
    public void setStackable(boolean stackable) { this.stackable = stackable; }
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public abstract void use();

    @Override
    public Item pull() {
        System.out.println("Pulled item: " + name);
        return this;
    }

    @Override
    public double getDropRate() {
        // base drop rate, adjusted by item value
        return 1.0 / (value / 10.0);
    }

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

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",%d,%d,%b,%s",
                value, weight, stackable, itemType);
    }
}