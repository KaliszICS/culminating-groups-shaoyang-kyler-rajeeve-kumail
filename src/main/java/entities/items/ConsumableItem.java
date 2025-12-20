package entities.items;

public class ConsumableItem extends Item {
    private int usesRemaining;
    private int effectDuration;

    public ConsumableItem() {
        super();
        this.usesRemaining = 3;
        this.effectDuration = 30; // 30秒效果持续时间
    }

    public ConsumableItem(String name, int uses) {
        super(name, 50); // 消耗品基础价值50
        this.usesRemaining = uses;
        this.effectDuration = 30;
    }

    public ConsumableItem(String name, int uses, int duration, int value) {
        super(name, value);
        this.usesRemaining = uses;
        this.effectDuration = duration;
    }

    @Override
    public void use() {
        if (usesRemaining <= 0) {
            System.out.println(name + " 已经用完了！");
            return;
        }

        usesRemaining--;
        System.out.println("使用了 " + name);
        System.out.println("效果持续: " + effectDuration + " 秒");
        System.out.println("剩余使用次数: " + usesRemaining);

        // 不同类型的消耗品效果
        if (name.contains("治疗")) {
            System.out.println("恢复生命值！");
        } else if (name.contains("攻击")) {
            System.out.println("增加攻击力！");
        } else if (name.contains("防御")) {
            System.out.println("增加防御力！");
        } else if (name.contains("速度")) {
            System.out.println("增加速度！");
        }
    }

    public void refill() {
        refill(3); // 默认补充3次
    }

    public void refill(int amount) {
        usesRemaining += amount;
        System.out.println(name + " 补充了 " + amount + " 次使用次数");
        System.out.println("当前剩余次数: " + usesRemaining);
    }

    public void increaseDuration(int seconds) {
        effectDuration += seconds;
        System.out.println(name + " 效果持续时间增加 " + seconds + " 秒");
        System.out.println("总持续时间: " + effectDuration + " 秒");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("类型: 消耗品");
        System.out.println("剩余使用次数: " + usesRemaining);
        System.out.println("效果持续时间: " + effectDuration + " 秒");
    }

    @Override
    public String toCSVFormat() {
        return super.toCSVFormat() + String.format(",消耗品,%d,%d", usesRemaining, effectDuration);
    }

    // Getter 和 Setter
    public int getUsesRemaining() { return usesRemaining; }
    public void setUsesRemaining(int usesRemaining) { this.usesRemaining = usesRemaining; }
    public int getEffectDuration() { return effectDuration; }
    public void setEffectDuration(int effectDuration) { this.effectDuration = effectDuration; }
}