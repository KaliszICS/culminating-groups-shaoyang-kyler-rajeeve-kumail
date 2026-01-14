package util.Algorithms;
import entities.items.Item;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The Search algorithms, of utilities. The purpose of this code is to search for items from the Item class
 *
 * @author Rajeeve Ravi
 * @version 1.5
 */
public class SearchAlgorithms {
    /**
     * The constructor of searchAlgorithms.
     */
    public SearchAlgorithms() {
    }

    /**
     *  The binary search with a provided item name
     *
     * @param items the items for list of items to searching
     * @param name  the name for name of items
     * @return the item if it doesn't exist
     */
    public Item binarySearch(List<Item> items, String name) {
        if (items == null || items.isEmpty() || name == null) {
            return null;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems.sort(Comparator.comparing(Item::getName));
        int let = 0;
        int rig = sortedItems.size() - 1;
        while (let <= rig) {
            int m = let + (rig - let) / 2;
            int compare = sortedItems.get(m).getName().compareTo(name);
            if (compare == 0) {
                return sortedItems.get(m);
            } else if (compare > 0) {
                rig = m - 1;
            }
            else {
                let = m + 1;
            }
        }
        return null;
    }

    /**
     * Sequential search with a provided item name
     *
     * @param items    the items list of items to searching
     * @param criteria the criteria
     * @return the list
     */
    public List<Item> sequentialSearch(List<Item> items, String criteria) {
        if (items == null || items.isEmpty() || criteria == null) {
            return null;
        }
        for (Item item : items) {
            if (item.getName().equals(criteria)) {
                return items;
            }
        }
        return null;
    }

    /**
     * Recursive search with a provided item name and start index
     *
     * @param items    the items
     * @param index    the index
     * @param criteria the criteria
     * @return the item
     */
    public Item recursiveSearch(List<Item> items, int index, String criteria)
    {
        if (items == null || criteria == null) {
            return null;
        }
        if (index >= items.size()) {
            return null;
        }
        Item item = items.get(index);
        if (item != null && item.getName() != null
                && item.getName().equals(criteria)) {
            return item;
        }
        return recursiveSearch(items, index + 1, criteria);
    }

}
