package systems.ai;
import entities.enemies.Enemy;
import entities.abs.BattleUnit;
import java.util.List;

public class EnemyAI {
    private Enemy controlledEnemy;
    private int aggressionLevel;     //0: defensive, 1: balanced, 2: aggressive (set to 1 by default)
    private String[] behaviorPatterns;

    public EnemyAI() {
        this.controlledEnemy = null;
        this.aggressionLevel = 1;
        this.behaviorPatterns = new String[] { "HIGHEST_THREAT"};
    }

    public EnemyAI(Enemy enemy) {
        this.controlledEnemy = enemy;
        this.aggressionLevel = 1;
        this.behaviorPatterns = new String[] { "HIGHEST_THREAT"};
    }

    public void setControlledEnemy(Enemy enemy) {
        this.controlledEnemy = enemy;
    }
    public Enemy getControlledEnemy() {
        return controlledEnemy;
    }
    public void setAggressionLevel(int level) {
        if (level < 0) {
            level = 0;
        }
        if (level > 2) {
            level = 2;
        }
        this.aggressionLevel = level;
    }
    public int getAggressionLevel() {
        return aggressionLevel;
    }
    public void setBehaviorPatterns(String[] patterns) {
        this.behaviorPatterns = patterns;
    }
    public String[] getBehaviorPatterns() {
        return behaviorPatterns;
    }

    public String decideAction() {
        if (controlledEnemy == null) {
            return "WAIT";
        }
        int hp = controlledEnemy.getCurrentHP();
        int max = controlledEnemy.getMaxHP();
        int hpPct;  //hp percentage
        if (max>0) {
            hpPct = (hp*100) / max;
        } else {
            hpPct = 0;
        }

        if (aggressionLevel == 0 && hpPct <= 25) {
            return "DEFEND";
        }
        if (aggressionLevel == 2) {
            return "SKILL1";
        }
        if (hpPct <= 40) {
            return "DEFEND";
        }
        return "ATTACK";
    }

    public BattleUnit selectTarget(List<BattleUnit> targets) {
        if (targets == null || targets.isEmpty()) {
            return null;
        }

        String strategy; 
        if (behaviorPatterns != null && behaviorPatterns.length > 0) {
            strategy = behaviorPatterns[0];
        } else {
            strategy = "HIGHEST_THREAT";
        }
        if ("LOWEST_HP".equalsIgnoreCase(strategy)) {
            return pickLowestHp(targets);
        }
        if ("RANDOM".equalsIgnoreCase(strategy)) {
            return pickRandomAlive(targets);
        }

        BattleUnit best = null;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < targets.size(); i++) {
            BattleUnit t = targets.get(i);
            if (t == null) {
                continue;
            }
            if (!t.isAlive()) {
                continue;
            }
            int score = evaluateThreat(t);
            if (score > bestScore) {
                bestScore = score;
                best = t;
            }
        }
        return best;
    }
    //helper 1
    private BattleUnit pickLowestHp(java.util.List<BattleUnit> targets) {
        BattleUnit best = null;
        int minHp = Integer.MAX_VALUE;
        for (int i =0; i < targets.size(); i++) {
            BattleUnit t = targets.get(i);
            if (t == null) {
                continue;
            }
            if (!t.isAlive()) {
                continue;
            }
            int hp = t.getCurrentHP();
            if (hp < minHp) {
                minHp = hp;
                best = t;
            }
        }
        return best;
    }
    //helper 2
    private BattleUnit pickRandomAlive(java.util.List<BattleUnit> targets) {
        java.util.ArrayList<BattleUnit> pool = new java.util.ArrayList<>();
        for (int i = 0; i < targets.size(); i++) {
            BattleUnit t = targets.get(i);
            if (t != null && t.isAlive()) {
                pool.add(t);
            }
        }
        if (pool.isEmpty()) {
            return null;
        }
        int idx = (int) (Math.random() *pool.size());
        return pool.get(idx);
    }

    public int evaluateThreat(BattleUnit unit) {
        if (unit == null) {
            return Integer.MIN_VALUE;
        }
        if(!unit.isAlive()) {
            return Integer.MIN_VALUE;
        }

        int atk = unit.getAttack();
        int def = unit.getDefense();
        int spd = unit.getSpeed();
        int hp = unit.getCurrentHP();
        int max = unit.getMaxHP();

        int missing = 0;
        if (max > 0) {
            missing = max - hp;
        }
        return (atk *2) + spd + (missing /4) - (def/3);
    }
}