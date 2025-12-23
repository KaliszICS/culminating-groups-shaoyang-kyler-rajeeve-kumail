package entities.items;

public class MaterialItem extends Item {
    private String materialType;
    private int rarity; // 1-5

    public MaterialItem() {
        super();
        this.materialType = "Regular Material";
        this.rarity = 1;
        this.stackable = true;
    }

    public MaterialItem(String name, String type) {
        super(name, 10);
        this.materialType = type;
        this.rarity = determineRarity(type);
        this.stackable = true;
    }

    public MaterialItem(String name, String type, int rarity, int value) {
        super(name, value);
        this.materialType = type;
        this.rarity = rarity;
        this.stackable = true;
    }


    public String getMaterialType() { return materialType; }
    public void setMaterialType(String materialType) { this.materialType = materialType; }
    public int getRarity() { return rarity; }
    public void setRarity(int rarity) { this.rarity = rarity; }

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

    @Override
    public void use() {
        System.out.println("Use Material: " + name);
        System.out.println("Material Type: " + materialType);
        System.out.println("Rarity: " + getRarityLevel());
        System.out.println("Can be combined and upgrades with same rarity and type material");
    }

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

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: Material");
        System.out.println("Material Type: " + materialType);
        System.out.println("Rarity: " + getRarityLevel() + " (" + rarity + "★)");
    }

    @Override
    public String toCSVFormat() {
        String str = ",Material," + materialType + "," + rarity;
        return super.toCSVFormat() + str;
    }
}