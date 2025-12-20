package entities.characters;

public class FourStarCharacter extends PlayableCharacter {
    private boolean canBeObtainedFree;

    public FourStarCharacter() {
        super();
        this.canBeObtainedFree = true;

        // 四星角色初始属性适中
        maxHP += 20;
        currentHP = maxHP;
        attack += 5;
        defense += 3;
        speed += 3;
    }

    public FourStarCharacter(String name, boolean freeObtainable) {
        super(name, 1, true);
        this.canBeObtainedFree = freeObtainable;

        // 四星角色初始属性适中
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

    public boolean checkAvailability() {
        if (canBeObtainedFree) {
            System.out.println(name + " 可以通过免费方式获得");
        } else {
            System.out.println(name + " 只能通过特定活动或祈愿获得");
        }
        return canBeObtainedFree;
    }

    @Override
    public void useSkill() {
        System.out.println(name + "（四星）发动实用技能！");
        // 四星角色的技能实用性强
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("稀有度: ★★★★");
        System.out.println("免费获取: " + (canBeObtainedFree ? "是" : "否"));
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",★★★★,%b", canBeObtainedFree);
    }

    // Getter 和 Setter
    public boolean isCanBeObtainedFree() { return canBeObtainedFree; }
    public void setCanBeObtainedFree(boolean canBeObtainedFree) { this.canBeObtainedFree = canBeObtainedFree; }
}