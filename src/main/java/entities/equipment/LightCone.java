package entities.equipment;

import java.io.Serializable;

/**
 * The type Light cone.
 */
public class LightCone extends Equipment implements Serializable {
    //added serializable, so now people can save items to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private String pathAlignment; // paths
    private String[] abilities;

    /**
     * Instantiates a new Light cone.
     */
// as default, when not prompted, generate a default lightCone with path The Hunt
    public LightCone() {
        super();
        this.pathAlignment = "The Hunt";
        this.abilities = new String[2];
        this.abilities[0] = "Increase attack";
        this.abilities[1] = "Increase speed";
        // Lightcone Special Stats / attributes / effects
        setStat("attack", getStat("attack") + 20);
        setStat("speed", getStat("speed") + 10);
    }

    /**
     * Instantiates a new Light cone.
     *
     * @param name the name
     * @param path the path
     */
    public LightCone(String name, String path) {
        super(name, "LightCone");
        this.pathAlignment = path;
        this.abilities = new String[2];
        initializeAbilities(path);
        adjustStatsByPath(path);
    }

    /**
     * Gets path alignment.
     *
     * @return the path alignment
     */
    public String getPathAlignment() { return pathAlignment; }

    /**
     * Sets path alignment.
     *
     * @param pathAlignment the path alignment
     */
    public void setPathAlignment(String pathAlignment) {
        this.pathAlignment = pathAlignment;
        initializeAbilities(pathAlignment);
        adjustStatsByPath(pathAlignment);
    }

    /**
     * Get abilities string [ ].
     *
     * @return the string [ ]
     */
    public String[] getAbilities() { return abilities; }

    /**
     * Sets abilities.
     *
     * @param abilities the abilities
     */
    public void setAbilities(String[] abilities) { this.abilities = abilities; }

    private void initializeAbilities(String path) {
        switch (path) {
            case "The Hunt":
                abilities[0] = "Hunting Instinct: Increases critical hit rate";
                abilities[1] = "Fast Moves: Increases speed significantly";
                break;
            case "Preservation":
                abilities[0] = "Guardian Will: Increases defense.";
                abilities[1] = "Unbreakable: Increase health";
                break;
            case "Destruction":
                abilities[0] = "Power of Destruction: Increase attack";
                abilities[1] = "Berserk: Increases critical damage rate";
                break;
            case "Erudition":
                abilities[0] = "Light of Wisdom: Increases effect hit rate";
                abilities[1] = "Multiple Strikes: Increases the probability of additional attacks.";
                break;
            case "Harmony":
                abilities[0] = "Harmonious Sounds: Increases effect resistance";
                abilities[1] = "Cooperative Combat: Team Buffs";
                break;
            default:
                abilities[0] = "Basic Skill";
                abilities[1] = "Special Skill";
        }
    }

    private void adjustStatsByPath(String path) {
        switch (path) {
            case "The Hunt":
                setStat("critical_rate", getStat("critical_rate") + 10);
                setStat("speed", getStat("speed") + 15);
                break;
            case "Preservation":
                setStat("defense", getStat("defense") + 30);
                setStat("hp", getStat("hp") + 100);
                break;
            case "Destruction":
                setStat("attack", getStat("attack") + 40);
                setStat("critical_damage", getStat("critical_damage") + 30);
                break;
            case "Erudition":
                setStat("attack", getStat("attack") + 25);
                setStat("effect_hit", 20); // 效果命中
                break;
            case "Harmony":
                setStat("effect_res", 30); // 效果抵抗
                setStat("speed", getStat("speed") + 10);
                break;
        }
    }

    /**
     * Activate ability.
     */
    public void activateAbility() {
        System.out.println("Activated LightCone Ability: " + name);
        System.out.println("Path: " + pathAlignment);

        for (int i = 0; i < abilities.length; i++) {
            String ability = abilities[i];
            if (ability != null) {
                System.out.println("Ability: " + ability);
            }
        }
        // The Hunt special skills
        if (pathAlignment.equals("The Hunt") && Math.random() < 0.3) {
            System.out.println("Triggered The Hunt Instinct: Extra Move!");
        }
    }

    @Override
    public void calculateStats() {
        System.out.println("Calculating LightCone Stats...");
        // base calculation
        int baseAttack = getStat("attack");
        int baseDefense = getStat("defense");
        // lvls adjust
        double levelMultiplier = 1.0 + (requiredLevel * 0.02);
        setStat("attack", (int)(baseAttack * levelMultiplier));
        setStat("defense", (int)(baseDefense * levelMultiplier));
        System.out.println(name + " Final Stats:");
        System.out.println("Attack: " + getStat("attack"));
        System.out.println("Defense: " + getStat("defense"));
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: LightCone");
        System.out.println("Path: " + pathAlignment);
        System.out.println("Skill: ");
        for (int i = 0; i < abilities.length; i++) {
            if (abilities[i] != null) {
                System.out.println("  - " + abilities[i]);
            }
        }
    }

    @Override
    public String toCSVFormat() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < abilities.length; i++) {
            String ability = abilities[i];
            if (ability != null) {
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(ability);
            }
        }
        // example output: ,LightCone,The Hunt,"Hunting Instinct: Increases critical hit rate;Fast Moves: Increases speed significantly"
        String var = ",LightCone," + pathAlignment + ",\"" + sb.toString() + "\"";
        return super.toCSVFormat() + var;
    }
}