package nico;

import nico.tasks.Task;

public class TaskList {
    private static final int MAX_TASKS = 100;

    private final Task[] tasks = new Task[MAX_TASKS];
    private int taskCount = 0;

    public int size() {
        return taskCount;
    }

    public Task get(int index) {
        return tasks[index];
    }

    public boolean isFull() {
        return taskCount >= MAX_TASKS;
    }

    public int getMaxTasks() {
        return MAX_TASKS;
    }

    public void add(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public Task setDone(int index, boolean isDone) {
        if (index < 0 || index >= taskCount) {
            throw new IndexOutOfBoundsException();
        }

        Task task = tasks[index];
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
        return task;
    }
}