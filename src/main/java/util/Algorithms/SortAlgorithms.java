package util.Algorithms;

import entities.items.Item;
import java.util.ArrayList;
import java.util.List;



public class SortAlgorithms {

    public SortAlgorithms() {
    }

    public List<Item> bubbleSort(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (sortedItems.get(j).getName().compareTo(sortedItems.get(j + 1).getName()) > 0) {
                    Item tem = sortedItems.get(j);
                    sortedItems.set(j, sortedItems.get(j + 1));
                    sortedItems.set(j + 1, tem);
                    swapped = true;
                }
                if (!swapped) break;
            }
        }
        return sortedItems;
    }


    public List<Item> selectionSort(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        for (int i = 0; i < n - 1; i++) {
            int mi = i;
            for (int j = i + 1; j < n; j++) {
                if (sortedItems.get(j).getName().compareTo(sortedItems.get(mi).getName()) < 0) {
                    mi = j;
                }
            }
            sortedItems.set(mi, sortedItems.get(mi + 1));
            sortedItems.set(mi + 1, sortedItems.get(mi));

        }
        return sortedItems;
    }

    public List<Item> insertionSort(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        for (int i = 1; i < n; i++) {
            Item k = sortedItems.get(i);
            int j = i - 1;
            while (j >= 0 && sortedItems.get(j).getName().compareTo(k.getName()) > 0) {
                sortedItems.set(j + 1, sortedItems.get(j));
                j--;
            }
            sortedItems.set(j + 1, k);
        }
        return sortedItems;
    }

    public List<Item> mergeSort(List<Item> items) {
        if (items == null || items.size() <= 1) {
            return items;
        }
        List<Item> sortedItems = new ArrayList<>(items);
        int n = sortedItems.size();
        int mi = n / 2;

        List<Item> left = new ArrayList<>();
        List<Item> right = new ArrayList<>();

        for (int i = 0; i < mi; i++) {
            left.add(items.get(i));
        }
        for (int i = mi; i < n; i++) {
            right.add(items.get(i));
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
            if (left.get(i).getName().compareTo(right.get(j).getName()) > 0) {
                sortedItems.add(right.get(j++));
            } else {
                sortedItems.add(left.get(i++));
            }
            while (j < right.size()) {
                sortedItems.add(right.get(j++));
            }
            while (i < left.size()) {
                sortedItems.add(left.get(i++));
            }
        }
        return sortedItems;
    }
}
