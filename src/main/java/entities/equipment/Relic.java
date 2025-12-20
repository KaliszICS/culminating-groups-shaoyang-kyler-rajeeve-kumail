package entities.equipment;

import entities.characters.Character;

public class Relic extends Equipment {
    private String setBonus;
    private int setPiecesRequired;
    private String relicSet; // 遗器套装名

    public Relic() {
        super();
        this.relicSet = "冒险家";
        this.setBonus = "2件套：生命值+12%";
        this.setPiecesRequired = 2;
        this.slot = "遗器";

        // 遗器基础属性
        initializeRelicStats();
    }

    public Relic(String name, String set) {
        super(name, "遗器");
        this.relicSet = set;
        this.setBonus = getSetBonus(set);
        this.setPiecesRequired = 2;

        // 根据套装初始化属性
        initializeRelicStats();
        adjustStatsBySet(set);
    }

    private void initializeRelicStats() {
        // 遗器特有属性
        setStat("hp_percent", 0); // 生命值百分比
        setStat("attack_percent", 0); // 攻击力百分比
        setStat("defense_percent", 0); // 防御力百分比

        // 根据槽位设置主属性
        switch (slot) {
            case "头部":
                setStat("hp", 1000);
                break;
            case "手部":
                setStat("attack", 100);
                break;
            case "身体":
                setStat("defense", 100);
                break;
            case "脚部":
                setStat("speed", 10);
                break;
        }
    }

    private void adjustStatsBySet(String set) {
        switch (set) {
            case "冒险家":
                setStat("hp", getStat("hp") + 200);
                setStat("defense", getStat("defense") + 50);
                break;
            case "快枪手":
                setStat("attack", getStat("attack") + 80);
                setStat("speed", getStat("speed") + 5);
                break;
            case "圣骑士":
                setStat("defense", getStat("defense") + 100);
                setStat("effect_res", 20);
                break;
            case "天才":
                setStat("attack", getStat("attack") + 60);
                setStat("effect_hit", 15);
                break;
        }
    }

    private String getSetBonus(String set) {
        switch (set) {
            case "冒险家":
                return "2件套：生命值+12%";
            case "快枪手":
                return "2件套：攻击力+12%\n4件套：速度+6%";
            case "圣骑士":
                return "2件套：防御力+15%\n4件套：护盾效果+20%";
            case "天才":
                return "2件套：量子属性伤害+10%\n4件套：无视敌人10%防御";
            default:
                return "2件套：攻击力+10%";
        }
    }

    public void checkSetBonus(Character character) {
        System.out.println("检查遗器套装效果: " + relicSet);

        // 在实际游戏中，这里会检查角色装备的同套装遗器数量
        int equippedPieces = 2; // 假设装备了2件

        if (equippedPieces >= setPiecesRequired) {
            System.out.println("激活套装效果: " + setBonus);

            // 应用套装效果
            applySetBonus(character, equippedPieces);
        }
    }

    public void checkSetBonus(int pieces) {
        if (pieces >= setPiecesRequired) {
            System.out.println("激活 " + relicSet + " " + pieces + "件套效果");
            System.out.println(setBonus);
        } else {
            System.out.println("需要 " + setPiecesRequired + " 件套来激活效果，当前 " + pieces + " 件");
        }
    }

    private void applySetBonus(Character character, int pieces) {
        if (relicSet.equals("快枪手")) {
            if (pieces >= 2) {
                character.setAttack(character.getAttack() + (int)(character.getAttack() * 0.12));
                System.out.println("激活2件套：攻击力+12%");
            }
            if (pieces >= 4) {
                character.setSpeed(character.getSpeed() + 6);
                System.out.println("激活4件套：速度+6");
            }
        }
    }

    @Override
    public void calculateStats() {
        System.out.println("计算遗器属性...");

        // 基础属性
        int mainStat = 0;
        switch (slot) {
            case "头部":
                mainStat = getStat("hp");
                setStat("hp", (int)(mainStat * (1.0 + requiredLevel * 0.05)));
                break;
            case "手部":
                mainStat = getStat("attack");
                setStat("attack", (int)(mainStat * (1.0 + requiredLevel * 0.05)));
                break;
            case "身体":
                mainStat = getStat("defense");
                setStat("defense", (int)(mainStat * (1.0 + requiredLevel * 0.05)));
                break;
            case "脚部":
                mainStat = getStat("speed");
                setStat("speed", (int)(mainStat * (1.0 + requiredLevel * 0.02)));
                break;
        }

        // 副属性（随机）
        if (Math.random() < 0.5) {
            int substatValue = requiredLevel * 2;
            setStat("critical_rate", getStat("critical_rate") + substatValue);
            System.out.println("获得副属性：暴击率+" + substatValue);
        }

        System.out.println(name + " 最终属性:");
        System.out.println("主属性: " + getMainStatValue());
    }

    private String getMainStatValue() {
        switch (slot) {
            case "头部": return "生命值: " + getStat("hp");
            case "手部": return "攻击力: " + getStat("attack");
            case "身体": return "防御力: " + getStat("defense");
            case "脚部": return "速度: " + getStat("speed");
            default: return "未知";
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("类型: 遗器");
        System.out.println("套装: " + relicSet);
        System.out.println("槽位: " + slot);
        System.out.println("套装效果: " + setBonus);
        System.out.println("需求件数: " + setPiecesRequired);
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",遗器,%s,\"%s\",%d",
                relicSet, setBonus, setPiecesRequired);
    }

    // Getter 和 Setter
    public String getSetBonus() { return setBonus; }
    public void setSetBonus(String setBonus) { this.setBonus = setBonus; }
    public int getSetPiecesRequired() { return setPiecesRequired; }
    public void setSetPiecesRequired(int setPiecesRequired) { this.setPiecesRequired = setPiecesRequired; }
    public String getRelicSet() { return relicSet; }
    public void setRelicSet(String relicSet) {
        this.relicSet = relicSet;
        this.setBonus = getSetBonus(relicSet);
        adjustStatsBySet(relicSet);
    }
}