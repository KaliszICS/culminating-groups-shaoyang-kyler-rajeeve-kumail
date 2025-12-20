package entities.items;

public class MaterialItem extends Item {
    private String materialType;
    private int rarity; // 1-5星，5星最稀有

    public MaterialItem() {
        super();
        this.materialType = "普通材料";
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

    private int determineRarity(String type) {
        switch (type.toLowerCase()) {
            case "普通材料": return 1;
            case "稀有材料": return 3;
            case "史诗材料": return 4;
            case "传说材料": return 5;
            case "突破材料": return 4;
            case "技能材料": return 3;
            default: return 1;
        }
    }

    public MaterialItem combine(MaterialItem other) {
        if (this.materialType.equals(other.materialType) &&
                this.rarity == other.rarity) {

            // 相同类型和稀有度的材料可以合成
            String newName = this.name + "合成体";
            int newValue = this.value + other.value;
            int newRarity = this.rarity;

            // 有概率提升稀有度
            if (Math.random() < 0.1) { // 10%概率提升稀有度
                newRarity = Math.min(5, this.rarity + 1);
                newName = "强化" + newName;
            }

            MaterialItem combined = new MaterialItem(newName, this.materialType, newRarity, newValue);
            System.out.println("成功合成材料: " + combined.getName());
            System.out.println("稀有度: " + combined.getRarityLevel());

            return combined;
        } else {
            System.out.println("无法合成不同类型的材料");
            return null;
        }
    }

    public String getRarityLevel() {
        switch (rarity) {
            case 1: return "一星";
            case 2: return "二星";
            case 3: return "三星";
            case 4: return "四星";
            case 5: return "五星";
            default: return "普通";
        }
    }

    @Override
    public void use() {
        System.out.println("使用材料: " + name);
        System.out.println("材料类型: " + materialType);
        System.out.println("稀有度: " + getRarityLevel());
        System.out.println("可用于合成或强化");
    }

    @Override
    public double getDropRate() {
        // 根据稀有度调整掉落率
        switch (rarity) {
            case 1: return 0.5;   // 50%
            case 2: return 0.3;   // 30%
            case 3: return 0.15;  // 15%
            case 4: return 0.04;  // 4%
            case 5: return 0.01;  // 1%
            default: return 0.1;
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("类型: 材料");
        System.out.println("材料类别: " + materialType);
        System.out.println("稀有度: " + getRarityLevel() + " (" + rarity + "星)");
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",材料,%s,%d", materialType, rarity);
    }

    // Getter 和 Setter
    public String getMaterialType() { return materialType; }
    public void setMaterialType(String materialType) { this.materialType = materialType; }
    public int getRarity() { return rarity; }
    public void setRarity(int rarity) { this.rarity = rarity; }
}