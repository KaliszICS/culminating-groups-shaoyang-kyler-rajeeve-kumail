package entities.characters;

public class FiveStarCharacter extends PlayableCharacter {
    private String signatureWeapon;
    private String ultimateAnimation;

    public FiveStarCharacter() {
        super();
        this.signatureWeapon = "专属光锥";
        this.ultimateAnimation = "华丽终结技动画";

        // 五星角色初始属性更高
        maxHP += 50;
        currentHP = maxHP;
        attack += 10;
        defense += 5;
        speed += 5;
    }

    public FiveStarCharacter(String name) {
        super(name, 1, true);
        this.signatureWeapon = name + "的专属光锥";
        this.ultimateAnimation = name + "的终结技动画";

        // 五星角色初始属性更高
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
        System.out.println(name + " 发动终结技！");
        System.out.println("播放动画: " + ultimateAnimation);
        System.out.println("使用专属武器: " + signatureWeapon);

        // 终结技效果：造成大量伤害
        int ultimateDamage = attack * 3;
        System.out.println("造成 " + ultimateDamage + " 点终结技伤害！");
    }

    @Override
    public void useSkill() {
        System.out.println(name + "（五星）发动强力技能！");
        // 五星角色的技能更强
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("稀有度: ★★★★★");
        System.out.println("专属武器: " + signatureWeapon);
        System.out.println("终结技动画: " + ultimateAnimation);
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",★★★★★,\"%s\",\"%s\"",
                signatureWeapon, ultimateAnimation);
    }

    // Getter 和 Setter
    public String getSignatureWeapon() { return signatureWeapon; }
    public void setSignatureWeapon(String signatureWeapon) { this.signatureWeapon = signatureWeapon; }
    public String getUltimateAnimation() { return ultimateAnimation; }
    public void setUltimateAnimation(String ultimateAnimation) { this.ultimateAnimation = ultimateAnimation; }
}