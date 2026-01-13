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
        int mid = n / 2;
        List<Item> left = new ArrayList<>();
        List<Item> right = new ArrayList<>();
        for (int i = 0; i < mid; i++) {
            left.add(sortedItems.get(i));
        }
        for (int i = mid; i < n; i++) {
            right.add(sortedItems.get(i + 1));
        }
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }

    public List<Item> merge(List<Item> left, List<Item> right) {
        List<Item> sortedItems = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getName().compareTo(right.get(j).getName()) < 0) {
                sortedItems.add(left.get(i++));
            }
            else if (left.get(i).getName().compareTo(right.get(j).getName()) > 0) {
                sortedItems.add(right.get(j++));
            }
            else {
                sortedItems.add(left.get(i++));
            }
            while (i < left.size() && left.get(i).getName().compareTo(right.get(j).getName()) < 0) {
                sortedItems.add(right.get(j++));
            }
        }
        while (i < left.size()) {
            sortedItems.add(left.get(i++));
        }
        while (j < right.size()) {
            sortedItems.add(right.get(j++));
        }
        return sortedItems;
    }
}
