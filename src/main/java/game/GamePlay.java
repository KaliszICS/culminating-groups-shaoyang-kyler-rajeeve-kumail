package game;

import data.CharacterData;
import data.config.ConfigManager;
import data.save.GameData;
import data.save.SaveManager;
import entities.characters.Character;
import entities.characters.FiveStarCharacter;
import entities.enemies.BossEnemy;
import entities.enemies.Enemy;
import systems.battle.BattleSystem;
import entities.enemies.RegularEnemy;
import entities.equipment.Equipment;
import entities.items.Item;
import systems.battle.Damage;
import systems.gacha.GachaSystem;
import systems.inventory.Inventory;
import util.fileio.FileHandler;

import java.util.*;

/**
 * Represents gameplay for this game. This is the class which user interacts with.
 * Include primary user interfaces and also handles incorrect commands from the user.
 *
 * @author Shaoyang Chen
 * @version 1.2.0
 */
public class GamePlay {
    private CharacterData characterData;
    private GachaSystem gachaSystem;
    private Inventory playerInventory;
    private ConfigManager configManager;
    private FileHandler fileHandler;
    private GameData currentGameData;
    private SaveManager saveManager;
    private Scanner scanner;
    private boolean gameRunning;
    private Random random;
    private BattleSystem battleSystem;
    private static final String LOGO =
            "╔═══════════════════════════════════════════════════════════════╗\n" +
                    "║         ███████╗████████╗ █████╗ ██████╗  █████╗ ██╗██╗       ║\n" +
                    "║         ██╔════╝╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗██║██║       ║\n" +
                    "║         ███████╗   ██║   ███████║██████╔╝███████║██║██║       ║\n" +
                    "║         ╚════██║   ██║   ██╔══██║██╔══██╗██╔══██║██║██║       ║\n" +
                    "║         ███████║   ██║   ██║  ██║██║  ██║██║  ██║██║███████╗  ║\n" +
                    "║         ╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚══════╝  ║\n" +
                    "║                                                               ║\n" +
                    "║                   STAR RAIL DESTINY CROSSING                  ║\n" +
                    "╚═══════════════════════════════════════════════════════════════╝\n";

    /**
     * Initialize game play, load game, load all info required, load configs and saves if applicable
     */
    public GamePlay() {
        this.scanner = new Scanner(System.in);
        this.gameRunning = true;
        this.random = new Random();
        this.fileHandler = new FileHandler();
        this.configManager = new ConfigManager();
        this.gachaSystem = new GachaSystem();
        this.playerInventory = new Inventory(1000);
        this.saveManager = new SaveManager();
        this.currentGameData = new GameData();
        this.battleSystem = new BattleSystem();

        loadGameData();
        System.out.println("Game initialized!");
    }

    private void loadGameData() {
        System.out.print("Load saved game? (y/n): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("y") || choice.equals("yes")) {
            List<String> saves = saveManager.listSaves();
            if (saves.isEmpty()) {
                System.out.println("No save files found. Starting new game...");
                characterData = new CharacterData();
                currentGameData = new GameData();
                currentGameData.setCharacterData(characterData);
            } else {
                System.out.println("Available saves:");
                for (int i = 0; i < saves.size(); i++) {
                    System.out.println((i + 1) + ". " + saves.get(i));
                }
                System.out.print("Select save slot: ");
                try {
                    int slot = Integer.parseInt(scanner.nextLine());
                    currentGameData = saveManager.loadGame(slot);
                    if (currentGameData != null) {
                        // Fixed Character Data properly install. update: 2026/1/13
                        characterData = currentGameData.getCharacterData();
                        if (characterData == null) {
                            System.out.println("Warning: No character data found in save. Initializing new character data...");
                            characterData = new CharacterData();
                            currentGameData.setCharacterData(characterData);
                        }
                        System.out.println("Game loaded successfully!");
                        System.out.println("Loaded " + characterData.getOwnedCharacters().size() + " characters");
                    } else {
                        System.out.println("Failed to load save. Starting new game...");
                        characterData = new CharacterData();
                        currentGameData = new GameData();
                        currentGameData.setCharacterData(characterData);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Starting new game...");
                    characterData = new CharacterData();
                    currentGameData = new GameData();
                    currentGameData.setCharacterData(characterData);
                }
            }
        } else {
            System.out.println("Starting new game...");
            characterData = new CharacterData();
            currentGameData = new GameData();
            currentGameData.setCharacterData(characterData);
        }
    }


    /**
     * Prints out the start screen of the game
     */
    public void start() {
        clearScreen();
        System.out.println(LOGO);
        System.out.println("\nWelcome to Star Rail!");
        System.out.println("The journey begins now...\n");

        while (gameRunning) {
            showMainMenu();
        }

        System.out.println("Thank you for playing Star Rail!");
    }

    private void showMainMenu() {
        clearScreen();
        System.out.println(LOGO);
        System.out.println("╔═══════════════════════════════════════════════════════════╗\n" +
                "║                        MAIN MENU                          ║\n" +
                "╠═══════════════════════════════════════════════════════════╣\n" +
                "║ 1. Continue Adventure                                     ║\n" +
                "║ 2. Character Management                                   ║\n" +
                "║ 3. Gacha System                                           ║\n" +
                "║ 4. Battle Simulation                                      ║\n" +
                "║ 5. Inventory Management                                   ║\n" +
                "║ 6. Game Settings                                          ║\n" +
                "║ 7. Save/Load Game                                         ║\n" +
                "║ 8. Game Statistics                                        ║\n" +
                "║ 9. Exit Game                                              ║\n" +
                "╚═══════════════════════════════════════════════════════════╝\n");

        System.out.print("Select option (1-9): ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                adventureMenu();
                break;
            case "2":
                characterMenu();
                break;
            case "3":
                gachaMenu();
                break;
            case "4":
                battleMenu();
                break;
            case "5":
                inventoryMenu();
                break;
            case "6":
                settingsMenu();
                break;
            case "7":
                saveMenu();
                break;
            case "8":
                showStatistics();
                break;
            case "9":
                exitGame();
                break;
            default:
                System.out.println("Invalid choice!");
                pause(1);
        }
    }

    private void adventureMenu() {
        clearScreen();
        System.out.println("=== ADVENTURE MODE ===");
        System.out.println("Current Chapter: " + characterData.getCurrentChapter());
        System.out.println("Current Mission: " + characterData.getCurrentMission());
        System.out.println("\n1. Start Current Mission");
        System.out.println("2. View Story Summary");
        System.out.println("3. Return to Main Menu");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            startMission();
        } else if (choice.equals("2")) {
            showStorySummary();
        }
    }

    private void startMission() {
        System.out.println("\n=== MISSION START ===");

        if (!characterData.spendEnergy(30)) {
            System.out.println("Not enough energy! Need 30 energy.");
            return;
        }

        System.out.println("Mission in progress...");
        pause(3);

        boolean success = random.nextDouble() > 0.3;

        if (success) {
            System.out.println("Mission completed!");
            String missionId = "chapter_" + characterData.getCurrentChapter() +
                    "_mission_" + characterData.getCurrentMission();
            characterData.completeMission(missionId);

            int expReward = 100 * characterData.getCurrentChapter();
            for (String charId : characterData.getOwnedCharacters().keySet()) {
                characterData.levelUpCharacter(charId, expReward);
            }

            if (characterData.getCurrentMission() < 5) {
                characterData.setCurrentMission(characterData.getCurrentMission() + 1);
            } else {
                characterData.setCurrentChapter(characterData.getCurrentChapter() + 1);
                characterData.setCurrentMission(1);
                System.out.println("Chapter " + (characterData.getCurrentChapter() - 1) + " completed!");
                characterData.addStellarJade(600);
            }
        } else {
            System.out.println("Mission failed! Try again.");
        }

        characterData.recordBattle(success);
        pause(2);
    }

    private void characterMenu() {
        clearScreen();
        System.out.println("=== CHARACTER MANAGEMENT ===");
        System.out.println("1. View All Characters");
        System.out.println("2. View Character Details");
        System.out.println("3. Level Up Character");
        System.out.println("4. Equipment Management");
        System.out.println("5. Return to Main Menu");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                showAllCharacters();
                break;
            case "2":
                showCharacterDetails();
                break;
            case "3":
                levelUpCharacter();
                break;
            case "4":
                equipmentMenu();
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice!");
        }

        pause(2);
    }

    private void showAllCharacters() {
        clearScreen();
        System.out.println("=== OWNED CHARACTERS ===");

        Map<String, Character> characters = characterData.getOwnedCharacters();
        if (characters.isEmpty()) {
            System.out.println("No characters owned!");
            return;
        }

        int i = 1;
        for (Map.Entry<String, Character> entry : characters.entrySet()) {
            Character character = entry.getValue();
            int level = characterData.getCharacterLevels().getOrDefault(entry.getKey(), 1);
            int friendship = characterData.getFriendshipLevels().getOrDefault(entry.getKey(), 1);

            System.out.println(i + ". " + entry.getKey());
            System.out.println("   Level: " + level + " | Friendship: " + friendship +
                    " | HP: " + character.getCurrentHP() + "/" + character.getMaxHP());
            i++;
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void showCharacterDetails() {
        System.out.print("Enter character name: ");
        String name = scanner.nextLine();

        characterData.printCharacterDetails(name);

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void levelUpCharacter() {
        System.out.print("Enter character name to level up: ");
        String name = scanner.nextLine();

        if (!characterData.getOwnedCharacters().containsKey(name)) {
            System.out.println("Character not found!");
            return;
        }

        System.out.print("Enter experience amount: ");
        try {
            int exp = Integer.parseInt(scanner.nextLine());
            characterData.levelUpCharacter(name, exp);
            System.out.println("Character leveled up!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid experience amount!");
        }
    }

    private void equipmentMenu() {
        clearScreen();
        System.out.println("=== EQUIPMENT MANAGEMENT ===");
        System.out.println("1. Equip Item");
        System.out.println("2. Unequip Item");
        System.out.println("3. View Character Equipment");
        System.out.println("4. Return");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                equipItem();
                break;
            case "2":
                unequipItem();
                break;
            case "3":
                viewEquipment();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void equipItem() {
        System.out.print("Enter character name: ");
        String charName = scanner.nextLine();

        System.out.print("Enter equipment name: ");
        String equipName = scanner.nextLine();
        Equipment dummyEquip = new Equipment(equipName, "Weapon", 1) {
            @Override
            public void calculateStats() {
                System.out.println("Calculating stats...");
            }
        };

        boolean success = characterData.equipItem(charName, dummyEquip);
        if (success) {
            System.out.println("Equipment equipped!");
        }
    }

    private void unequipItem() {
        System.out.print("Enter character name: ");
        String charName = scanner.nextLine();

        System.out.print("Enter slot to unequip: ");
        String slot = scanner.nextLine();

        characterData.unequipItem(charName, slot);
    }

    private void viewEquipment() {
        System.out.print("Enter character name: ");
        String charName = scanner.nextLine();

        Equipment[] equipped = characterData.getEquippedItems().get(charName);
        if (equipped == null) {
            System.out.println("No equipment found!");
            return;
        }

        System.out.println("Equipment for " + charName + ":");
        String[] slotNames = {"LightCone", "Head", "Arm", "Body", "Leg"};
        for (int i = 0; i < equipped.length && i < slotNames.length; i++) {
            System.out.print(slotNames[i] + ": ");
            if (equipped[i] != null) {
                System.out.println(equipped[i].getName());
            } else {
                System.out.println("Empty");
            }
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void gachaMenu() {
        clearScreen();
        System.out.println("=== GACHA SYSTEM ===");
        System.out.println("1. Single Pull (Items)");
        System.out.println("2. Ten Pull (Item)");
        System.out.println("3. Single Pull (Characters)");
        System.out.println("4. Ten Pull (Characters)");
        System.out.println("5. View Gacha Statistics");
        System.out.println("6. Return to Main Menu");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                performSinglePull();
                break;
            case "2":
                performTenPull();
                break;
            case "3":
                performSinglePullCharacter();
                break;
            case "4":
                performTenPullCharacter();
                break;
            case "5":
                gachaSystem.printStatistics();
                break;
            case "6":
                return;
            default:
                System.out.println("Invalid choice!");
        }

        pause(2);
    }

    private void performSinglePull() {
        Item item = gachaSystem.pullSingle();
        System.out.println("Obtained: " + item.getName());
        playerInventory.addItem(item);
    }
    private void performSinglePullCharacter() {
        Character character = gachaSystem.pullSingleCharacter();
        System.out.println("Obtained: " + character.getName());
        characterData.addCharacter(character.getName(), character);
    }

    private void performTenPull() {
        List<Item> items = gachaSystem.pullTen();
        System.out.println("Obtained " + items.size() + " items");
        for (Item item : items) {
            playerInventory.addItem(item);
        }
    }
    private void performTenPullCharacter() {
        List<Character> characters = gachaSystem.pullTenCharacter();
        System.out.println("Obtained: " + characters.size() + " characters");
        for (Character setCharacters : characters) {
            characterData.addCharacter(setCharacters.getName(), setCharacters);
        }
    }
    private void battleMenu() {
        clearScreen();
        System.out.println("=== BATTLE SIMULATION ===");
        System.out.println("1. Easy Difficulty (Regular Enemies)");
        System.out.println("2. Normal Difficulty (Elite Enemies)");
        System.out.println("3. Hard Difficulty (Boss Enemy)");
        System.out.println("4. Return to Main Menu");
        System.out.print("Select difficulty: ");
        String choice = scanner.nextLine();
        if (choice.matches("[1-3]")) {
            int difficulty = Integer.parseInt(choice);
            startBattle(difficulty);
        } else if (!choice.equals("4")) {
            System.out.println("Invalid choice!");
        }
    }

    private void startBattle(int difficulty) {
        System.out.println("\n=== BATTLE START ===");
        System.out.println("Select characters for battle...");
        List<Character> selectedChars = selectCharactersForBattle();
        if (selectedChars.isEmpty()) {
            System.out.println("No characters selected!");
            return;
        }
        System.out.println("\nBattle starting with:");
        for (Character character : selectedChars) {
            System.out.println("  - " + character.getName() + " (HP: " +
                    character.getCurrentHP() + "/" + character.getMaxHP() + ")");
        }
        pause(2);
        List<Enemy> enemies = createEnemies(difficulty);
        System.out.println("\nEnemies encountered:");
        for (Enemy enemy : enemies) {
            System.out.println("  - " + enemy.getName() + " (HP: " +
                    enemy.getCurrentHP() + "/" + enemy.getMaxHP() + ")");
        }

        pause(2);

        // Initialize battle
        battleSystem.initializeBattle(selectedChars, enemies);
        // Start loop and change status
        boolean battleInProgress = true;
        int turnCount = 1;

        while (battleInProgress) {
            clearScreen();
            System.out.println("=== TURN " + turnCount + " ===");
            System.out.println("=======================");
            displayBattleStatus(selectedChars, enemies);
            System.out.println("\n=== PLAYER TURN ===");
            // Player characters take action
            for (Character character : selectedChars) {
                if (character.isAlive()) {
                    System.out.println("\n" + character.getName() + "'s turn:");
                    System.out.println("1. Basic Attack");
                    System.out.println("2. Use Skill");
                    if (character instanceof FiveStarCharacter) {
                        System.out.println("3. Use Ultimate");
                    }
                    System.out.print("Select action: ");
                    String action = scanner.nextLine();
                    switch (action) {
                        case "1":
                            performBasicAttack(character, enemies);
                            break;
                        case "2":
                            character.useSkill();
                            // For simplicity, apply damage to first alive enemy
                            Enemy target = getFirstAliveEnemy(enemies);
                            if (target != null) {
                                int damage = calculatePlayerDamage(character, target);
                                target.takeDamage(damage);
                                System.out.println(character.getName() + " used skill on " +
                                        target.getName() + " for " + damage + " damage!");
                            }
                            break;
                        case "3":
                            if (character instanceof FiveStarCharacter) {
                                ((FiveStarCharacter) character).useUltimate();
                                Enemy ultTarget = getFirstAliveEnemy(enemies);
                                if (ultTarget != null) {
                                    int damage = character.getAttack() * 7;
                                    ultTarget.takeDamage(damage);
                                    System.out.println(character.getName() + "'s ultimate hit " +
                                            ultTarget.getName() + " for " + damage + " damage!");
                                }
                            }
                            break;
                        default:
                            System.out.println("Invalid action, using basic attack!");
                            performBasicAttack(character, enemies);
                    }
                    // Check if battle ended
                    if (checkBattleEnd(enemies)) {
                        battleInProgress = false;
                        break;
                    }

                    pause(1);
                }
            }

            if (!battleInProgress) {
                break;
            }

            System.out.println("\n=== ENEMY TURN ===");
            // Enemies take action
            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    System.out.println("\n" + enemy.getName() + "'s turn:");
                    enemy.useSkill();
                    // Enemy attacks a random player character
                    Character target = getRandomAliveCharacter(selectedChars);
                    if (target != null) {
                        int damage = calculateEnemyDamage(enemy, target);
                        target.takeDamage(damage);
                        System.out.println(enemy.getName() + " attacked " +
                                target.getName() + " for " + damage + " damage!");
                        // Check if character died
                        if (!target.isAlive()) {
                            System.out.println(target.getName() + " has been defeated!");
                        }
                    }
                    // Check if battle ended
                    if (checkBattleEnd(selectedChars)) {
                        battleInProgress = false;
                        break;
                    }

                    pause(1);
                }
            }

            // Check for phase change (for boss enemies)
            for (Enemy enemy : enemies) {
                if (enemy instanceof BossEnemy) {
                    BossEnemy boss = (BossEnemy) enemy;
                    // Boss automatic phase change
                    if (boss.hasPhases() && boss.getCurrentHP() < boss.getMaxHP() / 2) {
                        boss.transitionPhase();
                    }
                }
            }
            applyEndOfTurnEffects(selectedChars);

            turnCount++;

            System.out.println("\nPress Enter to continue to next turn...");
            scanner.nextLine();
        }

        // Battle result
        boolean victory = checkBattleEnd(enemies);

        if (victory) {
            System.out.println("\n=== BATTLE VICTORY! ===");
            int creditReward = 1000 * difficulty;
            int expReward = 100 * difficulty;

            characterData.addCredits(creditReward);
            System.out.println("Earned " + creditReward + " credits");

            for (Character character : selectedChars) {
                if (character.isAlive()) {
                    characterData.levelUpCharacter(character.getName(), expReward);
                    System.out.println(character.getName() + " gained " + expReward + " EXP");
                }
            }

            // Calculate drops from defeated enemies
            for (Enemy enemy : enemies) {
                if (!enemy.isAlive()) {
                    Item drop = enemy.calculateDrop();
                    if (drop != null) {
                        playerInventory.addItem(drop);
                        System.out.println("Obtained: " + drop.getName());
                    }
                }
            }

            characterData.recordBattle(true);
        } else {
            System.out.println("\n=== BATTLE DEFEAT! ===");
            // Heal all characters to full after battle completed.
            for (Character character : selectedChars) {
                character.setCurrentHP(character.getMaxHP());
            }
            characterData.recordBattle(false);
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private List<Character> selectCharactersForBattle() {
        Map<String, Character> allCharacters = characterData.getOwnedCharacters();
        List<Character> selected = new ArrayList<>();
        List<String> characterNames = new ArrayList<>(allCharacters.keySet());

        while (selected.size() < 4 && selected.size() < allCharacters.size()) {
            clearScreen();
            System.out.println("=== SELECT CHARACTERS FOR BATTLE ===");
            System.out.println("Current selection: " + selected.size() + "/4");

            for (int i = 0; i < selected.size(); i++) {
                Character c = selected.get(i);
                System.out.println((i + 1) + ". " + c.getName() +
                        " (Lvl " + c.getLevel() + ", HP: " +
                        c.getCurrentHP() + "/" + c.getMaxHP() + ")");
            }

            System.out.println("\nAvailable characters:");
            int optionNum = 1;
            Map<Integer, String> optionMap = new HashMap<>();

            for (String charName : characterNames) {
                if (!isCharacterSelected(selected, charName)) {
                    Character c = allCharacters.get(charName);
                    System.out.println(optionNum + ". " + charName +
                            " (Lvl " + c.getLevel() + ", HP: " +
                            c.getCurrentHP() + "/" + c.getMaxHP() + ")");
                    optionMap.put(optionNum, charName);
                    optionNum++;
                }
            }

            System.out.println(optionNum + ". Start Battle");
            System.out.println((optionNum + 1) + ". Clear Selection");

            System.out.print("\nSelect option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == optionNum) {
                    break;
                } else if (choice == optionNum + 1) {
                    selected.clear();
                } else if (choice > 0 && choice < optionNum) {
                    String selectedName = optionMap.get(choice);
                    Character selectedChar = allCharacters.get(selectedName);
                    selected.add(selectedChar);
                } else {
                    System.out.println("Invalid choice!");
                    pause(1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
                pause(1);
            }
        }

        return selected;
    }

    private boolean isCharacterSelected(List<Character> selected, String name) {
        for (Character c : selected) {
            if (c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private List<Enemy> createEnemies(int difficulty) {
        List<Enemy> enemies = new ArrayList<>();

        switch (difficulty) {
            case 1: // Easy - Regular enemies
                enemies.add(new RegularEnemy("Mara-Struck Soldier", false));
                enemies.add(new RegularEnemy("Fragmentum Monster", false));
                break;
            case 2: // Normal - Elite enemies
                enemies.add(new RegularEnemy("Elite Automaton", true));
                enemies.add(new RegularEnemy("Voidranger", false));
                enemies.add(new RegularEnemy("Silvermane Guard", false));
                break;
            case 3: // Hard - Boss enemy
                BossEnemy boss = new BossEnemy("Cocolia, Mother of Deception", 3);
                enemies.add(boss);
                break;
        }

        return enemies;
    }

    private void displayBattleStatus(List<Character> players, List<Enemy> enemies) {
        System.out.println("=== PLAYERS ===");
        for (Character player : players) {
            String status = player.isAlive() ?
                    "HP: " + player.getCurrentHP() + "/" + player.getMaxHP() :
                    "DEFEATED";
            System.out.println(player.getName() + " [" + status + "]");
        }

        System.out.println("\n=== ENEMIES ===");
        for (Enemy enemy : enemies) {
            String status = enemy.isAlive() ?
                    "HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP() :
                    "DEFEATED";
            String extraInfo = "";
            if (enemy instanceof BossEnemy) {
                BossEnemy boss = (BossEnemy) enemy;
                extraInfo = " (Phase " + boss.getCurrentPhase() + ")";
            } else if (enemy instanceof RegularEnemy) {
                RegularEnemy reg = (RegularEnemy) enemy;
                if (reg.isElite()) {
                    extraInfo = " (Elite)";
                }
            }
            System.out.println(enemy.getName() + extraInfo + " [" + status + "]");
        }
    }

    private void performBasicAttack(Character attacker, List<Enemy> enemies) {
        Enemy target = getFirstAliveEnemy(enemies);
        if (target != null) {
            int damage = calculatePlayerDamage(attacker, target);
            target.takeDamage(damage);
            System.out.println(attacker.getName() + " attacked " +
                    target.getName() + " for " + damage + " damage!");

            if (!target.isAlive()) {
                System.out.println(target.getName() + " has been defeated!");
            }
        }
    }

    private int calculatePlayerDamage(Character attacker, Enemy defender) {
        double critRate = 0.05; // Base 5% crit rate
        double critDamage = 0.5; // Base 50% crit damage
        // ex damage caused by equipments and items are calc here
        return Damage.compute(attacker.getAttack(), defender.getDefense(), critRate, critDamage);
    }

    private int calculateEnemyDamage(Enemy attacker, Character defender) {
        double critRate = 0.05;
        double critDamage = 0.5;
        return Damage.compute(attacker.getAttack(), defender.getDefense(), critRate, critDamage);
    }

    private Enemy getFirstAliveEnemy(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                return enemy;
            }
        }
        return null;
    }

    private Character getRandomAliveCharacter(List<Character> characters) {
        List<Character> aliveCharacters = new ArrayList<>();
        for (Character character : characters) {
            if (character.isAlive()) {
                aliveCharacters.add(character);
            }
        }

        if (aliveCharacters.isEmpty()) {
            return null;
        }

        int index = random.nextInt(aliveCharacters.size());
        return aliveCharacters.get(index);
    }

    private boolean checkBattleEnd(List<?> team) {
        if (team.get(0) instanceof Enemy) {
            List<Enemy> enemies = (List<Enemy>) team;
            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    return false;
                }
            }
            return true;
        } else if (team.get(0) instanceof Character) {
            List<Character> characters = (List<Character>) team;
            for (Character character : characters) {
                if (character.isAlive()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private void applyEndOfTurnEffects(List<Character> characters) {
        // Apply small end-of-turn healing to alive characters
        for (Character character : characters) {
            if (character.isAlive()) {
                int healAmount = character.getMaxHP() / 20; // 5% healing per turn
                int newHP = Math.min(character.getCurrentHP() + healAmount, character.getMaxHP());
                if (newHP > character.getCurrentHP()) {
                    character.setCurrentHP(newHP);
                    System.out.println(character.getName() + " recovered " +
                            (newHP - character.getCurrentHP()) + " HP at end of turn");
                }
            }
        }
    }
    
    // the old battle system, which just do simulations based on character rarity, level and difficulties selected by the player

//    private void battleMenu() {
//        clearScreen();
//        System.out.println("=== BATTLE SIMULATION ===");
//        System.out.println("1. Easy Difficulty");
//        System.out.println("2. Normal Difficulty");
//        System.out.println("3. Hard Difficulty");
//        System.out.println("4. Return to Main Menu");
//
//        System.out.print("Select difficulty: ");
//        String choice = scanner.nextLine();
//
//        if (choice.matches("[1-3]")) {
//            int difficulty = Integer.parseInt(choice);
//            startBattle(difficulty);
//        } else if (!choice.equals("4")) {
//            System.out.println("Invalid choice!");
//        }
//    }
//
//    private void startBattle(int difficulty) {
//        System.out.println("\n=== BATTLE START ===");
//
//        System.out.println("Select characters for battle...");
//        List<String> selectedChars = selectCharacters();
//
//        if (selectedChars.isEmpty()) {
//            System.out.println("No characters selected!");
//            return;
//        }
//
//        System.out.println("Battle starting with: " + selectedChars);
//        pause(2);
//
//        boolean victory = simulateBattle(selectedChars, difficulty);
//
//        if (victory) {
//            System.out.println("\nBattle Victory!");
//            int creditReward = 1000 * difficulty;
//            int expReward = 50 * difficulty;
//
//            characterData.addCredits(creditReward);
//            System.out.println("Earned " + creditReward + " credits");
//
//            for (String charName : selectedChars) {
//                characterData.levelUpCharacter(charName, expReward);
//            }
//
//            characterData.recordBattle(true);
//        } else {
//            System.out.println("\nBattle Defeat!");
//            characterData.recordBattle(false);
//        }
//
//        System.out.println("\nPress Enter to continue...");
//        scanner.nextLine();
//    }
//
//    private List<String> selectCharacters() {
//        Map<String, Character> characters = characterData.getOwnedCharacters();
//        List<String> selected = new ArrayList<>();
//
//        while (selected.size() < 4 && selected.size() < characters.size()) {
//            System.out.println("Current selection: " + selected);
//            System.out.println("Available characters:");
//
//            List<String> available = new ArrayList<>();
//            int i = 1;
//            for (String charName : characters.keySet()) {
//                if (!selected.contains(charName)) {
//                    System.out.println(i + ". " + charName);
//                    available.add(charName);
//                    i++;
//                }
//            }
//
//            System.out.println(i + ". Done selecting");
//
//            System.out.print("Select character: ");
//            try {
//                int choice = Integer.parseInt(scanner.nextLine());
//
//                if (choice == i) {
//                    break;
//                } else if (choice > 0 && choice < i) {
//                    selected.add(available.get(choice - 1));
//                } else {
//                    System.out.println("Invalid choice!");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Please enter a number!");
//            }
//        }
//
//        return selected;
//    }
//
//    private boolean simulateBattle(List<String> selectedChars, int difficulty) {
//        double winChance = 0.7 - (difficulty * 0.1);
//
//        int totalLevel = 0;
//        for (String charName : selectedChars) {
//            totalLevel += characterData.getCharacterLevels().getOrDefault(charName, 1);
//        }
//        double levelBonus = totalLevel / (selectedChars.size() * 20.0);
//
//        winChance += Math.min(levelBonus, 0.3);
//
//        return random.nextDouble() < winChance;
//    }

    private void inventoryMenu() {
        clearScreen();
        System.out.println("=== INVENTORY MANAGEMENT ===");
        System.out.println("1. View Inventory");
        System.out.println("2. Sort Inventory");
        System.out.println("3. Search Items");
        System.out.println("4. Return to Main Menu");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewInventory();
                break;
            case "2":
                sortInventory();
                break;
            case "3":
                searchInventory();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void viewInventory() {
        clearScreen();
        playerInventory.displayInventory();

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void sortInventory() {
        clearScreen();
        System.out.println("=== SORT INVENTORY ===");
        System.out.println("1. Sort by Rarity");
        System.out.println("2. Sort by Type");
        System.out.println("3. Sort by Name");
        System.out.println("4. Return");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                playerInventory.sortByRarity();
                break;
            case "2":
                playerInventory.sortByType();
                break;
            case "3":
                playerInventory.sortByName();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice!");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void searchInventory() {
        clearScreen();
        System.out.println("=== SEARCH INVENTORY ===");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Type");
        System.out.println("3. Return");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter item name: ");
                String name = scanner.nextLine();
                playerInventory.searchItem(name);
                break;
            case "2":
                System.out.print("Enter item type: ");
                String type = scanner.nextLine();
                playerInventory.searchItemByType(type);
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid choice!");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void settingsMenu() {
        clearScreen();
        System.out.println("=== GAME SETTINGS ===");
        System.out.println("Current difficulty: " + configManager.getProperty("game.difficulty", "normal"));
        System.out.println("1. Change Difficulty");
        System.out.println("2. Return to Main Menu");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            changeDifficulty();
        }
    }

    private void changeDifficulty() {
        System.out.println("1. Easy");
        System.out.println("2. Normal");
        System.out.println("3. Hard");

        System.out.print("Select difficulty: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                configManager.setProperty("game.difficulty", "easy");
                System.out.println("Difficulty set to: Easy");
                break;
            case "2":
                configManager.setProperty("game.difficulty", "normal");
                System.out.println("Difficulty set to: Normal");
                break;
            case "3":
                configManager.setProperty("game.difficulty", "hard");
                System.out.println("Difficulty set to: Hard");
                break;
            default:
                System.out.println("Invalid choice!");
        }

        configManager.saveConfig();
    }

    private void saveMenu() {
        clearScreen();
        System.out.println("=== SAVE/LOAD GAME ===");
        System.out.println("1. Save Game");
        System.out.println("2. Load Game");
        System.out.println("3. Return to Main Menu");

        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                saveGame();
                break;
            case "2":
                loadGame();
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void saveGame() {
        // added: character data now saves into gameData.
        // (prevent nullpointer when loading a game, since previously characterdata isn't saved, causing a nullpointer when using "character" classes and methods)
        currentGameData.setCharacterData(characterData);
        currentGameData.setSaveDate(new Date());

        boolean success = saveManager.saveGame(currentGameData);
        if (success) {
            System.out.println("Game saved successfully!");
            System.out.println("Saved " + characterData.getOwnedCharacters().size() + " characters");
        } else {
            System.out.println("Failed to save game!");
        }
        pause(2);
    }

    private void loadGame() {
        List<String> saves = saveManager.listSaves();
        if (saves.isEmpty()) {
            System.out.println("No save files found!");
            return;
        }

        System.out.println("Available saves:");
        for (int i = 0; i < saves.size(); i++) {
            System.out.println((i + 1) + ". " + saves.get(i));
        }

        System.out.print("Select save slot: ");
        try {
            int slot = Integer.parseInt(scanner.nextLine());
            currentGameData = saveManager.loadGame(slot);
            if (currentGameData != null) {
                // Character data should be loaded right now
                characterData = currentGameData.getCharacterData();
                if (characterData == null) {
                    System.out.println("Warning: No character data found in save. Initializing new character data...");
                    characterData = new CharacterData();
                    currentGameData.setCharacterData(characterData);
                }
                System.out.println("Game loaded successfully!");
                System.out.println("Loaded " + characterData.getOwnedCharacters().size() + " characters");
            } else {
                System.out.println("Failed to load save!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }

        pause(2);
    }

    private void showStatistics() {
        clearScreen();
        characterData.printStatistics();

        System.out.println("\nPress Enter to return to main menu...");
        scanner.nextLine();
    }

    private void showStorySummary() {
        System.out.println("=== STORY SUMMARY ===\n");
        System.out.println("Chapter 1: Anomalous Signal from Space Station");
        System.out.println("You awaken as a Trailblazer in Herta Space Station with lost memories.");
        System.out.println("The space station is under attack by the Antimatter Legion.");
        System.out.println("\nChapter 2: Invitation to the Astral Express");
        System.out.println("After defeating the enemies, you receive an invitation to board the Astral Express.");
        System.out.println("The train will take you to different worlds in search of the lost Aeons...");
        System.out.println("\nMore chapters to explore...\n");

        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private void exitGame() {
        System.out.print("Save game before exiting? (y/n): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("y") || choice.equals("yes")) {
            saveGame();
        }

        gameRunning = false;
    }

    private void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        GamePlay game = new GamePlay();
        game.start();
    }
}
