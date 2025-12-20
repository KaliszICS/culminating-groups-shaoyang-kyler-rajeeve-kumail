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
                "第一形态：你们无法阻止我！",
                "第二形态：这还不是我的全部力量！",
                "最终形态：感受绝望吧！"
        };

        // Boss属性增强
        maxHP *= 3;
        currentHP = maxHP;
        attack *= 2;
        defense *= 2;
        speed += 5;
    }

    public BossEnemy(String name, int phases) {
        super(name, 10); // Boss默认难度为10
        this.hasPhases = phases > 1;
        this.currentPhase = 1;
        this.phaseMessages = new String[phases];

        // 初始化阶段消息
        for (int i = 0; i < phases; i++) {
            phaseMessages[i] = name + " 第" + (i + 1) + "形态";
        }

        // Boss属性增强
        maxHP *= 3;
        currentHP = maxHP;
        attack *= 2;
        defense *= 2;
        speed += 5;
    }

    public void transitionPhase() {
        if (!hasPhases || currentPhase >= phaseMessages.length) {
            System.out.println(name + " 已经是最终形态了！");
            return;
        }

        currentPhase++;
        System.out.println(phaseMessages[currentPhase - 1]);

        // 每进入新阶段，恢复部分生命并增强属性
        currentHP = Math.min(currentHP + maxHP / 2, maxHP);
        attack += 10;
        defense += 5;

        System.out.println(name + " 进入第 " + currentPhase + " 阶段！");
        System.out.println("生命值恢复，攻击和防御提升！");
    }

    public void useSpecialAttack() {
        System.out.println(name + " 发动特殊攻击！");

        switch (currentPhase) {
            case 1:
                System.out.println("范围攻击：对所有敌人造成伤害！");
                break;
            case 2:
                System.out.println("召唤援军：召唤小怪协助战斗！");
                break;
            case 3:
                System.out.println("灭世一击：造成巨额伤害！");
                break;
            default:
                System.out.println("强力攻击！");
        }
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        // 检查是否需要进入下一阶段
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
        if (random.nextDouble() < 0.3) { // 30%概率使用特殊攻击
            useSpecialAttack();
        } else {
            super.useSkill();
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Boss类型: " + (hasPhases ? "多阶段" : "单阶段"));
        System.out.println("当前阶段: " + currentPhase + "/" + phaseMessages.length);
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

        return super.toCSVFormat() + String.format(",%b,%d,\"%s\"",
                hasPhases, currentPhase, phases.toString());
    }

    // Getter 和 Setter
    public boolean hasPhases() { return hasPhases; }
    public void setHasPhases(boolean hasPhases) { this.hasPhases = hasPhases; }
    public int getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(int currentPhase) { this.currentPhase = currentPhase; }
    public String[] getPhaseMessages() { return phaseMessages; }
    public void setPhaseMessages(String[] phaseMessages) { this.phaseMessages = phaseMessages; }
}