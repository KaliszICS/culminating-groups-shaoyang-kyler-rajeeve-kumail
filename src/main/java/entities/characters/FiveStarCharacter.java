package entities.characters;

import java.io.Serializable;

/**
 * This class represent a five-star playable character (rarest) in the game.
 * Extends {@link PlayableCharacter} with enhanced attributes and special features
 * Five-star characters have better base attributes compared to lower rarity characters.
 * <p>
 * This class includes unique abilities like ultimate attacks with special signature weapons that enhance their attributes even more.
 * </p>
 *
 * @author Shaoyang Chen
 * @version 1.4.1
 * @see PlayableCharacter
 */
public class FiveStarCharacter extends PlayableCharacter implements Serializable {
    //added serializable, so now people can save characters to a file (updated on 2026/1/13 emergency update)
    private static final long serialVersionUID = 1L;
    private String signatureWeapon;
    private String ultimateAnimation;

    /**
     * Constructs a FiveStarCharacter with default values.
     * Max HP increased by 50
     * Attack increased by 10
     * Defense increased by 5
     * Speed increased by 5
     */
    public FiveStarCharacter() {
        super();
        this.signatureWeapon = "Signature LightCone";
        this.ultimateAnimation = "Ultimate Animation";

        // 5 star characters have better base attributes
        maxHP += 50;
        currentHP = maxHP;
        attack += 10;
        defense += 5;
        speed += 5;
    }

    /**
     * Constructs a FiveStarCharacter with a specified name ONLY.
     * The character level is set to 1 and unlocked by default.
     *
     * @param name the name of this five-star character
     */
    public FiveStarCharacter(String name) {
        super(name, 1, true);
        this.signatureWeapon = name + "'s Signature LightCone";
        this.ultimateAnimation = name + "'s Ultimate Animation";

        // 5 star characters have better base attributes
        maxHP += 50;
        currentHP = maxHP;
        attack += 10;
        defense += 5;
        speed += 5;
    }

    /**
     * Constructs a FiveStarCharacter with a specified name and signature weapon.
     *
     * @param name            the name of this five-star character
     * @param signatureWeapon the signature weapon for this character
     */
    public FiveStarCharacter(String name, String signatureWeapon) {
        this(name);
        this.signatureWeapon = signatureWeapon;
    }

    /**
     * Runs the character's ultimate skill.
     * Displays the skill activation, animations, and signature weapon.
     * Ultimate skill will have 700% of character attack.
     */
    public void useUltimate() {
        System.out.println(name + " Used Ultimate Skill!");
        System.out.println("Animation: " + ultimateAnimation);
        System.out.println("Used Signature LightCone: " + signatureWeapon);

        // Ultimate Skill will cause extra damage
        int ultimateDamage = attack * 7;
        System.out.println("Caused " + ultimateDamage + " damage with Ultimate Skill!");
    }

    /**
     * Returns the signature weapon name.
     *
     * @return the signature weapon name
     */
    public String getSignatureWeapon() { return signatureWeapon; }

    /**
     * Sets a new signature weapon.
     *
     * @param signatureWeapon the new signature weapon name
     */
    public void setSignatureWeapon(String signatureWeapon) { this.signatureWeapon = signatureWeapon; }

    /**
     * Returns the ultimate animation description.
     *
     * @return the ultimate animation description
     */
    public String getUltimateAnimation() { return ultimateAnimation; }

    /**
     * Sets the ultimate animation description.
     *
     * @param ultimateAnimation the new ultimate animation description
     */
    public void setUltimateAnimation(String ultimateAnimation) { this.ultimateAnimation = ultimateAnimation; }

    /**
     * Executes the character's standard skill.
     * Five-star characters have enhanced skill effects.
     */
    @Override
    public void useSkill() {
        System.out.println(name + "(5★) used Skill!");
        // 5★ have better skills
    }

    /**
     * Displays info about a character.
     * Includes information from the parent class and 5★ specific details:
     * rarity, signature weapon, and ultimate animation.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Rarity: ★★★★★");
        System.out.println("Signature LightCone: " + signatureWeapon);
        System.out.println("Ultimate Animation: " + ultimateAnimation);
    }

    /**
     * Returns a CSV of this 5★ character's attributes.
     * Details: rarity, signature weapon, and ultimate animation.
     *
     * @return a comma-separated string in CSV format
     */
    @Override
    public String toCSVFormat() {
        String escapedWeapon = signatureWeapon.replace("\"", "\"\"");
        String escapedAnimation = ultimateAnimation.replace("\"", "\"\"");
        String var = ",★★★★★,\"" + escapedWeapon + "\",\"" + escapedAnimation + "\"";
        return super.toCSVFormat() + var;
    }
}
