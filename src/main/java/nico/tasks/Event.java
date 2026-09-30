package nico.tasks;

/**
 * Represents a task that starts and ends at specific times,
 * e.g. "project meeting (from: Mon 2pm to: 4pm)".
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an event with the given description, start and end.
     *
     * @param description What the event is about.
     * @param from When the event starts.
     * @param to When the event ends.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    /**
     * Returns when this event starts.
     *
     * @return The start date or time.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns when this event ends.
     *
     * @return The end date or time.
     */
    public String getTo() {
        return to;
    }

    @Override
    public String toSaveFormat() {
        return super.toSaveFormat() + SAVE_DELIMITER + from + SAVE_DELIMITER + to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}