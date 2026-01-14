package entities.abs;

import entities.Displayable;

import java.io.Serializable;

/**
 * Represents an abstract class for all game entities.
 * Implements the {@link Displayable} interface to provide CSV formats.
 * Each game entity has a unique identifier, a name, and a description which is optional.
 * <p>
 * This class maintains a static counter to automatically generate unique IDs
 * for entities, ensuring each entity instance has a unique identifier.
 * </p>
 * @author Shaoyang Chen
 * @version 1.4.1
 * @see Displayable
 */
public abstract class GameEntity implements Displayable, Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;
    protected int id;
    protected String description;
    protected static int nextId = 1;
    /**
     * Constructs a GameEntity with default values.
     */
    public GameEntity() {
        this.id = nextId++;
        this.name = "Unnamed Entity";
        this.description = "No description";
    }

    /**
     * Constructs a GameEntity with a specified name and ID.
     * Updates the static ID counter if the provided ID is greater than or equal to the current nextId.
     *
     * @param name name of this entity
     * @param id unique id for this entity
     */
    public GameEntity(String name, int id) {
        this.id = id;
        this.name = name;
        this.description = "No description";
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    /**
     * Constructs a GameEntity with a specified name, ID, and description.
     * Updates the static ID counter if the provided ID is greater than or equal to the current nextId.
     *
     * @param name the name of this entity
     * @param id the unique id for this entity
     * @param description the description about this entity
     */
    public GameEntity(String name, int id, String description) {
        this(name, id);
        this.description = description;
    }

    /**
     * Returns the name of this entity.
     *
     * @return the name of this entity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this entity.
     *
     * @param name the new name for this entity
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the unique id of this entity.
     *
     * @return the ID of this entity
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the description of this entity.
     *
     * @return the description about this entity
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this entity.
     *
     * @param description the new description for this entity
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Converts this game entity to CSV format.
     * The format is: id,name,"description"
     * @return a comma-separated string representation of this entity in CSV format
     */
    @Override
    public String toCSVFormat() {
        return id + "," + name + ",\"" + description.replace("\"", "\"\"") + "\"";
    }
}