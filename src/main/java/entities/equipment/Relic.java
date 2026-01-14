package entities.equipment;

import entities.characters.Character;

import java.io.Serializable;

/**
 * Represents the equipment type Relic,
 * Relics have extra attributes compared to equipment: setBonus, setPiecesRequired, relicSet.
 * Sets of relic equipment will bring up extra bonuses to the character.
 * Inherited from {@link Equipment} which means they will have shared attributes.
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 *
 * @see Equipment
 * @see Character
 */
public class Relic extends Equipment implements Serializable {
    //added serializable, so now people can save items to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private String setBonus;
    private int setPiecesRequired;
    private String relicSet; // relic set name

    /**
     * Instantiates a new Relic with default values (no args provided).
     */
    public Relic() {
        super();
        this.relicSet = "Explorer";
        this.setBonus = "Set of 2：Increase HP by 12%";
        this.setPiecesRequired = 2;
        this.slot = "Relic";
        initializeRelicStats();
    }

    /**
     * Instantiates a new Relic with provided name and set
     *
     * @param name the name of the relic
     * @param set  the set of the relic
     */
    public Relic(String name, String set) {
        super(name, "Relic");
        this.relicSet = set;
        this.setBonus = getSetBonus(set);
        this.setPiecesRequired = 2;
        initializeRelicStats();
        adjustStatsBySet(set);
    }

    /**
     * Gets set bonus for the set
     *
     * @return the set bonus for the relic
     */

    public String getSetBonus() { return setBonus; }

    /**
     * Sets set bonus for the relic
     *
     * @param setBonus the set bonus for the relic
     */
    public void setSetBonus(String setBonus) { this.setBonus = setBonus; }

    /**
     * Gets set pieces required for the relic
     *
     * @return the set pieces required for the relic
     */
    public int getSetPiecesRequired() { return setPiecesRequired; }

    /**
     * Sets set pieces required for the relic
     *
     * @param setPiecesRequired the set pieces required for the relic
     */
    public void setSetPiecesRequired(int setPiecesRequired) { this.setPiecesRequired = setPiecesRequired; }

    /**
     * Gets relic set.
     *
     * @return the relic set
     */
    public String getRelicSet() { return relicSet; }

    /**
     * Sets relic set.
     *
     * @param relicSet the relic set
     */
    public void setRelicSet(String relicSet) {
        this.relicSet = relicSet;
        this.setBonus = getSetBonus(relicSet);
        adjustStatsBySet(relicSet);
    }

    /**
     * Initialize relic stats by different slots.
     */
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

    /**
     * Adjust relic stats by the set of the relic.
     * Currently only support sets: Explorer, Quick Gunner, Paladin, Genius
     * @param set name of the relic
     */
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

    /**
     * Get the set bonus
     * Currently only support sets: Explorer, Quick Gunner, Paladin, Genius
     * @param set name of the set
     * @return bonuses that will be given
     */
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

    /**
     * Check set bonus applied to the character
     *
     * @param character the character to check
     */
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

    /**
     * Check set bonus for specific pieces
     *
     * @param pieces the pieces to check
     */
    public void checkSetBonus(int pieces) {
        if (pieces >= setPiecesRequired) {
            System.out.println("Activated " + relicSet + " " + pieces + " bonus");
            System.out.println(setBonus);
        } else {
            System.out.println("Need " + setPiecesRequired + " to activate bonus, current pieces: " + pieces);
        }
    }

    /**
     * Apply set bonuses, Fast Gunner have extra bonuses.
     * @param character
     * @param pieces
     */
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
        if (relicSet.equals("Explorer")) {
            if (pieces >= 2) {
                character.setMaxHP(character.getMaxHP() + (int)(character.getMaxHP() * 0.12));
                System.out.println("Activated Set of 2: Increase max HP by 12%");
            }
        }
        if  (relicSet.equals("Paladin")) {
            if (pieces >= 2) {
                character.setDefense(character.getDefense() + (int)(character.getDefense() * 0.15));
                System.out.println("Activated Set of 2: Increase defense by 15%");
            }
            if (pieces >= 4) {
                character.setDefense(character.getDefense() + (int)(character.getDefense() * 0.2));
                System.out.println("Set of 4: Shield effect increase by 20%");
            }
        }
        if (relicSet.equals("Genius")) {
            if (pieces >= 2) {
                character.setAttack(character.getAttack() + (int)(character.getAttack() * 0.1));
                System.out.println("Set of 2: Increase Quantum damage by 10%");
            }
            if (pieces >= 4) {
                character.setAttack(character.getAttack() + (int)(character.getAttack() * 0.11));
                System.out.println("Set of 4: Ignore 10% of the enemy's defense");
            }
        }

    }

    /**
     * Calculate Relic stats using with requiredLevel and each default value
     *
     */
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
        // random sub attributes (each relic hae main stats and sub stats)
        if (Math.random() < 0.5) {
            int substatValue = requiredLevel * 2;
            setStat("critical_rate", getStat("critical_rate") + substatValue);
            System.out.println("Gain secondary attribute: Critical Hit Rate + " + substatValue);
        }

        System.out.println(name + " Final Stats: ");
        System.out.println("Main: " + getMainStatValue());
    }

    /**
     * Gets the main stat values bases on the slot for the relic
     * @return the main stat type and value in a String
     */
    private String getMainStatValue() {
        switch (slot) {
            case "Head": return "HP: " + getStat("hp");
            case "Arm": return "Attack: " + getStat("attack");
            case "Body": return "Defense: " + getStat("defense");
            case "Leg": return "Speed: " + getStat("speed");
            default: return "Unknown";
        }
    }

    /**
     * Display(print) all information of the relic
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: relic");
        System.out.println("Set: " + relicSet);
        System.out.println("Slot: " + slot);
        System.out.println("Set Bonus: " + setBonus);
        System.out.println("Pieces Required: " + setPiecesRequired);
    }

    /**
     * Returns all information for the relic into a CSV form.
     * @return the CSV of all information about the relic.
     */
    @Override
    public String toCSVFormat() {
        String str = ",Relic," + relicSet + "," + setBonus + "," + setPiecesRequired;
        return super.toCSVFormat() + str;
    }

}
