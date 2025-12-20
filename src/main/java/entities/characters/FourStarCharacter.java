package entities.characters;

public class FourStarCharacter extends PlayableCharacter {
    private boolean canBeObtainedFree;

    public FourStarCharacter() {
        super();
        this.canBeObtainedFree = true;

        // 4★ have standard level of attributes
        maxHP += 20;
        currentHP = maxHP;
        attack += 5;
        defense += 3;
        speed += 3;
    }

    public FourStarCharacter(String name, boolean freeObtainable) {
        super(name, 1, true);
        this.canBeObtainedFree = freeObtainable;

        // 4★ have standard level of attributes
        maxHP += 20;
        currentHP = maxHP;
        attack += 5;
        defense += 3;
        speed += 3;
    }

    public FourStarCharacter(String name, int level, boolean freeObtainable) {
        super(name, level, true);
        this.canBeObtainedFree = freeObtainable;
    }

    public boolean isCanBeObtainedFree() { return canBeObtainedFree; }
    public void setCanBeObtainedFree(boolean canBeObtainedFree) { this.canBeObtainedFree = canBeObtainedFree; }

    public boolean checkAvailability() {
        if (canBeObtainedFree) {
            System.out.println(name + " Can be obtained as a free character");
        } else {
            System.out.println(name + " only a UP character");
        }
        return canBeObtainedFree;
    }

    @Override
    public void useSkill() {
        System.out.println(name + "(4★) used Skill!");
        // 四星角色的技能实用性强
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Rarity: ★★★★");
        System.out.println("Free Obtain: " + (canBeObtainedFree ? "True" : "False"));
    }

    @Override
    public String toCSVFormat() {
        String var = ",★★★★," + canBeObtainedFree;
        return super.toCSVFormat() + var;
    }
}