package entities.enemies;

import java.io.Serializable;

/**
 * This class represents a boss enemy, with attributes of if it has phases, the current phase, and phase messages.
 * Inherited from {@link Enemy}, therefore they have shared properties.
 *
 * @version 1.4.1
 * @author Shaoyang Chen
 * @see Enemy
 */
public class BossEnemy extends Enemy implements Serializable {
    //added serializable, so now people can save characters to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private boolean hasPhases;
    private int currentPhase;
    private String[] phaseMessages;

    /**
     * Instantiates a new Boss enemy with all default attributes
     */
    public BossEnemy() {
        super();
        this.hasPhases = true;
        this.currentPhase = 1;
        this.phaseMessages = new String[]{
                "Phase 1: You cannot stop me!",
                "Phase 2: This is not all of my power!",
                "Phase 3: Feel the despair!"
        };
        // Enhance Attributes for boss
        maxHP *= 3;
        currentHP = maxHP;
        attack *= 2;
        defense *= 2;
        speed += 5;
    }

    /**
     * Instantiates a new Boss enemy with name and phases
     *
     * @param name   the name
     * @param phases the phases
     */
    public BossEnemy(String name, int phases) {
        super(name, 10); // Boss default difficulty: 10
        this.hasPhases = phases > 1;
        this.currentPhase = 1;
        this.phaseMessages = new String[phases];
        // initialize phase message
        for (int i = 0; i < phases; i++) {
            phaseMessages[i] = name + " Phase " + (i + 1) + " form.";
        }
        // Enhance Attributes for boss
        maxHP *= 3;
        currentHP = maxHP;
        attack *= 2;
        defense *= 2;
        speed += 5;
    }

    /**
     * return does the boss enemy has phases boolean.
     *
     * @return the boolean of does the boss enemy has phases
     */
    public boolean hasPhases() { return hasPhases; }

    /**
     * Sets if the boss has phases.
     *
     * @param hasPhases boolean if the boss have phases
     */
    public void setHasPhases(boolean hasPhases) { this.hasPhases = hasPhases; }

    /**
     * Gets current phase.
     *
     * @return the current phase
     */
    public int getCurrentPhase() { return currentPhase; }

    /**
     * Sets current phase.
     *
     * @param currentPhase the current phase
     */
    public void setCurrentPhase(int currentPhase) { this.currentPhase = currentPhase; }

    /**
     * Get phase messages string[].
     *
     * @return the string[] of phase messages
     */
    public String[] getPhaseMessages() { return phaseMessages; }

    /**
     * Sets phase messages.
     *
     * @param phaseMessages the phase messages to set
     */
    public void setPhaseMessages(String[] phaseMessages) { this.phaseMessages = phaseMessages; }

    /**
     * Represents a boss's transition phase.
     * Also prints out the message about the change
     */
    public void transitionPhase() {
        if (!hasPhases || currentPhase >= phaseMessages.length) {
            System.out.println(name + " is on final phase.");
            return;
        }
        currentPhase++;
        System.out.println(phaseMessages[currentPhase - 1]);
        // When enter new phase, heal characters attributes
        currentHP = Math.min(currentHP + maxHP / 2, maxHP);
        attack += 10;
        defense += 5;
        System.out.println(name + " entering Phase " + currentPhase + "!");
        System.out.println("Heal HP and added attack and defense");
    }

    /**
     * Use special attack (boss enemy) in default the boss have 30% chance of using this attack.
     */
    public void useSpecialAttack() {
        System.out.println(name + " used special attack skill");
        switch (currentPhase) {
            case 1:
                System.out.println("Area of effect attack: Caused damage to all enemies!");
                break;
            case 2:
                System.out.println("Summon reinforcements: Summons minions to assist in the battle!");
                break;
            case 3:
                System.out.println("A devastating blow: cause massive damage! Please be careful!");
                break;
            default:
                System.out.println("Powerful attack!");
        }
    }

    /**
     * Calculates how much damage the boss is taking
     * @param damage the damage that the boss is taking
     */

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        // check if entering new phase
        if (hasPhases && currentPhase < phaseMessages.length) {
            int phaseThreshold = maxHP / phaseMessages.length;
            if (currentHP < maxHP - (currentPhase * phaseThreshold)) {
                transitionPhase();
            }
        }
    }

    /**
     * The boss's default skill. (attack player)
     * The boss have a default chance of 30% to use special attack which will cause more damage to the player.
     */
    @Override
    public void useSkill() {
        if (random.nextDouble() < 0.3) { // boss 30% chance cause special attack
            useSpecialAttack();
        } else {
            super.useSkill();
        }
    }

    /**
     * Prints all the information of a boss.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Boss type: " + (hasPhases ? "Multi-phase" : "Single-phase") + " boss");
        System.out.println("Current phase: " + currentPhase + "/" + phaseMessages.length);
    }

    /**
     * Return all information about the boss in CSV format.
     * @return CSV of all information about the boss.
     */
    @Override
    public String toCSVFormat() {
        StringBuilder phases = new StringBuilder();
        for (String msg : phaseMessages) {
            if (msg != null) {
                if (phases.length() > 0) phases.append(";");
                phases.append(msg);
            }
        }
        String var = phases.toString();
        String var2 = "," + hasPhases + "," + currentPhase + ",\"" + var.replace("\"", "\"\"") + "\"";
        return super.toCSVFormat() + var2;
    }
}