package nico.tasks;

/**
 * Represents a task that must be done by a certain date or time,
 * e.g. "return book (by: Sunday)".
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a deadline with the given description and due date.
     *
     * @param description What the deadline is about.
     * @param by When the task must be done by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns when this deadline is due.
     *
     * @return The due date or time.
     */
    public String getBy() {
        return by;
    }

    @Override
    public String toSaveFormat() {
        return super.toSaveFormat() + SAVE_DELIMITER + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}