package entities.equipment;

import entities.characters.Character;

public class Relic extends Equipment {
    private String setBonus;
    private int setPiecesRequired;
    private String relicSet; // relic set name
    public Relic() {
        super();
        this.relicSet = "Explorer";
        this.setBonus = "Set of 2：Increase HP by 12%";
        this.setPiecesRequired = 2;
        this.slot = "Relic";
        initializeRelicStats();
    }

    public Relic(String name, String set) {
        super(name, "Relic");
        this.relicSet = set;
        this.setBonus = getSetBonus(set);
        this.setPiecesRequired = 2;
        initializeRelicStats();
        adjustStatsBySet(set);
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

    private void initializeRelicStats() {
        // relic attributes
        setStat("hp_percent", 0); // hp
        setStat("attack_percent", 0); // attack
        setStat("defense_percent", 0); // defense
        // Slot to set type of attribute bonus
        switch (slot) {
            case "Head":
                setStat("hp", 1000);
                break;
            case "Arm":
                setStat("attack", 100);
                break;
            case "Body":
                setStat("defense", 100);
                break;
            case "Leg":
                setStat("speed", 10);
                break;
        }
    }

    private void adjustStatsBySet(String set) {
        switch (set) {
            case "Explorer":
                setStat("hp", getStat("hp") + 200);
                setStat("defense", getStat("defense") + 50);
                break;
            case "Quick Gunner":
                setStat("attack", getStat("attack") + 80);
                setStat("speed", getStat("speed") + 5);
                break;
            case "Paladin":
                setStat("defense", getStat("defense") + 100);
                setStat("effect_res", 20);
                break;
            case "Genius":
                setStat("attack", getStat("attack") + 60);
                setStat("effect_hit", 15);
                break;
        }
    }

    private String getSetBonus(String set) {
        switch (set) {
            case "Explorer":
                return "Set of 2: Increase HP by 12%";
            case "Quick Gunner":
                return "Set of 2: Increase attack by 12%\nSet of 4: Increase speed by 6%";
            case "Paladin":
                return "Set of 2: Increase defense by 15%\nSet of 4: Shield effect increase by 20%";
            case "Genius":
                return "Set of 2: Increase Quantum damage by 10%\nSet of 4: Ignore 10% of the enemy's defense";
            default:
                return "Set of 2: Increase attack by 10%";
        }
    }

    public void checkSetBonus(Character character) {
        System.out.println("Check for set bonus: " + relicSet);
        // check for pieces
        int equippedPieces = 2;
        if (equippedPieces >= setPiecesRequired) {
            System.out.println("Activate set bonus: " + setBonus);
            // apply bonus
            applySetBonus(character, equippedPieces);
        }
    }

    public void checkSetBonus(int pieces) {
        if (pieces >= setPiecesRequired) {
            System.out.println("Activated " + relicSet + " " + pieces + " bonus");
            System.out.println(setBonus);
        } else {
            System.out.println("Need " + setPiecesRequired + " to activate bonus, current pieces: " + pieces);
        }
    }

    private void applySetBonus(Character character, int pieces) {
        if (relicSet.equals("Fast Gunner")) {
            if (pieces >= 2) {
                character.setAttack(character.getAttack() + (int)(character.getAttack() * 0.12));
                System.out.println("Activated Set of 2: Increase attack by 12%");
            }
            if (pieces >= 4) {
                character.setSpeed(character.getSpeed() + 6);
                System.out.println("Activated Set of 4: Increase speed by 6%");
            }
        }
    }

    @Override
    public void calculateStats() {
        System.out.println("Calculating relic stats");
        // base attributes
        int mainStat = 0;
        switch (slot) {
            case "Head":
                mainStat = getStat("hp");
                setStat("hp", (int)(mainStat * (1.0 + requiredLevel * 0.05)));
                break;
            case "Arm":
                mainStat = getStat("attack");
                setStat("attack", (int)(mainStat * (1.0 + requiredLevel * 0.05)));
                break;
            case "Body":
                mainStat = getStat("defense");
                setStat("defense", (int)(mainStat * (1.0 + requiredLevel * 0.05)));
                break;
            case "Leg":
                mainStat = getStat("speed");
                setStat("speed", (int)(mainStat * (1.0 + requiredLevel * 0.02)));
                break;
        }
        // random sub attributes
        if (Math.random() < 0.5) {
            int substatValue = requiredLevel * 2;
            setStat("critical_rate", getStat("critical_rate") + substatValue);
            System.out.println("Gain secondary attribute: Critical Hit Rate + " + substatValue);
        }

        System.out.println(name + " Final Stats: ");
        System.out.println("Main: " + getMainStatValue());
    }

    private String getMainStatValue() {
        switch (slot) {
            case "Head": return "HP: " + getStat("hp");
            case "Arm": return "Attack: " + getStat("attack");
            case "Body": return "Defense: " + getStat("defense");
            case "Leg": return "Speed: " + getStat("speed");
            default: return "Unknown";
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: relic");
        System.out.println("Set: " + relicSet);
        System.out.println("Slot: " + slot);
        System.out.println("Set Bonus: " + setBonus);
        System.out.println("Pieces Required: " + setPiecesRequired);
    }

    @Override
    public String toCSVFormat() {
        String str = ",Relic," + relicSet + "," + setBonus + "," + setPiecesRequired;
        return super.toCSVFormat() + str;
    }

}