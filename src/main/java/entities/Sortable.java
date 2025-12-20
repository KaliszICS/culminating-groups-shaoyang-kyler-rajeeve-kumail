package entities;

public interface Sortable {
    void sort();
    int compare(Object other);
}