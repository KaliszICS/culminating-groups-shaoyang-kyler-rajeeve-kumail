package entities;

import entities.characters.Character;
import entities.characters.*;
import entities.equipment.*;
import entities.items.*;
import entities.enemies.*;


public class TestEntities {
    public static void main(String[] args) {
        System.out.println("=== 星穹铁道实体类测试 ===\n");

        // 测试角色
        testCharacters();
        System.out.println();

        // 测试敌人
        testEnemies();
        System.out.println();

        // 测试物品
        testItems();
        System.out.println();

        // 测试装备
        testEquipment();
        System.out.println();

        // 测试战斗
        testBattle();
    }

    private static void testCharacters() {
        System.out.println("--- 角色测试 ---");

        // 创建五星角色
        FiveStarCharacter fiveStar = new FiveStarCharacter("景元");
        fiveStar.setSignatureWeapon("拂晓之前");
        fiveStar.displayInfo();
        fiveStar.useUltimate();

        System.out.println();

        // 创建四星角色
        FourStarCharacter fourStar = new FourStarCharacter("素裳", true);
        fourStar.displayInfo();
        fourStar.checkAvailability();

        System.out.println();

        // 测试升级
        Character character = new Character("丹恒", 10);
        character.addExperience(500);
        character.displayInfo();
    }

    private static void testEnemies() {
        System.out.println("--- 敌人测试 ---");

        // 普通敌人
        RegularEnemy regular = new RegularEnemy("虚卒·掠夺者", false);
        regular.displayInfo();
        regular.useSkill();

        System.out.println();

        // Boss敌人
        BossEnemy boss = new BossEnemy("末日兽", 3);
        boss.displayInfo();
        boss.useSpecialAttack();

        // 测试阶段转换
        boss.takeDamage(200);
    }

    private static void testItems() {
        System.out.println("--- 物品测试 ---");

        // 消耗品
        ConsumableItem potion = new ConsumableItem("治疗药水", 5, 60, 100);
        potion.displayInfo();
        potion.use();

        System.out.println();

        // 材料
        MaterialItem material1 = new MaterialItem("信用点", "普通材料", 1, 1000);
        MaterialItem material2 = new MaterialItem("遗失的光尘", "稀有材料", 3, 5000);

        material1.displayInfo();
        System.out.println();
        material2.displayInfo();

        // 测试合成
        MaterialItem combined = material2.combine(
                new MaterialItem("遗失的光尘", "稀有材料", 3, 5000)
        );
    }

    private static void testEquipment() {
        System.out.println("--- 装备测试 ---");

        // 光锥
        LightCone lightCone = new LightCone("银河铁道之夜", "智识");
        lightCone.displayInfo();
        lightCone.activateAbility();
        lightCone.calculateStats();

        System.out.println();

        // 遗器
        Relic relic = new Relic("快枪手的野穗毡帽", "快枪手");
        relic.setSlot("头部");
        relic.displayInfo();
        relic.checkSetBonus(2);
    }

    private static void testBattle() {
        System.out.println("--- 战斗测试 ---");

        // 创建角色和敌人
        PlayableCharacter player = new PlayableCharacter("开拓者", 5, true);
        RegularEnemy enemy = new RegularEnemy("虚卒·践踏者", true);

        // 显示初始状态
        System.out.println("战斗开始！");
        System.out.println("玩家: " + player.getName() + " HP: " + player.getCurrentHP());
        System.out.println("敌人: " + enemy.getName() + " HP: " + enemy.getCurrentHP());

        // 战斗循环
        for (int round = 1; round <= 3; round++) {
            System.out.println("\n=== 第 " + round + " 回合 ===");

            // 玩家攻击
            player.attack(enemy);

            // 如果敌人还活着，攻击玩家
            if (enemy.isAlive()) {
                enemy.attack(player);
            } else {
                System.out.println("敌人被击败！");
                break;
            }
        }

        // 战斗结果
        System.out.println("\n=== 战斗结束 ===");
        if (player.isAlive()) {
            System.out.println(player.getName() + " 胜利！");
            Item drop = enemy.calculateDrop();
            if (drop != null) {
                System.out.println("获得战利品: " + drop.getName());
            }
        } else {
            System.out.println(player.getName() + " 被击败了...");
        }
    }
}