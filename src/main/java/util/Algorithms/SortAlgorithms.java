package util.Algorithms;

import entities.items.Item;

import java.util.ArrayList;
import java.util.List;

public class SortAlgorithms {

    public SortAlgorithms() {
    }

    public List<Item> bubbleSort(List<Item> items) {
        if (items == null || items.size() == 0) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = true;
            for (int j = 0; j < n - i - 1; j++){
                Item tempItem = sortedItems.get(j);
                Item tempItem1 = sortedItems.get(j + 1);
                if (tempItem.getName().compareTo(tempItem1.getName()) > 0) {
                    sortedItems.set(j, sortedItems.get(j + 1));
                    sortedItems.set(j + 1, tempItem);
                    swapped = true;
                }
                if (!swapped) {
                    break;
                }
            }
        }
        return sortedItems;
    }

    public List<Item> selectionSort(List<Item> items) {
        if (items == null || items.size() == 0) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        for (int i = 0; i < n - 1; i++) {
            int Min = i;
            for (int j = i + 1; j < n; j++) {
                if (sortedItems.get(j).getName().compareTo(sortedItems.get(Min).getName()) < 0) {
                    Min = j;
                }
            }
            int temp = sortedItems.get(Min).getId();
            sortedItems.set(Min, sortedItems.get(Min + 1));
            sortedItems.set(Min + 1, sortedItems.get(Min));

        }
        return items;
    }
    public List<Item> insertionSort(List<Item> items) {
        if (items == null || items.size() == 0) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        for (int i = 1; i < n; i++) {
            int Min = i;
            int j = i - 1;
            while (j >= 0 && sortedItems.get(j).getId() == sortedItems.get(Min).getId()) {
                sortedItems.set(j + 1, sortedItems.get(j));
            }
            sortedItems.set(j + 1, sortedItems.get(Min));
        }
        return items;
    }

    public List<Item> mergeSort(List<Item> items) {
        if (items == null || items.size() == 0) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        if (n % 2 == 0) {
            return null;
        }
        int mid = n / 2;
        int[] left = new int[mid];
        int[] right = new int[n - mid];
        for (int i = 0; i < mid; i++) {
            left[i] = i;
        }
        for (int i = mid; i < n; i++) {
            right[i - mid] = i;
        }
        Item[] leftItems = new Item[left.length];
        Item[] rightItems = new Item[right.length];
        for (int i = 0; i < left.length; i++) {
        }
        return null;
    }
}
