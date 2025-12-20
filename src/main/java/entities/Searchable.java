package entities;

public interface Searchable {
    Object search(String criteria);
    Object search(String criteria, int startIndex);
}