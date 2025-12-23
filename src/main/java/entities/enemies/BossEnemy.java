package entities.enemies;

public class BossEnemy extends Enemy {
    private boolean hasPhases;
    private int currentPhase;
    private String[] phaseMessages;

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

    public boolean hasPhases() { return hasPhases; }
    public void setHasPhases(boolean hasPhases) { this.hasPhases = hasPhases; }
    public int getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(int currentPhase) { this.currentPhase = currentPhase; }
    public String[] getPhaseMessages() { return phaseMessages; }
    public void setPhaseMessages(String[] phaseMessages) { this.phaseMessages = phaseMessages; }

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

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        // check if entering new phase
        if (hasPhases && currentPhase < phaseMessages.length) {
            int phaseThreshold = maxHP / phaseMessages.length;
            int remainingPhases = phaseMessages.length - currentPhase;

            if (currentHP < maxHP - (currentPhase * phaseThreshold)) {
                transitionPhase();
            }
        }
    }

    @Override
    public void useSkill() {
        if (random.nextDouble() < 0.3) { // boss 30% chance cause special attack
            useSpecialAttack();
        } else {
            super.useSkill();
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Boss type: " + (hasPhases ? "Multi-phase" : "Single-phase") + " boss");
        System.out.println("Current phase: " + currentPhase + "/" + phaseMessages.length);
    }

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