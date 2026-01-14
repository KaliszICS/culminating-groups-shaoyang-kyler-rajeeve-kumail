package entities;

import entities.characters.Character;
import entities.characters.*;
import entities.equipment.*;
import entities.items.*;
import entities.enemies.*;

//test entities functionality, this class was written by AI, and it is for debug only
public class TestEntities {
    public static void main(String[] args) {
        System.out.println("===== ENTITIES PACKAGE TEST =====\n");
        testCharacters();
        System.out.println();
        testEnemies();
        System.out.println();
        testItems();
        System.out.println();
        testEquipment();
        System.out.println();
        testBattle();
    }

    private static void testCharacters() {
        System.out.println("***** Character Test *****");

        FiveStarCharacter fiveStar = new FiveStarCharacter("Jingyuan");
        fiveStar.setSignatureWeapon("Before Dawn");
        fiveStar.displayInfo();
        fiveStar.useUltimate();

        System.out.println();

        FourStarCharacter fourStar = new FourStarCharacter("Surang", true);
        fourStar.displayInfo();
        fourStar.checkAvailability();

        System.out.println();

        // TEST UPGRADE
        Character character = new Character("Danheng", 10);
        character.addExperience(500);
        character.displayInfo();
    }

    private static void testEnemies() {
        System.out.println("***** Enemies Test *****");
        RegularEnemy regular = new RegularEnemy("Voidranger: Reaver", false);
        regular.displayInfo();
        regular.useSkill();

        System.out.println();

        BossEnemy boss = new BossEnemy("Doomsday Beast", 3);
        boss.displayInfo();
        boss.useSpecialAttack();

        // TEST BOSS PHASE CHANGE
        boss.takeDamage(200);
    }

    private static void testItems() {
        System.out.println("****** Items Test *****");

        ConsumableItem potion = new ConsumableItem("Healing Potion", 5, 60, 100);
        potion.displayInfo();
        potion.use();

        System.out.println();

        MaterialItem material1 = new MaterialItem("Credits", "Regular Material", 1, 1000);
        MaterialItem material2 = new MaterialItem("Lost Lightdust", "Rare Material", 3, 5000);

        material1.displayInfo();
        System.out.println();
        material2.displayInfo();

        // TEST COMBINE
        MaterialItem combined = material2.combine(
                new MaterialItem("Lost Lightdust", "Rare Material", 3, 5000)
        );
    }

    private static void testEquipment() {
        System.out.println("***** Equipment Test *****");

        LightCone lightCone = new LightCone("Night on the Milky Way", "Erudition");
        lightCone.displayInfo();
        lightCone.activateAbility();
        lightCone.calculateStats();

        System.out.println();

        Relic relic = new Relic("Musketeer’s Wild Wheat Felt Hat", "Quick Gunner");
        relic.setSlot("Head");
        relic.displayInfo();
        relic.checkSetBonus(2);
    }

    private static void testBattle() {
        System.out.println("***** Battle Test *****");

        // Create entities
        PlayableCharacter player = new PlayableCharacter("Trailblazer", 5, true);
        RegularEnemy enemy = new RegularEnemy("Voidranger: Trampler", true);

        // Start Status
        System.out.println("Battle Start!");
        System.out.println("Player: " + player.getName() + " HP: " + player.getCurrentHP());
        System.out.println("Enemy: " + enemy.getName() + " HP: " + enemy.getCurrentHP());

        // battle rounds
        for (int round = 1; round <= 3; round++) {
            System.out.println("\n=== Round " + round + " ===");
            player.attack(enemy);
            if (enemy.isAlive()) {
                enemy.attack(player);
            } else {
                System.out.println("Enemy Defeated!");
                break;
            }
        }
        System.out.println("\n=== Battle Ends ===");
        if (player.isAlive()) {
            System.out.println(player.getName() + " Victory!");
            Item drop = enemy.calculateDrop();
            if (drop != null) {
                System.out.println("Obtained loot: " + drop.getName());
            }
        } else {
            System.out.println(player.getName() + " has been defeated!...");
        }
    }
}