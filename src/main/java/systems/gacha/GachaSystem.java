package systems.gacha;

import entities.items.*;
import entities.equipment.*;
import entities.characters.*;
import entities.characters.Character;
import util.fileio.FileHandler;


import java.util.*;

/**
 * Represents the gacha system in game.
 * Uses 2d-array for probability table, have pity systems for 4 and 5 star characters, stores pull history in String list.
 * Uses {@link PitySystem}
 *
 * @author Shaoyang Chen
 * @version 1.1.0
 *
 * @see PitySystem
 * @see Item
 * @see Character
 * @see Equipment
 */
public class GachaSystem {
    private double[][] probabilityTable;
    private int pityCounter5Star;
    private int pityCounter4Star;
    private boolean guaranteed5Star;
    private List<String> pullHistory;
    private List<String> characterPullHistory;
    private Random random;
    private PitySystem pitySystem;
    private PitySystem characterPitySystem;

    /**
     * Instantiates a new Gacha system (gacha system shouldn't have any args)
     * In default, 3 star item probability is 94.3%, 4 star item is 5.1%, 5 star item is 0.6%
     */
    public GachaSystem() {
        // probability table: rows: (3 star, 4 star, 5 star), 3 cols (base probability, UP probability, total probability
        probabilityTable = new double[3][3];
        probabilityTable[0][0] = 0.943;
        probabilityTable[1][0] = 0.051;
        probabilityTable[2][0] = 0.006;
        probabilityTable[0][2] = 0.943;
        probabilityTable[1][2] = 0.051;
        probabilityTable[2][2] = 0.006;
        Random random = new Random();

        pityCounter5Star = 0;
        pityCounter4Star = 0;
        guaranteed5Star = false;
        pullHistory = new ArrayList<>();
        characterPullHistory = new ArrayList<>();
        pitySystem = new PitySystem();
        characterPitySystem = new PitySystem();
    }

    /**
     * Pull single item
     *
     * @return the item pulled
     */
    public Item pullSingle() {
        System.out.println("=== Single Pull (Items) ===");
        // update pity first
        pityCounter5Star++;
        pityCounter4Star++;
        pitySystem.incrementPity();
        checkPity();
        updateProbabilities();
        int rarity = determineRarity();
        //get item
        Item item = getRandomItemByRarity(rarity);
        // pull history
        int temp = pullHistory.size() + 1;
        String record = "Single Pull Item,Pull: " + temp + ",5 Star Pity Count: " + pityCounter5Star +
        ",4 Star Pity Count: " + pityCounter4Star + ",Got Item: " + item.getName();
        pullHistory.add(record);
        if (rarity == 5) {
            pityCounter5Star = 0;
            pitySystem.resetPity(5);
            guaranteed5Star = false;
        }
        if (rarity == 4) {
            pityCounter4Star = 0;
            pitySystem.resetPity(4);
        }
        System.out.println("Pulled Item: " + item.getName() + " (" + comeOnJustStarrrrrrrrs(rarity) + ")");
        return item;
    }

    /**
     * Pull ten items
     *
     * @return the list of ten items
     */
    public List<Item> pullTen() {
        System.out.println("=== Ten Pulls (Items) ===");
        List<Item> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(pullSingle());
        }
        long fiveStarCount = results.stream()
                .filter(item -> {
                    if (item instanceof LightCone) {
                        return ((LightCone) item).getRequiredLevel() >= 60;
                    }
                    if (item instanceof MaterialItem) {
                        return ((MaterialItem) item).getRarity() >= 5;
                    }
                    return item.getValue() >= 1000;
                })
                .count();

        long fourStarCount = results.stream()
                .filter(item -> {
                    if (item instanceof LightCone) {
                        return ((LightCone) item).getRequiredLevel() < 60 && ((LightCone) item).getRequiredLevel() > 1;
                    }
                    if (item instanceof MaterialItem) {
                        int rarity = ((MaterialItem) item).getRarity();
                        return rarity >= 3 && rarity < 5;
                    }
                    return item.getValue() >= 400 && item.getValue() < 1000;
                })
                .count();

        System.out.println("Ten Pulls Stats: 5 Star items:" + fiveStarCount + ", 4 Star items: " + fourStarCount);
        return results;
    }

    /**
     * Pull single character from the pool
     *
     * @return the character pulled from the pool
     */
    public Character pullSingleCharacter() {
        System.out.println("=== Single Pull (Characters) ===");
        characterPitySystem.incrementPity();
        if (characterPitySystem.checkGuarantee()) {
            System.out.println("Character Pull Pity Used!");
        }
        int characterRarity = determineCharacterRarity();
        Character character = getRandomCharacterByRarity(characterRarity);
        int temp = characterPullHistory.size() + 1;
        String record = "Character Single Pull,Pull " + temp + ",Gained:" + character.getName() + ",Rarity: " + characterRarity;
        characterPullHistory.add(record);
        if (characterRarity == 5) {
            characterPitySystem.resetPity(5);
        } else if (characterRarity == 4) {
            characterPitySystem.resetPity(4);
        }
        System.out.println("Gained character: " + character.getName() + " (" + comeOnJustStarrrrrrrrs(characterRarity) + ")");
        character.displayInfo();
        return character;
    }

    /**
     * Pull ten character from the pool, the result will be in a list of characters
     *
     * @return the list of characters pulled
     */
    public List<Character> pullTenCharacter() {
        System.out.println("=== Ten Pulls (Characters) ===");
        List<Character> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(pullSingleCharacter());
        }
        // collect pull results
        long fiveStarCount = results.stream()
                .filter(character -> character instanceof FiveStarCharacter)
                .count();
        long fourStarCount = results.stream()
                .filter(character -> character instanceof FourStarCharacter)
                .count();

        System.out.println("Ten pull results - 5 Star Characters: " + fiveStarCount + ", 4 Star Characters: " + fourStarCount);
        return results;
    }

    /**
     * Determine character rarity when pulling
     * @return rarity of the character
     */
    private int determineCharacterRarity() {
        // 4-5 only since there isn't 1-3 star characters
        if (characterPitySystem.getFiveStarPity() >= 90) {
            return 5;
        }
        if (characterPitySystem.getFourStarPity() >= 10) {
            return 4;
        }
        double random = Math.random();
        double cumulative = 0.0;
        if (random < probabilityTable[2][2]) {
            return 5;
        }
        cumulative += probabilityTable[2][2];

        // 4 star character
        if (random < cumulative + probabilityTable[1][2]) {
            return 4;
        }

        // 4-5 only since there isn't 1-3 star characters
        return 4;
    }

    /**
     * Get random character based on a provided rarity (the pool of pulling)
     * @param rarity rarity of the desired character
     * @return result character after randomly picked
     */
    private Character getRandomCharacterByRarity(int rarity) {
        switch (rarity) {
            case 5:
                String[] fiveStarNames = {"姬子", "瓦尔特", "布洛妮娅", "杰帕德", "希儿", "景元"};
                return new FiveStarCharacter(fiveStarNames[random.nextInt(fiveStarNames.length)]);

            case 4:
            default:
                String[] fourStarNames = {"希露瓦", "佩拉", "卢卡", "虎克", "素裳", "阿兰"};
                return new FourStarCharacter(fourStarNames[random.nextInt(fourStarNames.length)], true);
        }
    }

    /**
     * Determine rarity when doing item pulls
     * @return rarity (3-5) pulled
     */
    private int determineRarity() {
        if (pityCounter5Star >= 90) {
            return 5;
        }
        if (pityCounter4Star >= 10) {
            return 4;
        }
        double random = Math.random();
        double cumulative = 0.0;
        if (random < probabilityTable[2][2]) {
            return 5;
        }
        cumulative += probabilityTable[2][2];
        if (random < cumulative + probabilityTable[1][2]) {
            return 4;
        }
        return 3;
    }

    private Item getRandomItemByRarity(int rarity) {
        switch (rarity) {
            case 5:
                // 50% of pulling lightcone, 50% of pulling material item
                if (random.nextBoolean()) {
                    String[] names = {"银河铁道之夜", "无可取代的东西", "但战斗还未结束"};
                    String[] paths = {"智识", "毁灭", "同谐"};
                    LightCone lc = new LightCone(names[random.nextInt(names.length)],
                            paths[random.nextInt(paths.length)]);
                    lc.setRequiredLevel(60);
                    lc.setValue(1000);
                    return lc;
                } else {
                    String[] names = {"传说材料", "史诗材料", "稀有材料"};
                    MaterialItem item = new MaterialItem(names[random.nextInt(names.length)],
                            "稀有材料", 5, 800);
                    return item;
                }
            case 4:
                // the same rule as 5 star
                if (random.nextBoolean()) {
                    String[] names = {"早餐的仪式感", "唯有沉默", "记忆中的模样"};
                    String[] paths = {"智识", "巡猎", "同谐"};
                    LightCone lc = new LightCone(names[random.nextInt(names.length)],
                            paths[random.nextInt(paths.length)]);
                    lc.setRequiredLevel(40);
                    lc.setValue(400);
                    return lc;
                } else {
                    String[] names = {"高级材料", "中级材料", "基础材料"};
                    MaterialItem item = new MaterialItem(names[random.nextInt(names.length)],
                            "普通材料", 4, 300);
                    return item;
                }

            case 3:
            default:
                String[] names = {"信用点", "冒险记录", "旅情见闻", "提纯以太"};
                String[] types = {"货币", "经验材料", "经验材料", "突破材料"};
                int[] rarities = {1, 1, 2, 3};
                int index = random.nextInt(names.length);
                return new MaterialItem(names[index],
                        types[index],
                        rarities[index],
                        100);
        }
    }

    /**
     * Check pity when pulling items
     */
    public void checkPity() {
        if (pityCounter5Star >= 90) {
            System.out.println("Activated 5 Star Pity System");
            guaranteed5Star = true;
        }
        if (pityCounter4Star >= 10) {
            System.out.println("Activated 4 Star Pity System");
        }
        if (pitySystem.checkGuarantee()) {
            System.out.println("Pity System Activated Pity");
        }
    }

    /**
     * Update probabilities for specific rarity items / characters.
     * 5 Star: add probability by 6% every pull after pity counter reached 75
     */
    public void updateProbabilities() {
        if (pityCounter5Star >= 75) {
            int softPityPulls = pityCounter5Star - 74;
            double increase = softPityPulls * 0.06;
            probabilityTable[2][2] = 0.006 + increase; // Base + bonus rate
            // Make sure it doesn't greater then 1
            if (probabilityTable[2][2] > 1.0) {
                probabilityTable[2][2] = 1.0;
            }
            probabilityTable[0][2] = 0.943 - (probabilityTable[2][2] - 0.006);
            probabilityTable[1][2] = 0.051; // 4 Star don't change
            double probability = probabilityTable[2][2] * 100;
            System.out.println("Soft Pity System Activated! Current Possibility For 5 Star Characters: "+ probability);
        } else {
            // reset to default when pity is less than 75
            probabilityTable[0][2] = 0.943;
            probabilityTable[1][2] = 0.051;
            probabilityTable[2][2] = 0.006;
        }
    }

    /**
     * Save history to file for Pull history
     */
    public void saveHistoryToFile() {
        FileHandler fileHandler = new FileHandler();
        // Combine character and item pull history
        String data = "=== All Pull History ===\n\n";
        data += "Item Pull History\n";
        data += "================\n";
        for (String record : pullHistory) {
            data += record + "\n";
        }

        data += "\nCharacter Pull History\n";
        data += "================\n";
        for (String record : characterPullHistory) {
            data += record + "\n";
        }

        data += "\n=== Statistics ===\n\n";
        data += "Item Pull Statistics\n";
        data += "Total Pulls: " + pullHistory.size() + "\n";
        data += "Current Pity Count For 5 Stars: " + pityCounter5Star + "/90\n";
        data += "Current Pity Count For 4 Stars: " + pityCounter4Star + "/10\n";
        data += "5 Star Guarantee Status " + guaranteed5Star + "\n";

        data += "\nCharacter Pull Statistics\n";
        data += "Total Pulls: " + characterPullHistory.size() + "\n";
        data += "Current Pity Count For 5 Stars: " + characterPitySystem.getFiveStarPity() + "/90\n";
        data += "Current Pity Count For 4 Stars: " + characterPitySystem.getFourStarPity() + "/10\n";
        data += "Character Guarantee Status: " + characterPitySystem.getGuaranteeFlag() + "\n";

        boolean success = fileHandler.exportToTXT(data, "gacha_history.txt");
        if (success) {
            System.out.println("Saved Pull history to File!");
        } else {
            System.out.println("Failed to save Pull history to File!");
        }
    }

    /**
     * Get probability table double [ ] [ ].
     *
     * @return the double [ ] [ ]
     */
// Getter方法
    public double[][] getProbabilityTable() { return probabilityTable; }

    /**
     * Gets pity counter 5 star.
     *
     * @return the pity counter 5 star
     */
    public int getPityCounter5Star() { return pityCounter5Star; }

    /**
     * Gets pity counter 4 star.
     *
     * @return the pity counter 4 star
     */
    public int getPityCounter4Star() { return pityCounter4Star; }

    /**
     * Is guaranteed 5 star boolean.
     *
     * @return the boolean
     */
    public boolean isGuaranteed5Star() { return guaranteed5Star; }

    /**
     * Gets pull history.
     *
     * @return the pull history
     */
    public List<String> getPullHistory() { return new ArrayList<>(pullHistory); }

    /**
     * Gets character pull history.
     *
     * @return the character pull history
     */
    public List<String> getCharacterPullHistory() { return new ArrayList<>(characterPullHistory); }

    /**
     * Gets pity system.
     *
     * @return the pity system
     */
    public PitySystem getPitySystem() { return pitySystem; }

    /**
     * Gets character pity system.
     *
     * @return the character pity system
     */
    public PitySystem getCharacterPitySystem() { return characterPitySystem; }

    /**
     * Print statistics.
     */
// 显示统计信息
    public void printStatistics() {
        System.out.println("=== 物品抽取统计 ===");
        System.out.println("总抽数: " + pullHistory.size());
        System.out.println("当前5星保底: " + pityCounter5Star + "/90");
        System.out.println("当前4星保底: " + pityCounter4Star + "/10");
        System.out.println("5星保底状态: " + (guaranteed5Star ? "触发" : "未触发"));

        System.out.println("\n=== 角色抽取统计 ===");
        System.out.println("总抽数: " + characterPullHistory.size());
        System.out.println("角色5星保底: " + characterPitySystem.getFiveStarPity() + "/90");
        System.out.println("角色4星保底: " + characterPitySystem.getFourStarPity() + "/10");
        System.out.println("角色保底标志: " + characterPitySystem.getGuaranteeFlag());

        // 计算概率
        if (pullHistory.size() > 0) {
            long fiveStarItems = pullHistory.stream()
                    .filter(record -> record.contains("5星保底:0"))
                    .count();
            System.out.println(String.format("\n物品5星出货率: %.2f%%",
                    (fiveStarItems * 100.0) / pullHistory.size()));
        }

        if (characterPullHistory.size() > 0) {
            long fiveStarCharacters = characterPullHistory.stream()
                    .filter(record -> record.contains("稀有度:5星"))
                    .count();
            System.out.println(String.format("角色5星出货率: %.2f%%",
                    (fiveStarCharacters * 100.0) / characterPullHistory.size()));
        }
    }

    private String comeOnJustStarrrrrrrrs (int numOfStars) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numOfStars; i++) {
            builder.append("★");
        }
        return builder.toString();
    }
}
