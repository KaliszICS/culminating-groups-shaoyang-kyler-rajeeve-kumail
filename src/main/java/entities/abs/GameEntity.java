package entities.abs;

import entities.Displayable;

public abstract class GameEntity implements Displayable {
    protected String name;
    protected int id;
    protected String description;

    // static var to generate id
    protected static int nextId = 1;

    public GameEntity() {
        this.id = nextId++;
        this.name = "Unnamed Entity";
        this.description = "No description";
    }

    public GameEntity(String name, int id) {
        this.id = id;
        this.name = name;
        this.description = "No description";
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    // constructor with description
    public GameEntity(String name, int id, String description) {
        this(name, id);
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toCSVFormat() {
        return id + "," + name + ",\"" + description.replace("\"", "\"\"") + "\"";
    }
}