package data;

import entities.characters.*;
import entities.characters.Character;
import entities.equipment.*;
import entities.items.*;
import systems.inventory.*;
import java.io.*;
import java.util.*;

/**
 * Represents character data in game, including all attributes of the character and also game history for the character
 * Implements {@link Serializable} there for all things here can be written to a file (updated on emerg. 2026/-1/13)
 *
 * @author Shaoyang Chen
 * @version 1.0.18.E1
 *
 * @see Serializable
 */
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

    /**
     * Instantiates a default character with no provided args.
     * This should be only used when you are trying to create the default player character Trailblazer
     *
     * @see Character
     */
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
        this.achievementsUnlocked = 0; // achievement WIP
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

    /**
     * Gets a map(table) of owned characters
     *
     * @return the owned characters map
     */

    public Map<String, Character> getOwnedCharacters() { return ownedCharacters; }

    /**
     * Gets a map(table) of character inventories.
     *
     * @return the character's name and inventories
     */
    public Map<String, List<Item>> getCharacterInventories() { return characterInventories; }

    /**
     * Gets equipped items of the character
     *
     * @return the equipped items in a map
     */
    public Map<String, Equipment[]> getEquippedItems() { return equippedItems; }

    /**
     * Gets character's levels
     *
     * @return the character levels
     */
    public Map<String, Integer> getCharacterLevels() { return characterLevels; }

    /**
     * Gets character exp remaining
     *
     * @return the character exp
     */
    public Map<String, Integer> getCharacterExp() { return characterExp; }

    /**
     * Gets friendship levels.
     *
     * @return the friendship levels
     */
    public Map<String, Integer> getFriendshipLevels() { return friendshipLevels; }

    /**
     * Gets total game time.
     *
     * @return the total game time
     */
    public int getTotalGameTime() { return totalGameTime; }

    /**
     * Sets total game time.
     *
     * @param time the time
     */
    public void setTotalGameTime(int time) { this.totalGameTime = time; }

    /**
     * Gets last played.
     *
     * @return the last played
     */
    public Date getLastPlayed() { return lastPlayed; }

    /**
     * Sets last played.
     *
     * @param date the date
     */
    public void setLastPlayed(Date date) { this.lastPlayed = date; }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public String getPlayerName() { return playerName; }

    /**
     * Sets player name.
     *
     * @param name the name
     */
    public void setPlayerName(String name) { this.playerName = name; }

    /**
     * Gets player level.
     *
     * @return the player level
     */
    public int getPlayerLevel() { return playerLevel; }

    /**
     * Sets player level.
     *
     * @param level the level
     */
    public void setPlayerLevel(int level) { this.playerLevel = level; }

    /**
     * Gets achievements unlocked.
     *
     * @return the achievements unlocked
     */
    public int getAchievementsUnlocked() { return achievementsUnlocked; }

    /**
     * Gets total battles.
     *
     * @return the total battles
     */
    public int getTotalBattles() { return totalBattles; }

    /**
     * Gets battles won.
     *
     * @return the battles won
     */
    public int getBattlesWon() { return battlesWon; }

    /**
     * Gets win rate for player
     *
     * @return the win rate for player
     */
    public double getWinRate() {
        return totalBattles > 0 ? (double) battlesWon / totalBattles * 100 : 0;
    }

    /**
     * Gets current chapter.
     *
     * @return the current chapter
     */
    public int getCurrentChapter() { return currentChapter; }

    /**
     * Sets current chapter.
     *
     * @param chapter the chapter
     */
    public void setCurrentChapter(int chapter) { this.currentChapter = chapter; }

    /**
     * Gets current mission.
     *
     * @return the current mission
     */
    public int getCurrentMission() { return currentMission; }

    /**
     * Sets current mission.
     *
     * @param mission the mission
     */
    public void setCurrentMission(int mission) { this.currentMission = mission; }

    /**
     * Gets completed missions.
     *
     * @return the completed missions
     */
    public Set<String> getCompletedMissions() { return completedMissions; }

    /**
     * Gets unlocked areas.
     *
     * @return the unlocked areas
     */
    public Set<String> getUnlockedAreas() { return unlockedAreas; }

    /**
     * Gets stellar jade.
     *
     * @return the stellar jade
     */
    public int getStellarJade() { return stellarJade; }

    /**
     * Gets credits.
     *
     * @return the credits
     */
    public int getCredits() { return credits; }

    /**
     * Gets energy.
     *
     * @return the energy
     */
    public int getEnergy() { return energy; }

    /**
     * Gets item usage stats.
     *
     * @return the item usage stats
     */
    public Map<String, Integer> getItemUsageStats() { return itemUsageStats; }

    /**
     * Gets skill usage stats.
     *
     * @return the skill usage stats
     */
    public Map<String, Integer> getSkillUsageStats() { return skillUsageStats; }

    /**
     * Gets enemy defeat stats.
     *
     * @return the enemy defeat stats
     */
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

    /**
     * Add characters to list with character name and character data
     *
     * @param characterId the character name
     * @param character   the character data
     */
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

    /**
     * Add item to character.
     *
     * @param characterId the character id
     * @param item        the item to add
     */
    public void addItemToCharacter(String characterId, Item item) {
        if (!characterInventories.containsKey(characterId)) {
            characterInventories.put(characterId, new ArrayList<>());
        }
        characterInventories.get(characterId).add(item);
        trackItemUsage(item.getName());

        System.out.println("Character " + characterId + " Obtained Item: " + item.getName());
    }

    /**
     * Remove item from character.
     *
     * @param characterId the character id
     * @param item        the item to remove
     */
    public void removeItemFromCharacter(String characterId, Item item) {
        if (characterInventories.containsKey(characterId)) {
            characterInventories.get(characterId).remove(item);
        }
    }

    /**
     * Equip item to a character, also returns a boolean indicating the equipping process
     *
     * @param characterId the character id
     * @param equipment   the equipment to equip
     * @return the boolean indicating equip status
     */
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

    /**
     * Unequip item from a character with provided character name and slot, also returns a boolean indicating the unequipping process
     *
     * @param characterId the character id
     * @param slotType    the slot type
     * @return the boolean indicating unequip status
     */
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

    /**
     * Level up character by a specific experience amount
     *
     * @param characterId the character name
     * @param exp         the exp to add to character
     */
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

    /**
     * Increase friendship of character by provided amount
     *
     * @param characterId the character name
     * @param amount      the amount to add
     */
    public void increaseFriendship(String characterId, int amount) {
        if (friendshipLevels.containsKey(characterId)) {
            int currentFriendship = friendshipLevels.get(characterId);
            currentFriendship += amount;
            friendshipLevels.put(characterId, currentFriendship);

            System.out.println(characterId + " friendship level increased " + amount + "! Current friendship level: " + currentFriendship);
        }
    }

    /**
     * Unlock skill for character with name and skill id
     *
     * @param characterId the character name
     * @param skillId     the skill id
     */
    public void unlockSkill(String characterId, int skillId) {
        String key = characterId + "_skill_" + skillId;
        unlockedSkills.put(key, true);

        System.out.println("Unlocked " + characterId + "'s skill " + skillId);
    }

    /**
     * Check if a specific skill is unlocked
     *
     * @param characterId the character id
     * @param skillId     the skill id
     * @return the boolean indicating whether the skill is unlocked or not
     */
    public boolean isSkillUnlocked(String characterId, int skillId) {
        String key = characterId + "_skill_" + skillId;
        return unlockedSkills.getOrDefault(key, false);
    }

    private void trackItemUsage(String itemName) {
        itemUsageStats.put(itemName, itemUsageStats.getOrDefault(itemName, 0) + 1);
    }

    /**
     * Track skill usage.
     *
     * @param skillName the skill name
     */
    public void trackSkillUsage(String skillName) {
        skillUsageStats.put(skillName, skillUsageStats.getOrDefault(skillName, 0) + 1);
    }

    /**
     * Track whether the enemy is defeated or not
     *
     * @param enemyType the enemy type
     */
    public void trackEnemyDefeat(String enemyType) {
        enemyDefeatStats.put(enemyType, enemyDefeatStats.getOrDefault(enemyType, 0) + 1);
    }

    /**
     * Complete mission.
     *
     * @param missionId the mission id
     */
    public void completeMission(String missionId) {
        completedMissions.add(missionId);
        achievementsUnlocked++;
        // rewards when complete a mission
        credits += 5000;
        stellarJade += 150;
        System.out.println("Completed mission: " + missionId);
        System.out.println("Rewards: 5000 credits, 150 stellarJade");
    }

    /**
     * Unlock area for character to explore
     *
     * @param areaName the area name
     */
    public void unlockArea(String areaName) {
        unlockedAreas.add(areaName);
        System.out.println("Unlocked new area: " + areaName);
    }

    /**
     * Sets story flag.
     *
     * @param flag  the flag
     * @param value the value
     */
    public void setStoryFlag(String flag, boolean value) {
        storyFlags.put(flag, value);
    }

    /**
     * Gets story flag.
     *
     * @param flag the flag
     * @return the story flag
     */
    public boolean getStoryFlag(String flag) {
        return storyFlags.getOrDefault(flag, false);
    }

    /**
     * Add stellar jade.
     *
     * @param amount the amount
     */
    public void addStellarJade(int amount) {
        stellarJade += amount;
        System.out.println("Acquired " + amount + " Stellar Jades, Amount remaining: " + stellarJade);
    }

    /**
     * Spend stellar jade, will also return the status indicating whether the process was successful
     *
     * @param amount the amount to spend
     * @return the boolean indicating whether it have sufficient amount
     */
    public boolean spendStellarJade(int amount) {
        if (stellarJade >= amount) {
            stellarJade -= amount;
            System.out.println("Used " + amount + " Stellar Jades, Amount remaining: " + stellarJade);
            return true;
        }
        return false;
    }

    /**
     * Add credits to player
     *
     * @param amount the amount
     */
    public void addCredits(int amount) {
        credits += amount;
        System.out.println("Acquired " + amount + " Credits, Amount remaining: " + credits);
    }

    /**
     * Spend credits boolean, will also return the status indicating whether the process was successful
     *
     * @param amount the amount to spend
     * @return the boolean indicating whether it have sufficient amount
     */
    public boolean spendCredits(int amount) {
        if (credits >= amount) {
            credits -= amount;
            System.out.println("Used " + amount + " Credits, Amount remaining: " + credits);
            return true;
        }
        return false;
    }

    /**
     * Add energy to player, note that the max for character energy is 240
     *
     * @param amount the amount to add
     */
    public void addEnergy(int amount) {
        energy += amount;
        energy = Math.min(energy, 240); // max energy is 240
        System.out.println("Added " + amount + " Energy, Current: " + energy);
    }

    /**
     * Spend energy from the character, will also return the status indicating whether the process was successful
     *
     * @param amount the amount spend
     * @return the boolean indicating whether it have sufficient amount
     */
    public boolean spendEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            System.out.println("Used " + amount + " Energy, Remaining: " + energy);
            return true;
        }
        return false;
    }

    /**
     * Record battle win (specifically to record win rate)
     *
     * @param won status of the battle, true for win
     */
    public void recordBattle(boolean won) {
        totalBattles++;
        if (won) {
            battlesWon++;
        }
    }


    /**
     * Print statistics for the game, include player stats, win rates, owned characters, and currencies in game
     */
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

    /**
     * Print character details with provided character name
     *
     * @param characterId the character id
     */
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

        // display inventory of the character
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

    /**
     * Save everything to a file
     *
     * @param filename the filename
     * @throws IOException IOException if something is wrong during the saving process
     */
    public void saveToFile(String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            System.out.println("Saved gamedata to: " + filename);
        }
    }

    /**
     * Load from file for character data only
     *
     * @param filename the filename to read
     * @return the character data read from the save
     * @throws IOException            IOException when something is wrong during the reading process
     * @throws ClassNotFoundException the class not found exception throws when something in the file is unexcepted
     *
     * @see IOException
     * @see ClassNotFoundException
     */
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
