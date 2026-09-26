package nico;

import nico.tasks.Deadline;
import nico.tasks.Event;
import nico.tasks.Task;
import nico.tasks.Todo;

import java.util.Scanner;

public class Nico {
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND_PREFIX = "mark ";
    private static final String UNMARK_COMMAND_PREFIX = "unmark ";
    private static final String TODO_COMMAND_PREFIX = "todo ";
    private static final String DEADLINE_COMMAND_PREFIX = "deadline ";
    private static final String EVENT_COMMAND_PREFIX = "event ";

    private static final String BY_DELIMITER = " /by ";
    private static final String FROM_DELIMITER = " /from ";
    private static final String TO_DELIMITER = " /to ";

    private static final Ui ui = new Ui();
    private static final TaskList taskList = new TaskList();
    private static final Storage storage = new Storage();

    public static void main(String[] args) {
        String banner = " _   _  ___ ____ ___  \n"
                + "| \\ | |/ _ \\___ \\__ \\ \n"
                + "|  \\| | | |__) | ) |\n"
                + "| |\\  | |_| / __/ / / \n"
                + "|_| \\_|\\___/_____|___|\n";

        ui.printGreeting(banner);
        storage.load(taskList);
        runCommandLoop();
        ui.printFarewell();
    }

    private static void runCommandLoop() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals(BYE_COMMAND)) {
            ui.printDivider();
            handleCommand(command);
            ui.printDivider();
            command = scanner.nextLine();
        }
    }

    private static void handleCommand(String command) {
        if (command.equals(LIST_COMMAND)) {
            ui.printTaskList(taskList);
        } else if (command.startsWith(MARK_COMMAND_PREFIX)) {
            markTask(command, true);
        } else if (command.startsWith(UNMARK_COMMAND_PREFIX)) {
            markTask(command, false);
        } else if (command.startsWith(TODO_COMMAND_PREFIX)) {
            handleTodo(command);
        } else if (command.startsWith(DEADLINE_COMMAND_PREFIX)) {
            handleDeadline(command);
        } else if (command.startsWith(EVENT_COMMAND_PREFIX)) {
            handleEvent(command);
        } else {
            ui.printMessage("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static void handleTodo(String command) {
        String description = command.substring(TODO_COMMAND_PREFIX.length()).trim();

        if (description.isEmpty()) {
            ui.printMessage("OOPS!!! The description of a todo cannot be empty.");
            return;
        }

        addTask(new Todo(description));
    }

    private static void handleDeadline(String command) {
        String remainder = command.substring(DEADLINE_COMMAND_PREFIX.length());
        String[] parts = remainder.split(BY_DELIMITER, 2);

        if (parts.length < 2) {
            ui.printMessage("Please specify a deadline using: deadline <description> /by <date>");
            return;
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            ui.printMessage("OOPS!!! The description of a deadline cannot be empty.");
            return;
        }
        if (by.isEmpty()) {
            ui.printMessage("OOPS!!! The /by date of a deadline cannot be empty.");
            return;
        }

        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String command) {
        String remainder = command.substring(EVENT_COMMAND_PREFIX.length());
        String[] fromParts = remainder.split(FROM_DELIMITER, 2);

        if (fromParts.length < 2) {
            ui.printMessage("Please specify an event using: event <description> /from <start> /to <end>");
            return;
        }

        String[] toParts = fromParts[1].split(TO_DELIMITER, 2);

        if (toParts.length < 2) {
            ui.printMessage("Please specify an event using: event <description> /from <start> /to <end>");
            return;
        }

        String description = fromParts[0].trim();
        String from = toParts[0].trim();
        String to = toParts[1].trim();

        if (description.isEmpty()) {
            ui.printMessage("OOPS!!! The description of an event cannot be empty.");
            return;
        }
        if (from.isEmpty() || to.isEmpty()) {
            ui.printMessage("OOPS!!! The /from and /to times of an event cannot be empty.");
            return;
        }

        addTask(new Event(description, from, to));
    }

    private static void addTask(Task task) {
        if (taskList.isFull()) {
            ui.printMessage("OOPS!!! Your task list is full (max " + taskList.getMaxTasks() + " tasks).");
            return;
        }

        taskList.add(task);
        ui.printMessage("Got it. I've added this task:");
        ui.printMessage("  " + task);
        ui.printMessage("Now you have " + taskList.size() + " tasks in the list.");
        storage.save(taskList);
    }

    private static void markTask(String command, boolean isDone) {
        int prefixLength = isDone ? MARK_COMMAND_PREFIX.length() : UNMARK_COMMAND_PREFIX.length();
        String commandName = isDone ? "mark" : "unmark";

        try {
            int index = Integer.parseInt(command.substring(prefixLength).trim()) - 1;
            Task task = taskList.setDone(index, isDone);

            if (isDone) {
                ui.printMessage("Nice! I've marked this task as done:");
            } else {
                ui.printMessage("OK, I've marked this task as not done yet:");
            }
            ui.printMessage("  " + task);
            storage.save(taskList);
        } catch (NumberFormatException e) {
            ui.printMessage("OOPS!!! Please enter a valid task number, e.g. " + commandName + " 2");
        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("OOPS!!! That task number doesn't exist. You have " + taskList.size() + " task(s).");
        }
    }
}