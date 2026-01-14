package util.Algorithms;
import entities.items.Item;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchAlgorithms {
    public SearchAlgorithms() {
    }

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
