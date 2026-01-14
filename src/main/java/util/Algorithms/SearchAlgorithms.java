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
        int left = 0;
        int right = sortedItems.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (sortedItems.get(mid).getName().equals(name)) {
                return sortedItems.get(mid);
            } else if (sortedItems.get(mid).getName().compareTo(name) <= 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public List<Item> sequentialSearch(List<Item> items, String criteria) {
        if (items == null || items.isEmpty() || criteria == null) {
            return null;
        }
        List<Item> unsortedItems = new ArrayList<>(items);
        for (int i = 0; i < unsortedItems.size(); i++) {
            if (unsortedItems.get(i).getName().equals(criteria)) {
                return unsortedItems;
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





