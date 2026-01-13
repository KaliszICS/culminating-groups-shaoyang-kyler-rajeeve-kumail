package systems.inventory;

import entities.Searchable;
import entities.Sortable;
import entities.items.*;
import entities.characters.*;
import entities.equipment.*;
import util.Algorithms.*;
import entities.characters.Character;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the inventory system of the game.
 * Items are stored in List, the inventory have the following attributes:
 * currentWeight, maxWeight, Hashmap storing character equipments, search and sort algorithms
 * Implements {@link Sortable} and {@link Searchable}
 *
 * @author Shaoyang Chen
 * @version 1.0.3
 * @see Sortable
 * @see Searchable
 * @see Character
 * @see Equipment
 * @see Item
 */
public class Inventory implements Sortable, Searchable {
    private List<Item> items;
    private int currentWeight;
    private int maxWeight;
    private Map<Character, List<Equipment>> equippedItems;
    private SortAlgorithms sortAlgorithms;
    private SearchAlgorithms searchAlgorithms;

    /**
     * Instantiates a new Inventory with no args provided, therefore everything is as default, max weight is set to 1000
     */
    public Inventory() {
        this.items = new ArrayList<>();
        this.currentWeight = 0;
        this.maxWeight = 1000; // max weight currently 1000
        this.equippedItems = new HashMap<>();
        this.sortAlgorithms = new SortAlgorithms();
        this.searchAlgorithms = new SearchAlgorithms();
    }

    /**
     * Instantiates a new Inventory with provided capacity (maxWeight)
     *
     * @param capacity the capacity of the inventory
     */
    public Inventory(int capacity) {
        this();
        this.maxWeight = capacity;
    }

    /**
     * Add an item to inventory
     *
     * @param item the item to add
     * @return the boolean indicating whether the adding process was successful
     */
    public boolean addItem(Item item) {
        if (item == null) {
            System.out.println("Cannot add null item to inventory");
            return false;
        }

        // check weight
        int newWeight = currentWeight + item.getWeight();
        if (newWeight > maxWeight) {
            System.out.println("Your inventory is full! You cannot add new items at this moment: " + item.getName());
            System.out.println("Current weight: " + currentWeight + "/" + maxWeight);
            return false;
        }

        // if stackable, combine them together
        if (item.isStackable()) {
            for (Item existingItem : items) {
                if (existingItem.isStackable() &&
                        existingItem.getName().equals(item.getName()) &&
                        existingItem.getClass().equals(item.getClass())) {

                    // Combine item
                    System.out.println("Combined item: " + item.getName());
                    items.remove(existingItem);
                    Item mergedItem = mergeItems(existingItem, item);
                    items.add(mergedItem);

                    currentWeight = calculateTotalWeight();
                    return true;
                }
            }
        }

        // add new item to inventory
        items.add(item);
        currentWeight += item.getWeight();
        System.out.println("Added item: " + item.getName() + " (Weight: " + item.getWeight() + ")");
        System.out.println("Current Weight: " + currentWeight + "/" + maxWeight);
        return true;
    }

    /**
     * Merge items together
     * @param item1 first item to merge
     * @param item2 second item to merge
     * @return final product of the merge
     */
    private Item mergeItems(Item item1, Item item2) {
        // merge different items based on their type
        if (item1 instanceof MaterialItem && item2 instanceof MaterialItem) {
            MaterialItem mat1 = (MaterialItem) item1;
            MaterialItem mat2 = (MaterialItem) item2;
            // create new merged item
            MaterialItem merged = new MaterialItem(mat1.getName(), mat1.getMaterialType(),
                    mat1.getRarity(), mat1.getValue() + mat2.getValue());
            merged.setWeight(Math.max(mat1.getWeight(), mat2.getWeight()));
            merged.setStackable(true);
            System.out.println("Merging materials: " + mat1.getName() + " + " + mat2.getName());
            return merged;
        }
        // other type merging
        item1.setValue(item1.getValue() + item2.getValue());
        return item1;
    }

    /**
     * Remove item with an Item ID
     *
     * @param itemId the item id to remove
     * @return the item removed
     */
    public Item removeItem(int itemId) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getId() == itemId) {
                items.remove(i);
                currentWeight -= item.getWeight();
                System.out.println("Removed Item: " + item.getName());
                System.out.println("Current Weight: " + currentWeight + "/" + maxWeight);
                return item;
            }
        }

        System.out.println("Cannot find item ID: " + itemId);
        return null;
    }

    /**
     * Remove item with name
     *
     * @param name the name of the item to remove
     * @return the item removed
     */
    public Item removeItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equals(name)) {
                items.remove(i);
                currentWeight -= item.getWeight();
                System.out.println("Removed item: " + item.getName());
                System.out.println("Current weight: " + currentWeight + "/" + maxWeight);
                return item;
            }
        }

        System.out.println("Cannot find item with this name: " + name);
        return null;
    }

    /**
     * Sort inventory by rarity.
     */
    public void sortByRarity() {
        System.out.println("Sort by rarity using bubble sort");
        // create 2nd array
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems = sortAlgorithms.bubbleSort(sortedItems);
        // update list
        items = sortedItems;
        displayInventory();
    }

    /**
     * Sort by item type.
     */
    public void sortByType() {
        System.out.println("Sort by type using selection sort");
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems = sortAlgorithms.selectionSort(sortedItems);
        items = sortedItems;
        displayInventory();
    }

    /**
     * Sort by item name.
     */
    public void sortByName() {
        System.out.println("Sort by name using insertion sort");
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems = sortAlgorithms.insertionSort(sortedItems);
        items = sortedItems;
        displayInventory();
    }

    /**
     * Sort advanced sort using merge sort
     */
    public void sortAdvanced() {
        System.out.println("Advanced search using merge sort");
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems = sortAlgorithms.mergeSort(sortedItems);
        items = sortedItems;
        displayInventory();
    }

    /**
     * Search item with item name
     *
     * @param name the name of the item
     * @return the result item
     */
    public Item searchItem(String name) {
        System.out.println("Searching item: " + name);
        List<Item> sortedList = new ArrayList<>(items);
        sortedList = sortAlgorithms.insertionSort(sortedList);
        Item result = searchAlgorithms.binarySearch(sortedList, name);
        if (result != null) {
            System.out.println("Found Item: " + result.getName());
            result.displayInfo();
        } else {
            System.out.println("Item not found with provided name: " + name);
        }
        return result;
    }

    /**
     * Search for list of items by provided type
     *
     * @param type the type to search
     * @return the list of matched items
     */
    public List<Item> searchItemByType(String type) {
        System.out.println("Search by type: " + type);
        List<Item> result = searchAlgorithms.sequentialSearch(items, type);
        if  (result != null) {
            if (!result.isEmpty()) {
                System.out.println("Found " + result.size() + " " + type + "items:");
                for (Item item : result) {
                    System.out.println("  - " + item.getName());
                }
            } else {
                System.out.println("Not found with provided" + type);
            }
        } else {
            System.out.println("Not found with provided" + type);
        }

        return result;
    }

    /**
     * Recursive search item with provided criteria
     *
     * @param criteria the criteria of the search
     * @return the item the match the criteria
     */
    public Item recursiveSearch(String criteria) {
        System.out.println("Recursive Searching for: " + criteria);
        Item result = searchAlgorithms.recursiveSearch(items, 0, criteria);
        if (result != null) {
            System.out.println("Found items: " + result.getName());
        } else {
            System.out.println("Not found: " + criteria);
        }
        return result;
    }

    /**
     * Equip item to the character, return a boolean indicating whether the process was successful
     *
     * @param character the character to equip the equipment
     * @param equipment the equipment to equip on the character
     * @return the boolean indicating whether the process was successful
     */
    public boolean equipItem(Character character, Equipment equipment) {
        if (character == null || equipment == null) {
            System.out.println("Invalid character or equipment");
            return false;
        }
        if (character.getLevel() < equipment.getRequiredLevel()) {
            System.out.println(character.getName() + " insufficient level for character, level needed: " + equipment.getRequiredLevel());
            return false;
        }
        if (!containsItem(equipment)) {
            System.out.println("Item(s) not in inventory: " + equipment.getName());
            return false;
        }
        List<Equipment> characterEquipment = equippedItems.getOrDefault(character, new ArrayList<>());
        if (characterEquipment.size() >= 4) { // max 4 equipments
            System.out.println(character.getName() + "'s equipment slots are full");
            return false;
        }
        for (Equipment equipped : characterEquipment) {
            if (equipped.getSlot().equals(equipment.getSlot())) {
                System.out.println(character.getName() + "'s " + equipment.getSlot() + " already have an equipment on there: " + equipped.getName());
                return false;
            }
        }
        characterEquipment.add(equipment);
        equippedItems.put(character, characterEquipment);
        removeItem(equipment.getName());
        System.out.println(character.getName() + " equipped " + equipment.getName() + " on slot " + equipment.getSlot());
        equipment.calculateStats();
        equipment.equip(character);
        return true;
    }

    /**
     * Unequip an equipment from the character, return a boolean indicating whether the process was successful
     *
     * @param character the character choses
     * @param equipment the equipment to unequip
     * @return the boolean indicating whether the process was successful
     */
    public boolean unequipItem(Character character, Equipment equipment) {
        if (character == null || equipment == null) {
            System.out.println("Invalid character or equipment");
            return false;
        }

        List<Equipment> characterEquipment = equippedItems.get(character);
        if (characterEquipment == null || !characterEquipment.contains(equipment)) {
            System.out.println(character.getName() + " didn't equip " + equipment.getName());
            return false;
        }
        characterEquipment.remove(equipment);
        equippedItems.put(character, characterEquipment);
        addItem(equipment);
        System.out.println(character.getName() + " Unequipped " + equipment.getName());
        equipment.unequip();

        return true;
    }

    /**
     * Unequip a specific slot from the character, return a boolean indicating whether the process was successful
     *
     * @param character the character
     * @param slot      the slot
     * @return the boolean indicating whether the process was successful
     */
    public boolean unequipSlot(Character character, String slot) {
        List<Equipment> characterEquipment = equippedItems.get(character);
        if (characterEquipment == null) {
            System.out.println(character.getName() + " didn't equip anything yet");
            return false;
        }
        for (Equipment equipment : characterEquipment) {
            if (equipment.getSlot().equals(slot)) {
                return unequipItem(character, equipment);
            }
        }
        System.out.println(character.getName() + "'s slot " + slot + "does not equip with anything yet");
        return false;
    }

    /**
     * Check if inventory contains the provided item
     *
     * @param item the item to check
     * @return the boolean indicating whether the item is in the inventory
     */
    public boolean containsItem(Item item) {
        return items.contains(item);
    }

    /**
     * Check if inventory contains item that match the provided name
     *
     * @param name the name of item to check (case sensitive)
     * @return the boolean indicating whether the item is in the inventory
     */
    public boolean containsItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets item count of the inventory
     *
     * @return the item count of the inventory
     */
    public int getItemCount() {
        return items.size();
    }

    private int calculateTotalWeight() {
        return items.stream().mapToInt(Item::getWeight).sum();
    }

    /**
     * Display all items in the inventory and all stats about the inventory
     */
    public void displayInventory() {
        System.out.println("=== Inventory Information ===");
        System.out.println("Items count: " + items.size());
        System.out.println("Current weight: " + currentWeight + "/" + maxWeight);
        System.out.println("Free weight remaining: " + (maxWeight - currentWeight));

        if (items.isEmpty()) {
            System.out.println("Empty Inventory!");
            return;
        }

        System.out.println("\nItem list:");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String rarity = "";
            if (item instanceof MaterialItem) {
                MaterialItem mat = (MaterialItem) item;
                // rarity = "★".repeat(mat.getRarity());
                StringBuilder starBuilder = new StringBuilder();
                for (int j = 0; j < mat.getRarity(); j++) {
                    starBuilder.append("★");
                }
                rarity = starBuilder.toString();
            } else if (item instanceof Equipment) {
                Equipment eq = (Equipment) item;
                if (eq instanceof LightCone) {
                    LightCone lc = (LightCone) eq;
                    rarity = lc.getRequiredLevel() >= 60 ? "★★★★★" : "★★★★";
                } else {
                    rarity = "★★★";
                }
            } else {
                rarity = "★★";
            }
            i += 1;
            System.out.println(" " + i + " " + rarity + " " + item.getName() + " (Weight: " + item.getWeight() + "Value: " + item.getValue()+ ")");
        }
    }

    /**
     * Display equipment of a specific character
     *
     * @param character the character to display
     */
    public void displayEquipment(Character character) {
        System.out.println("=== " + character.getName() + "'s Equipment Information ===");

        List<Equipment> characterEquipment = equippedItems.get(character);
        if (characterEquipment == null || characterEquipment.isEmpty()) {
            System.out.println("No equipment on this character");
            return;
        }

        for (Equipment equipment : characterEquipment) {
            System.out.println("Slot: " + equipment.getSlot() + " - " + equipment.getName());
            System.out.println("  Required Level: " + equipment.getRequiredLevel());
            System.out.println("  Attack: " + equipment.getStat("attack") +
                    ", Defense: " + equipment.getStat("defense") +
                    ", HP: " + equipment.getStat("hp"));
        }
    }

    /**
     * Gets a list of characters that has equipped a specific Equipment
     *
     * @param equipment the equipment to check
     * @return the characters with the specific equipment
     */
    public List<Character> getCharactersWithEquipment(Equipment equipment) {
        List<Character> characters = new ArrayList<>();
        for (Map.Entry<Character, List<Equipment>> entry : equippedItems.entrySet()) {
            if (entry.getValue().contains(equipment)) {
                characters.add(entry.getKey());
            }
        }
        return characters;
    }

    /**
     * Gets all items in the inventory
     *
     * @return the arraylist of items
     */
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * Gets item with a specific index
     *
     * @param index the index to check
     * @return the item on that index
     */
    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    /**
     * Clear everything in inventory (should never been used unless debugging)
     */
    public void clear() {
        items.clear();
        currentWeight = 0;
        System.out.println("Inventory has been cleared");
    }

    /**
     * Expand capacity of inventory with a provided amount
     *
     * @param additionalCapacity the additional capacity to add
     */
    public void expandCapacity(int additionalCapacity) {
        maxWeight += additionalCapacity;
        System.out.println("Added " + additionalCapacity + " extra capacity");
        System.out.println("New max weight: " + maxWeight);
    }

    /**
     * Sort the inventory by item name
     */
    @Override
    public void sort() {
        sortByName();
    }

    /**
     * 
     * @param other
     * @return
     */
    @Override
    public int compare(Object other) {
        if (other instanceof Inventory) {
            Inventory otherInventory = (Inventory) other;
            return Integer.compare(this.items.size(), otherInventory.items.size());
        }
        return -1;
    }

    /**
     * Search items with provided criteria 
     * @param criteria String of criteria
     * @return Item that meet the criteria
     */
    @Override
    public Object search(String criteria) {
        return searchItem(criteria);
    }

    /**
     * Search with criteria and start index
     * @param criteria String of criteria
     * @param startIndex index of where the search should start
     * @return Item that meet the criteria
     */
    @Override
    public Object search(String criteria, int startIndex) {
        if (startIndex < 0 || startIndex >= items.size()) {
            System.out.println("Invalid Start Index");
            return null;
        }

        for (int i = startIndex; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().contains(criteria) ||
                    item.getItemType().equals(criteria) ||
                    (item instanceof MaterialItem && ((MaterialItem) item).getMaterialType().equals(criteria))) {
                System.out.println("From " + startIndex + " Found item: " + item.getName());
                return item;
            }
        }

        System.out.println("From index provided " + startIndex + " item not found: " + criteria);
        return null;
    }

    /**
     * Export everything in the inventory to csv string
     *
     * @return the string that contains all information and items
     */
    public String exportToCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Name,Type,Rarity,Weight,Value,Stack\n");

        for (Item item : items) {
            String rarity = "★";
            if (item instanceof MaterialItem) {
                MaterialItem mat = (MaterialItem) item;
                StringBuilder starBuilder = new StringBuilder();
                for (int j = 0; j < mat.getRarity(); j++) {
                    starBuilder.append("★");
                }
                rarity = starBuilder.toString();
            }
            String str = item.getId() + "," + item.getName() + "," + item.getItemType() + "," + rarity + "," + item.getWeight() + "," + item.getValue() + "," + item.isStackable();
            csv.append(str + "\n");
        }

        return csv.toString();
    }

    /**
     * Calculate total value of what is in the inventory
     *
     * @return the total value
     */
    public int calculateTotalValue() {
        return items.stream().mapToInt(Item::getValue).sum();
    }

    /**
     * Gets statistics about the inventory
     *
     * @return the statistics about the inventory
     */
    public Map<String, Integer> getStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        // Grouped statistics
        // Convert the `items` collection to a Stream.
        // Use `Collectors.groupingBy` to group by `itemType`.
        // For each group, use `Collectors.counting()` to count the number of items.
        // Return a `Map<String, Long>`, where the key is the item type and the value is the count of that type.
        Map<String, Long> typeCount = items.stream()
                .collect(Collectors.groupingBy(Item::getItemType, Collectors.counting()));

        //Iterate through each key-value pair in the typeCount Map.
        //Convert the Long type value to an int type.
        //Store the converted result in another Map called stats.
        for (Map.Entry<String, Long> entry : typeCount.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().intValue());
        }

        // Add total stats to hashmap
        stats.put("Total Amount", items.size());
        stats.put("Total Weight", currentWeight);
        stats.put("Total Value", calculateTotalValue());

        return stats;
    }
}
