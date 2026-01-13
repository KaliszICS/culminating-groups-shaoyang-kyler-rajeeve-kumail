package game;

import data.CharacterData;
import data.config.ConfigManager;
import data.save.GameData;
import data.save.SaveManager;
import entities.characters.Character;
import entities.equipment.Equipment;
import entities.items.Item;
import systems.gacha.GachaSystem;
import systems.inventory.Inventory;
import util.fileio.FileHandler;

import java.util.*;

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

    // ASCII Art
    private static final String LOGO =
            "╔═══════════════════════════════════════════════════════════════╗\n" +
                    "║    ███████╗████████╗██████╗ ███████╗██╗  ██╗███████╗██████╗  ║\n" +
                    "║    ██╔════╝╚══██╔══╝██╔══██╗██╔════╝██║  ██║██╔════╝██╔══██╗ ║\n" +
                    "║    ███████╗   ██║   ██████╔╝█████╗  ███████║█████╗  ██████╔╝ ║\n" +
                    "║    ╚════██║   ██║   ██╔══██╗██╔══╝  ██╔══██║██╔══╝  ██╔══██╗ ║\n" +
                    "║    ███████║   ██║   ██║  ██║███████╗██║  ██║███████╗██║  ██║ ║\n" +
                    "║    ╚══════╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝ ║\n" +
                    "║                                                               ║\n" +
                    "║                   STAR RAIL DESTINY CROSSING                  ║\n" +
                    "╚═══════════════════════════════════════════════════════════════╝\n";

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
                        System.out.println("Game loaded successfully!");
                    } else {
                        System.out.println("Failed to load save. Starting new game...");
                        characterData = new CharacterData();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Starting new game...");
                    characterData = new CharacterData();
                }
            }
        } else {
            System.out.println("Starting new game...");
            characterData = new CharacterData();
        }
    }

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
        System.out.println("╔═══════════════════════════════════════════════════════════╗\n" +
                "║                        MAIN MENU                         ║\n" +
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

        // In a real implementation, you would search for the equipment
        // For now, we'll create a dummy equipment
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
        System.out.println("1. Single Pull");
        System.out.println("2. Ten Pull");
        System.out.println("3. View Gacha Statistics");
        System.out.println("4. Return to Main Menu");

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
                gachaSystem.printStatistics();
                break;
            case "4":
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

    private void performTenPull() {
        List<Item> items = gachaSystem.pullTen();
        System.out.println("Obtained " + items.size() + " items");
        for (Item item : items) {
            playerInventory.addItem(item);
        }
    }

    private void battleMenu() {
        clearScreen();
        System.out.println("=== BATTLE SIMULATION ===");
        System.out.println("1. Easy Difficulty");
        System.out.println("2. Normal Difficulty");
        System.out.println("3. Hard Difficulty");
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
        List<String> selectedChars = selectCharacters();

        if (selectedChars.isEmpty()) {
            System.out.println("No characters selected!");
            return;
        }

        System.out.println("Battle starting with: " + selectedChars);
        pause(2);

        boolean victory = simulateBattle(selectedChars, difficulty);

        if (victory) {
            System.out.println("\nBattle Victory!");
            int creditReward = 1000 * difficulty;
            int expReward = 50 * difficulty;

            characterData.addCredits(creditReward);
            System.out.println("Earned " + creditReward + " credits");

            for (String charName : selectedChars) {
                characterData.levelUpCharacter(charName, expReward);
            }

            characterData.recordBattle(true);
        } else {
            System.out.println("\nBattle Defeat!");
            characterData.recordBattle(false);
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private List<String> selectCharacters() {
        Map<String, Character> characters = characterData.getOwnedCharacters();
        List<String> selected = new ArrayList<>();

        while (selected.size() < 4 && selected.size() < characters.size()) {
            System.out.println("Current selection: " + selected);
            System.out.println("Available characters:");

            List<String> available = new ArrayList<>();
            int i = 1;
            for (String charName : characters.keySet()) {
                if (!selected.contains(charName)) {
                    System.out.println(i + ". " + charName);
                    available.add(charName);
                    i++;
                }
            }

            System.out.println(i + ". Done selecting");

            System.out.print("Select character: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == i) {
                    break;
                } else if (choice > 0 && choice < i) {
                    selected.add(available.get(choice - 1));
                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }

        return selected;
    }

    private boolean simulateBattle(List<String> selectedChars, int difficulty) {
        double winChance = 0.7 - (difficulty * 0.1);

        int totalLevel = 0;
        for (String charName : selectedChars) {
            totalLevel += characterData.getCharacterLevels().getOrDefault(charName, 1);
        }
        double levelBonus = totalLevel / (selectedChars.size() * 20.0);

        winChance += Math.min(levelBonus, 0.3);

        return random.nextDouble() < winChance;
    }

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
        boolean success = saveManager.saveGame(currentGameData);
        if (success) {
            System.out.println("Game saved successfully!");
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
                System.out.println("Game loaded successfully!");
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

    public static void main(String[] args) {
        GamePlay game = new GamePlay();
        game.start();
    }
}