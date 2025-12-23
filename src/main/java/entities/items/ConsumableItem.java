package entities.items;

public class ConsumableItem extends Item {
    private int usesRemaining;
    private int effectDuration;

    public ConsumableItem() {
        super();
        this.usesRemaining = 3;
        this.effectDuration = 30; // 30s duration
    }

    public ConsumableItem(String name, int uses) {
        super(name, 50); // base value 50
        this.usesRemaining = uses;
        this.effectDuration = 30;
    }

    public ConsumableItem(String name, int uses, int duration, int value) {
        super(name, value);
        this.usesRemaining = uses;
        this.effectDuration = duration;
    }

    public int getUsesRemaining() { return usesRemaining; }
    public void setUsesRemaining(int usesRemaining) { this.usesRemaining = usesRemaining; }
    public int getEffectDuration() { return effectDuration; }
    public void setEffectDuration(int effectDuration) { this.effectDuration = effectDuration; }

    @Override
    public void use() {
        if (usesRemaining <= 0) {
            System.out.println(name + " is used up!");
            return;
        }
        usesRemaining--;
        System.out.println("Used " + name);
        System.out.println("Effect Duration: " + effectDuration + " seconds");
        System.out.println("Uses remaining: " + usesRemaining);
        // different effects
        if (name.contains("Heal")) {
            System.out.println("Heal HP");
        } else if (name.contains("Attack")) {
            System.out.println("Increase Attack!");
        } else if (name.contains("Defense")) {
            System.out.println("Increase Defense!");
        } else if (name.contains("Speed")) {
            System.out.println("Increase Speed!");
        }
    }
    public void refill() {
        refill(3); // default
    }

    public void refill(int amount) {
        usesRemaining += amount;
        System.out.println(name + " refilled " + amount + " uses");
        System.out.println("Current uses remaining " + usesRemaining);
    }

    public void increaseDuration(int seconds) {
        effectDuration += seconds;
        System.out.println(name + " increased effect duration by " + seconds + " seconds");
        System.out.println("Total Duration: " + effectDuration + " seconds");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: ConsumableItem");
        System.out.println("Uses Remaining " + usesRemaining);
        System.out.println("Effect Duration " + effectDuration + " Seconds");
    }

    @Override
    public String toCSVFormat() {
        String str = ",ConsumableItem," + usesRemaining + "," + effectDuration;
        return super.toCSVFormat() + str;
    }
}