package entities;

import entities.items.Item;

public interface GachaPullable {
    Item pull();
    double getDropRate();
}