package systems.battle;

import java.util.ArrayList;
import java.util.List;

import entities.characters.Character;
import entities.enemies.Enemy;

/**
 * Quick sanity test for BattleSystem:
 * - grid init
 * - turn order
 * - damage + battle end
 * - useSkill multipliers
 */
public class TestBattleSystem {
    public static void main(String[] args) {
        // Your Character(name, level) and Enemy(name, difficulty)
        Character c1 = new Character("Hero", 5);
        Character c2 = new Character("Mage", 6);

        Enemy e1 = new Enemy("Slime", 2);
        Enemy e2 = new Enemy("Goblin", 3);

        List<Character> playerTeam = new ArrayList<Character>();
        playerTeam.add(c1);
        playerTeam.add(c2);

        List<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e1);
        enemies.add(e2);

        BattleSystem bs = new BattleSystem();
        bs.initializeBattle(playerTeam, enemies);

        int turns = 0;
        int MAX_TURNS = 100;
        while (!bs.checkBattleEnd() && turns < MAX_TURNS) {
            bs.executeTurn();
            turns++;
        }
        System.out.println("Battle 1 finished after " + turns + " turns.");

        // Demo: re-init and try useSkill with different multipliers
        bs.initializeBattle(playerTeam, enemies);
        bs.useSkill(c1, 0); // multiplier 1.0
        bs.useSkill(c2, 2); // multiplier 2.0

        turns = 0;
        while (!bs.checkBattleEnd() && turns < MAX_TURNS) {
            bs.executeTurn();
            turns++;
        }
        System.out.println("Battle 2 (with skills) finished after " + turns + " turns.");
    }
}
