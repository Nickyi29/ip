package nico.tasks;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public abstract String getTypeIcon();

    @Override
    public String toString() {
        String statusIcon = isDone ? "X" : " ";
        return "[" + getTypeIcon() + "][" + statusIcon + "] " + description;
    }
}