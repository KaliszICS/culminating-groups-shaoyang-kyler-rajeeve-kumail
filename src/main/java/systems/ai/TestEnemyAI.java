package systems.ai;

import java.util.ArrayList;
import java.util.List;

import entities.enemies.Enemy;
import entities.abs.BattleUnit;

/**
 * Focused tests for EnemyAI:
 * - decideAction by HP% and aggression
 * - selectTarget strategies: HIGHEST_THREAT, LOWEST_HP, RANDOM
 * - evaluateThreat visibility
 */
public class TestEnemyAI {

    // Tiny concrete BattleUnit we control completely for testing.
    static class DummyUnit extends BattleUnit {
        public DummyUnit(String name, int maxHP, int attack, int defense, int speed) {
            super(name, maxHP, attack, defense, speed);
        }
        @Override public void useSkill() { /* no-op */ }
        @Override public void displayInfo() { /* no-op */ }
    }

    public static void main(String[] args) {
        // Enemy(name, difficulty)
        Enemy boss = new Enemy("AI Boss", 5);

        // We can tune HP etc. using BattleUnit setters
        boss.setMaxHP(200);
        boss.setCurrentHP(200);

        EnemyAI ai = new EnemyAI(boss);

        // Build targets with known stats
        BattleUnit lowHp = new DummyUnit("LowHP", 100, 15, 5, 10);
        lowHp.setCurrentHP(10); // 10/100

        BattleUnit tank = new DummyUnit("Tank", 180, 10, 20, 8);
        tank.setCurrentHP(180);

        BattleUnit glass = new DummyUnit("Glass", 70, 30, 2, 14);
        glass.setCurrentHP(70);

        List<BattleUnit> targets = new ArrayList<BattleUnit>();
        targets.add(lowHp);
        targets.add(tank);
        targets.add(glass);

        // 1) decideAction by aggression + HP%
        ai.setAggressionLevel(0); // defensive
        boss.setCurrentHP(40);    // 40/200 = 20%
        System.out.println("Defensive @20% -> " + ai.decideAction()); // expect DEFEND

        ai.setAggressionLevel(2); // aggressive
        boss.setCurrentHP(150);   // ~75%
        System.out.println("Aggressive @75% -> " + ai.decideAction()); // expect SKILL1

        ai.setAggressionLevel(1); // balanced
        boss.setCurrentHP(200);   // full
        System.out.println("Balanced @100% -> " + ai.decideAction()); // expect ATTACK

        // 2) Strategy: HIGHEST_THREAT (default)
        ai.setBehaviorPatterns(new String[] { "HIGHEST_THREAT" });
        BattleUnit byThreat = ai.selectTarget(targets);
        System.out.println("HIGHEST_THREAT picked: " + (byThreat == null ? "null" : byThreat.getName()));

        // 3) Strategy: LOWEST_HP
        ai.setBehaviorPatterns(new String[] { "LOWEST_HP" });
        BattleUnit byLowest = ai.selectTarget(targets);
        System.out.println("LOWEST_HP picked: " + (byLowest == null ? "null" : byLowest.getName())); // expect LowHP

        // 4) Strategy: RANDOM (repeat to see variety)
        ai.setBehaviorPatterns(new String[] { "RANDOM" });
        for (int i = 1; i <= 5; i++) {
            BattleUnit r = ai.selectTarget(targets);
            System.out.println("RANDOM try " + i + " picked: " + (r == null ? "null" : r.getName()));
        }

        // 5) Threat scores to understand picks
        System.out.println("Threat scores:");
        System.out.println("  LowHP:  " + ai.evaluateThreat(lowHp));
        System.out.println("  Tank:   " + ai.evaluateThreat(tank));
        System.out.println("  Glass:  " + ai.evaluateThreat(glass));
    }
}
