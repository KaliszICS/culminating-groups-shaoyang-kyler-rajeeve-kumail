package data;

import entities.characters.*;
import entities.characters.Character;
import entities.equipment.*;
import entities.items.*;
import systems.inventory.*;
import java.io.*;
import java.util.*;

public class CharacterData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Character> ownedCharacters;
    private Map<String, List<Item>> characterInventories;
    private Map<String, Equipment[]> equippedItems;
    private Map<String, Integer> characterLevels;
    private Map<String, Integer> characterExp;
    private Map<String, Integer> friendshipLevels;
    private Map<String, Boolean> unlockedSkills;
    private int totalGameTime;
    private Date lastPlayed;
    private String playerName;
    private int playerLevel;
    private int achievementsUnlocked;
    private int totalBattles;
    private int battlesWon;
    // game process (passed lvls)
    private int currentChapter;
    private int currentMission;
    private Set<String> completedMissions;
    private Set<String> unlockedAreas;
    private Map<String, Boolean> storyFlags;
    // player resouces
    private int stellarJade;
    private int credits;
    private int energy;
    // game stats
    private Map<String, Integer> itemUsageStats;
    private Map<String, Integer> skillUsageStats;
    private Map<String, Integer> enemyDefeatStats;

    public CharacterData() {
        this.ownedCharacters = new HashMap<>();
        this.characterInventories = new HashMap<>();
        this.equippedItems = new HashMap<>();
        this.characterLevels = new HashMap<>();
        this.characterExp = new HashMap<>();
        this.friendshipLevels = new HashMap<>();
        this.unlockedSkills = new HashMap<>();
        this.totalGameTime = 0;
        this.lastPlayed = new Date();
        this.playerName = "Trailblazer";
        this.playerLevel = 1;
        this.achievementsUnlocked = 0; //achieve systems implement in future (?)
        this.totalBattles = 0;
        this.battlesWon = 0;
        this.currentChapter = 1;
        this.currentMission = 1;
        this.completedMissions = new HashSet<>();
        this.unlockedAreas = new HashSet<>();
        this.storyFlags = new HashMap<>();
        // game start gift so the player can actually play
        this.stellarJade = 1600;
        this.credits = 10000;
        this.energy = 240;
        this.itemUsageStats = new HashMap<>();
        this.skillUsageStats = new HashMap<>();
        this.enemyDefeatStats = new HashMap<>();
        // Default Area
        unlockedAreas.add("Space Station 'Herta'");
        // Default Characters
        initializeDefaultCharacters();
    }
    // Getter/setter
    public Map<String, Character> getOwnedCharacters() { return ownedCharacters; }
    public Map<String, List<Item>> getCharacterInventories() { return characterInventories; }
    public Map<String, Equipment[]> getEquippedItems() { return equippedItems; }
    public Map<String, Integer> getCharacterLevels() { return characterLevels; }
    public Map<String, Integer> getCharacterExp() { return characterExp; }
    public Map<String, Integer> getFriendshipLevels() { return friendshipLevels; }

    public int getTotalGameTime() { return totalGameTime; }
    public void setTotalGameTime(int time) { this.totalGameTime = time; }

    public Date getLastPlayed() { return lastPlayed; }
    public void setLastPlayed(Date date) { this.lastPlayed = date; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String name) { this.playerName = name; }

    public int getPlayerLevel() { return playerLevel; }
    public void setPlayerLevel(int level) { this.playerLevel = level; }

    public int getAchievementsUnlocked() { return achievementsUnlocked; }
    public int getTotalBattles() { return totalBattles; }
    public int getBattlesWon() { return battlesWon; }
    public double getWinRate() {
        return totalBattles > 0 ? (double) battlesWon / totalBattles * 100 : 0;
    }

    public int getCurrentChapter() { return currentChapter; }
    public void setCurrentChapter(int chapter) { this.currentChapter = chapter; }

    public int getCurrentMission() { return currentMission; }
    public void setCurrentMission(int mission) { this.currentMission = mission; }

    public Set<String> getCompletedMissions() { return completedMissions; }
    public Set<String> getUnlockedAreas() { return unlockedAreas; }

    public int getStellarJade() { return stellarJade; }
    public int getCredits() { return credits; }
    public int getEnergy() { return energy; }

    public Map<String, Integer> getItemUsageStats() { return itemUsageStats; }
    public Map<String, Integer> getSkillUsageStats() { return skillUsageStats; }
    public Map<String, Integer> getEnemyDefeatStats() { return enemyDefeatStats; }

    private void initializeDefaultCharacters() {
        // add default character to players
        Character starter = new Character("Trailblazer", 1);
        addCharacter("Trailblazer", starter);
        FourStarCharacter march7th = new FourStarCharacter("March 7th", true);
        addCharacter("March 7th", march7th);
        FourStarCharacter danheng = new FourStarCharacter("Dan Heng", true);
        addCharacter("Dan Heng", danheng);
        // add equipment to characters
        LightCone starterLightCone = new LightCone("Meet You Soon", "Destruction");
        starterLightCone.setValue(100);
        starterLightCone.setRequiredLevel(1);
        // add to character inventory
        addItemToCharacter("Trailblazer", starterLightCone);
        equipItem("Trailblazer", starterLightCone);

        // unlock default skill to default characters
        unlockSkill("Trailblazer", 0);
        unlockSkill("March 7th", 0);
        unlockSkill("Dan Heng", 0);
    }

    public void addCharacter(String characterId, Character character) {
        ownedCharacters.put(characterId, character);
        characterLevels.put(characterId, character.getLevel());
        characterExp.put(characterId, character.getExperience());
        friendshipLevels.put(characterId, 1);
        // initialize character invent
        if (!characterInventories.containsKey(characterId)) {
            characterInventories.put(characterId, new ArrayList<>());
        }
        // initialize character equipment list
        if (!equippedItems.containsKey(characterId)) {
            equippedItems.put(characterId, new Equipment[4]);
        }

        System.out.println("Loaded Character: " + characterId);
    }

    // remove character shouldn't be used because you can't use it, but just in case it will be used

//    public boolean removeCharacter(String characterId) {
//        if (ownedCharacters.containsKey(characterId)) {
//            ownedCharacters.remove(characterId);
//            characterLevels.remove(characterId);
//            characterExp.remove(characterId);
//            friendshipLevels.remove(characterId);
//            characterInventories.remove(characterId);
//            equippedItems.remove(characterId);
//            System.out.println("Removed Character: " + characterId);
//            return true;
//        }
//        return false;
//    }

    public void addItemToCharacter(String characterId, Item item) {
        if (!characterInventories.containsKey(characterId)) {
            characterInventories.put(characterId, new ArrayList<>());
        }
        characterInventories.get(characterId).add(item);
        trackItemUsage(item.getName());

        System.out.println("Character " + characterId + " Obtained Item: " + item.getName());
    }

    public void removeItemFromCharacter(String characterId, Item item) {
        if (characterInventories.containsKey(characterId)) {
            characterInventories.get(characterId).remove(item);
        }
    }

    public boolean equipItem(String characterId, Equipment equipment) {
        if (!ownedCharacters.containsKey(characterId)) {
            System.out.println("Character not exist: " + characterId);
            return false;
        }
        Equipment[] slots = equippedItems.get(characterId);
        String slotType = equipment.getSlot();

        // Confirm slot index
        int slotIndex = getSlotIndex(slotType);
        if (slotIndex == -1) {
            System.out.println("Invalid slot: " + slotType);
            return false;
        }
        // Check for same slot equipment ?
        if (slots[slotIndex] != null) {
            System.out.println("Slot " + slotType + " have a equipment already: " + slots[slotIndex].getName());
            // unequip old then put on new
            unequipItem(characterId, slotType);
        }
        slots[slotIndex] = equipment;
        equippedItems.put(characterId, slots);
        // remove from inventory since it is equipped
        removeItemFromCharacter(characterId, equipment);
        System.out.println(characterId + " Equipped " + equipment.getName() + "!");
        return true;
    }

    public boolean unequipItem(String characterId, String slotType) {
        if (!ownedCharacters.containsKey(characterId)) {
            return false;
        }
        Equipment[] slots = equippedItems.get(characterId);
        int slotIndex = getSlotIndex(slotType);
        if (slotIndex == -1 || slots[slotIndex] == null) {
            return false;
        }
        Equipment equipment = slots[slotIndex];
        slots[slotIndex] = null;
        equippedItems.put(characterId, slots);
        // add to character inventory
        addItemToCharacter(characterId, equipment);

        System.out.println(characterId + " unequipped " + equipment.getName() + "!");
        return true;
    }

    private int getSlotIndex(String slotType) {
        switch (slotType.toLowerCase()) {
            case "lightcone": return 0;
            case "head": return 1;
            case "arm": return 2;
            case "body": return 3;
            case "leg": return 4;
            default: return -1;
        }
    }
    //check if lvled up

    public void levelUpCharacter(String characterId, int exp) {
        if (!characterExp.containsKey(characterId)) {
            return;
        }
        int currentExp = characterExp.get(characterId) + exp;
        int currentLevel = characterLevels.get(characterId);
        int expNeeded = currentLevel * 100;
        while (currentExp >= expNeeded) {
            currentExp -= expNeeded;
            currentLevel++;
            expNeeded = currentLevel * 100;
            System.out.println(characterId + " upgraded to level " + currentLevel + "!");
        }
        characterExp.put(characterId, currentExp);
        characterLevels.put(characterId, currentLevel);
    }

    public void increaseFriendship(String characterId, int amount) {
        if (friendshipLevels.containsKey(characterId)) {
            int currentFriendship = friendshipLevels.get(characterId);
            currentFriendship += amount;
            friendshipLevels.put(characterId, currentFriendship);

            System.out.println(characterId + " friendship level increased " + amount + "! Current friendship level: " + currentFriendship);
        }
    }

    public void unlockSkill(String characterId, int skillId) {
        String key = characterId + "_skill_" + skillId;
        unlockedSkills.put(key, true);

        System.out.println("Unlocked " + characterId + "'s skill " + skillId);
    }

    public boolean isSkillUnlocked(String characterId, int skillId) {
        String key = characterId + "_skill_" + skillId;
        return unlockedSkills.getOrDefault(key, false);
    }

    private void trackItemUsage(String itemName) {
        itemUsageStats.put(itemName, itemUsageStats.getOrDefault(itemName, 0) + 1);
    }

    public void trackSkillUsage(String skillName) {
        skillUsageStats.put(skillName, skillUsageStats.getOrDefault(skillName, 0) + 1);
    }

    public void trackEnemyDefeat(String enemyType) {
        enemyDefeatStats.put(enemyType, enemyDefeatStats.getOrDefault(enemyType, 0) + 1);
    }

    public void completeMission(String missionId) {
        completedMissions.add(missionId);
        achievementsUnlocked++;
        // rewards when complete a mission
        credits += 5000;
        stellarJade += 100;
        System.out.println("Completed mission: " + missionId);
        System.out.println("Rewards: 5000 credits, 100 stellarJade");
    }

    public void unlockArea(String areaName) {
        unlockedAreas.add(areaName);
        System.out.println("Unlocked new area: " + areaName);
    }

    public void setStoryFlag(String flag, boolean value) {
        storyFlags.put(flag, value);
    }

    public boolean getStoryFlag(String flag) {
        return storyFlags.getOrDefault(flag, false);
    }

    public void addStellarJade(int amount) {
        stellarJade += amount;
        System.out.println("Acquired " + amount + " Stellar Jades, Amount remaining: " + stellarJade);
    }

    public boolean spendStellarJade(int amount) {
        if (stellarJade >= amount) {
            stellarJade -= amount;
            System.out.println("Used " + amount + " Stellar Jades, Amount remaining: " + stellarJade);
            return true;
        }
        return false;
    }

    public void addCredits(int amount) {
        credits += amount;
        System.out.println("Acquired " + amount + " Credits, Amount remaining: " + credits);
    }

    public boolean spendCredits(int amount) {
        if (credits >= amount) {
            credits -= amount;
            System.out.println("Used " + amount + " Credits, Amount remaining: " + credits);
            return true;
        }
        return false;
    }

    public void addEnergy(int amount) {
        energy += amount;
        energy = Math.min(energy, 240); // max energy is 240
        System.out.println("Added " + amount + " Energy, Current: " + energy);
    }

    public boolean spendEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            System.out.println("Used " + amount + " Energy, Remaining: " + energy);
            return true;
        }
        return false;
    }

    public void recordBattle(boolean won) {
        totalBattles++;
        if (won) {
            battlesWon++;
        }
    }


    public void printStatistics() {
        System.out.println("=== Game Stats ===");
        System.out.println("Player: " + playerName);
        System.out.println("Level: " + playerLevel);
        System.out.println("Game Time: " + totalGameTime + " mins");
        System.out.println("Last Played: " + lastPlayed);
        System.out.println("Achievement Unlocked: " + achievementsUnlocked);
        System.out.println("Total Battles: " + totalBattles);
        System.out.println("Battles Won: " + battlesWon);
        System.out.println("Owned Characters: " + ownedCharacters.size());
        System.out.println("Stellar Jades: " + stellarJade);
        System.out.println("Credits: " + credits);
        System.out.println("Energy: " + energy);
    }

    public void printCharacterDetails(String characterId) {
        if (!ownedCharacters.containsKey(characterId)) {
            System.out.println("Character isn't exist: " + characterId);
            return;
        }

        Character character = ownedCharacters.get(characterId);
        System.out.println("=== Character Info: " + characterId + " ===");
        character.displayInfo();

        int level = characterLevels.getOrDefault(characterId, 1);
        int exp = characterExp.getOrDefault(characterId, 0);
        int friendship = friendshipLevels.getOrDefault(characterId, 1);

        System.out.println("Levels: " + level);
        System.out.println("Exp.: " + exp + "/" + (level * 100));
        System.out.println("Friendship lvl: " + friendship);

        // display equip
        Equipment[] equipped = equippedItems.get(characterId);
        if (equipped != null) {
            System.out.println("Equipments:");
            String[] slotNames = {"LightCone", "Head", "Arm", "Body", "Leg"};
            for (int i = 0; i < equipped.length; i++) {
                System.out.print("  " + slotNames[i] + ": ");
                if (equipped[i] != null) {
                    System.out.println(equipped[i].getName());
                } else {
                    System.out.println("Empty");
                }
            }
        }

        // display inventory
        List<Item> inventory = characterInventories.get(characterId);
        if (inventory != null && !inventory.isEmpty()) {
            System.out.println("Inventory (Tot. " + inventory.size() + " items):");
            for (int i = 0; i < Math.min(inventory.size(), 10); i++) {
                System.out.println("  " + (i+1) + ". " + inventory.get(i).getName());
            }
            if (inventory.size() > 10) {
                System.out.println("  ... There are " + (inventory.size() - 10) + " more items");
            }
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            System.out.println("Saved gamedata to: " + filename);
        }
    }

    public static CharacterData loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            CharacterData data = (CharacterData) ois.readObject();
            data.setLastPlayed(new Date());
            System.out.println("Game data loaded: " + filename);
            return data;
        }
    }
}