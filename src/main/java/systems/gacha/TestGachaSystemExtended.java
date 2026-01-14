package systems.gacha;

import entities.characters.Character;
import entities.items.Item;
import entities.characters.*;

import java.util.List;



//test gacha functionality, this class was written by AI, and it is for debug only
public class TestGachaSystemExtended {
    public static void main(String[] args) {
        System.out.println("=== 扩展的抽卡系统测试 ===\n");

        GachaSystem gacha = new GachaSystem();

        System.out.println("--- 测试物品抽取 ---");
        System.out.println("物品单抽5次：");
        for (int i = 0; i < 5; i++) {
            Item item = gacha.pullSingle();
            System.out.println("  获得: " + item.getName());
        }

        System.out.println("\n物品十连1次：");
        List<Item> items = gacha.pullTen();

        System.out.println("\n--- 测试角色抽取 ---");
        System.out.println("角色单抽3次：");
        for (int i = 0; i < 3; i++) {
            Character character = gacha.pullSingleCharacter();
            System.out.println("  获得角色: " + character.getName());
        }

        System.out.println("\n角色十连1次：");
        List<Character> characters = gacha.pullTenCharacter();

        System.out.println("\n--- 显示统计信息 ---");
        gacha.printStatistics();

        System.out.println("\n--- 测试保存记录 ---");
        // gacha.saveHistoryToFile();

        System.out.println("\n--- 测试保底系统 ---");
        System.out.println("物品保底系统：");
        System.out.println("5星保底计数器: " + gacha.getPitySystem().getFiveStarPity());
        System.out.println("4星保底计数器: " + gacha.getPitySystem().getFourStarPity());

        System.out.println("\n角色保底系统：");
        System.out.println("5星保底计数器: " + gacha.getCharacterPitySystem().getFiveStarPity());
        System.out.println("4星保底计数器: " + gacha.getCharacterPitySystem().getFourStarPity());
        System.out.println("软保底概率(80抽时): " +
                gacha.getCharacterPitySystem().calculateSoftPity() * 100 + "%");
    }
}