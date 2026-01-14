package entities.characters;

/**
 * Represents a four star character, with standard level of attributes.
 * This class is inherited from {@link PlayableCharacter}
 * @author Shaoyang Chen
 * @version 1.4.1
 */
public class FourStarCharacter extends PlayableCharacter {
    //added serializable, so now people can save characters to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private boolean canBeObtainedFree;

    /**
     * Instantiates a new Four star character.
     */
    public FourStarCharacter() {
        super();
        this.canBeObtainedFree = true;
        maxHP += 20;
        currentHP = maxHP;
        attack += 5;
        defense += 3;
        speed += 3;
    }

    /**
     * Instantiates a new Four star character.
     *
     * @param name           the name
     * @param freeObtainable the free obtainable
     */
    public FourStarCharacter(String name, boolean freeObtainable) {
        super(name, 1, true);
        this.canBeObtainedFree = freeObtainable;
        maxHP += 20;
        currentHP = maxHP;
        attack += 5;
        defense += 3;
        speed += 3;
    }

    /**
     * Instantiates a new Four star character.
     *
     * @param name           the name
     * @param level          the level
     * @param freeObtainable the free obtainable
     */
    public FourStarCharacter(String name, int level, boolean freeObtainable) {
        super(name, level, true);
        this.canBeObtainedFree = freeObtainable;
    }

    /**
     * Is the character can be obtained free boolean.
     *
     * @return the boolean
     */
    public boolean isCanBeObtainedFree() { return canBeObtainedFree; }

    /**
     * Sets the character can be obtained free.
     *
     * @param canBeObtainedFree the can be obtained free
     */
    public void setCanBeObtainedFree(boolean canBeObtainedFree) { this.canBeObtainedFree = canBeObtainedFree; }

    /**
     * Check availability boolean.
     *
     * @return the boolean
     */
    public boolean checkAvailability() {
        if (canBeObtainedFree) {
            System.out.println(name + " Can be obtained as a free character");
        } else {
            System.out.println(name + " only a UP character");
        }
        return canBeObtainedFree;
    }

    /**
     * Use skill
     */
    @Override
    public void useSkill() {
        System.out.println(name + "(4★) used Skill!");
    }
    /**
     * Display info of the 4★ character
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Rarity: ★★★★");
        System.out.println("Free Obtain: " + (canBeObtainedFree ? "True" : "False"));
    }
    /**
     *
     */
    @Override
    public String toCSVFormat() {
        String var = ",★★★★," + canBeObtainedFree;
        return super.toCSVFormat() + var;
    }
}
