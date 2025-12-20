package entities.equipment;

public class LightCone extends Equipment {
    private String pathAlignment; // 命途：巡猎、存护、毁灭等
    private String[] abilities;

    public LightCone() {
        super();
        this.pathAlignment = "巡猎";
        this.abilities = new String[2];
        this.abilities[0] = "基础攻击提升";
        this.abilities[1] = "速度提升";

        // 光锥特有属性
        setStat("attack", getStat("attack") + 20);
        setStat("speed", getStat("speed") + 10);
    }

    public LightCone(String name, String path) {
        super(name, "光锥");
        this.pathAlignment = path;
        this.abilities = new String[2];
        initializeAbilities(path);

        // 根据命途调整属性
        adjustStatsByPath(path);
    }

    private void initializeAbilities(String path) {
        switch (path) {
            case "巡猎":
                abilities[0] = "狩猎本能：暴击率提升";
                abilities[1] = "迅捷行动：速度大幅提升";
                break;
            case "存护":
                abilities[0] = "守护意志：防御力提升";
                abilities[1] = "坚不可摧：最大生命值提升";
                break;
            case "毁灭":
                abilities[0] = "破坏之力：攻击力提升";
                abilities[1] = "狂暴：暴击伤害提升";
                break;
            case "智识":
                abilities[0] = "智慧之光：效果命中提升";
                abilities[1] = "多重打击：追加攻击概率";
                break;
            case "同谐":
                abilities[0] = "和谐之音：效果抵抗提升";
                abilities[1] = "协同作战：团队增益效果";
                break;
            default:
                abilities[0] = "基础能力";
                abilities[1] = "特殊效果";
        }
    }

    private void adjustStatsByPath(String path) {
        switch (path) {
            case "巡猎":
                setStat("critical_rate", getStat("critical_rate") + 10);
                setStat("speed", getStat("speed") + 15);
                break;
            case "存护":
                setStat("defense", getStat("defense") + 30);
                setStat("hp", getStat("hp") + 100);
                break;
            case "毁灭":
                setStat("attack", getStat("attack") + 40);
                setStat("critical_damage", getStat("critical_damage") + 30);
                break;
            case "智识":
                setStat("attack", getStat("attack") + 25);
                setStat("effect_hit", 20); // 效果命中
                break;
            case "同谐":
                setStat("effect_res", 30); // 效果抵抗
                setStat("speed", getStat("speed") + 10);
                break;
        }
    }

    public void activateAbility() {
        System.out.println("激活光锥能力: " + name);
        System.out.println("命途: " + pathAlignment);

        for (String ability : abilities) {
            if (ability != null) {
                System.out.println("能力: " + ability);
            }
        }

        // 根据命途触发特殊效果
        if (pathAlignment.equals("巡猎") && Math.random() < 0.3) {
            System.out.println("触发狩猎本能：额外行动一次！");
        }
    }

    @Override
    public void calculateStats() {
        System.out.println("计算光锥属性...");

        // 基础属性计算
        int baseAttack = getStat("attack");
        int baseDefense = getStat("defense");

        // 根据等级调整
        double levelMultiplier = 1.0 + (requiredLevel * 0.02);

        setStat("attack", (int)(baseAttack * levelMultiplier));
        setStat("defense", (int)(baseDefense * levelMultiplier));

        System.out.println(name + " 最终属性:");
        System.out.println("攻击: " + getStat("attack"));
        System.out.println("防御: " + getStat("defense"));
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("类型: 光锥");
        System.out.println("命途: " + pathAlignment);
        System.out.println("能力:");
        for (String ability : abilities) {
            if (ability != null) {
                System.out.println("  - " + ability);
            }
        }
    }

    @Override
    public String toCSVFormat() {
        StringBuilder abilitiesStr = new StringBuilder();
        for (String ability : abilities) {
            if (ability != null) {
                if (abilitiesStr.length() > 0) abilitiesStr.append(";");
                abilitiesStr.append(ability);
            }
        }

        return super.toCSVFormat() + String.format(",光锥,%s,\"%s\"",
                pathAlignment, abilitiesStr.toString());
    }

    // Getter 和 Setter
    public String getPathAlignment() { return pathAlignment; }
    public void setPathAlignment(String pathAlignment) {
        this.pathAlignment = pathAlignment;
        initializeAbilities(pathAlignment);
        adjustStatsByPath(pathAlignment);
    }
    public String[] getAbilities() { return abilities; }
    public void setAbilities(String[] abilities) { this.abilities = abilities; }
}