package entities.characters;

public class FiveStarCharacter extends PlayableCharacter {
    private String signatureWeapon;
    private String ultimateAnimation;

    public FiveStarCharacter() {
        super();
        this.signatureWeapon = "Signature LightCone";
        this.ultimateAnimation = "Ultimate Animation";

        // 5 star charas have better base attributes
        maxHP += 50;
        currentHP = maxHP;
        attack += 10;
        defense += 5;
        speed += 5;
    }

    public FiveStarCharacter(String name) {
        super(name, 1, true);
        this.signatureWeapon = name + "'s Signature LightCone";
        this.ultimateAnimation = name + "'s Ultimate Animation";

        // 5 star charas have better base attributes
        maxHP += 50;
        currentHP = maxHP;
        attack += 10;
        defense += 5;
        speed += 5;
    }

    public FiveStarCharacter(String name, String signatureWeapon) {
        this(name);
        this.signatureWeapon = signatureWeapon;
    }

    public void useUltimate() {
        System.out.println(name + " Used Ultimate Skill!");
        System.out.println("Animation: " + ultimateAnimation);
        System.out.println("Used Signature LightCone: " + signatureWeapon);

        // Ultimate Skill will cause extra damage
        int ultimateDamage = attack * 3;
        System.out.println("Caused " + ultimateDamage + " damage with Ultimate Skill!");
    }
    public String getSignatureWeapon() { return signatureWeapon; }
    public void setSignatureWeapon(String signatureWeapon) { this.signatureWeapon = signatureWeapon; }
    public String getUltimateAnimation() { return ultimateAnimation; }
    public void setUltimateAnimation(String ultimateAnimation) { this.ultimateAnimation = ultimateAnimation; }

    @Override
    public void useSkill() {
        System.out.println(name + "(5★) used Skill!");
        // 5★ have better skills
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Rarity: ★★★★★");
        System.out.println("Signature LightCone: " + signatureWeapon);
        System.out.println("Ultimate Animation: " + ultimateAnimation);
    }

    @Override
    public String toCSVFormat() {
        String escapedWeapon = signatureWeapon.replace("\"", "\"\"");
        String escapedAnimation = ultimateAnimation.replace("\"", "\"\"");
        String var = ",★★★★★,\"" + escapedWeapon + "\",\"" + escapedAnimation + "\"";
        return super.toCSVFormat() + var;
    }
}