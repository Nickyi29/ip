package nico;

import nico.tasks.Deadline;
import nico.tasks.Event;
import nico.tasks.Task;
import nico.tasks.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Nico {
    private static final String DIVIDER =
            "____________________________________________________________";

    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

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

    private static final String SAVE_FILE_PATH = "data/nico.txt";
    private static final String SAVE_FIELD_DELIMITER = " | ";

    public static void main(String[] args) {
        String banner = " _   _  ___ ____ ___  \n"
                + "| \\ | |/ _ \\___ \\__ \\ \n"
                + "|  \\| | | |__) | ) |\n"
                + "| |\\  | |_| / __/ / / \n"
                + "|_| \\_|\\___/_____|___|\n";

        printGreeting(banner);
        loadTasks();
        runCommandLoop();
        printFarewell();
    }

    private static void runCommandLoop() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while (!command.equals(BYE_COMMAND)) {
            System.out.println(DIVIDER);
            handleCommand(command);
            System.out.println(DIVIDER);
            command = scanner.nextLine();
        }
    }

    private static void handleCommand(String command) {
        if (command.equals(LIST_COMMAND)) {
            printTaskList();
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
            System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static void handleTodo(String command) {
        String description = command.substring(TODO_COMMAND_PREFIX.length()).trim();

        if (description.isEmpty()) {
            System.out.println("OOPS!!! The description of a todo cannot be empty.");
            return;
        }

        addTask(new Todo(description));
    }

    private static void handleDeadline(String command) {
        String remainder = command.substring(DEADLINE_COMMAND_PREFIX.length());
        String[] parts = remainder.split(BY_DELIMITER, 2);

        if (parts.length < 2) {
            System.out.println("Please specify a deadline using: deadline <description> /by <date>");
            return;
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            System.out.println("OOPS!!! The description of a deadline cannot be empty.");
            return;
        }
        if (by.isEmpty()) {
            System.out.println("OOPS!!! The /by date of a deadline cannot be empty.");
            return;
        }

        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String command) {
        String remainder = command.substring(EVENT_COMMAND_PREFIX.length());
        String[] fromParts = remainder.split(FROM_DELIMITER, 2);

        if (fromParts.length < 2) {
            System.out.println("Please specify an event using: event <description> /from <start> /to <end>");
            return;
        }

        String[] toParts = fromParts[1].split(TO_DELIMITER, 2);

        if (toParts.length < 2) {
            System.out.println("Please specify an event using: event <description> /from <start> /to <end>");
            return;
        }

        String description = fromParts[0].trim();
        String from = toParts[0].trim();
        String to = toParts[1].trim();

        if (description.isEmpty()) {
            System.out.println("OOPS!!! The description of an event cannot be empty.");
            return;
        }
        if (from.isEmpty() || to.isEmpty()) {
            System.out.println("OOPS!!! The /from and /to times of an event cannot be empty.");
            return;
        }

        addTask(new Event(description, from, to));
    }

    private static void addTask(Task task) {
        if (taskCount >= MAX_TASKS) {
            System.out.println("OOPS!!! Your task list is full (max " + MAX_TASKS + " tasks).");
            return;
        }

        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        saveTasks();
    }

    private static void printTaskList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    private static void markTask(String command, boolean isDone) {
        int prefixLength = isDone ? MARK_COMMAND_PREFIX.length() : UNMARK_COMMAND_PREFIX.length();
        String commandName = isDone ? "mark" : "unmark";

        try {
            int index = Integer.parseInt(command.substring(prefixLength).trim()) - 1;
            Task task = tasks[index];

            if (isDone) {
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done:");
            } else {
                task.markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
            }
            System.out.println("  " + task);
            saveTasks();
        } catch (NumberFormatException e) {
            System.out.println("OOPS!!! Please enter a valid task number, e.g. " + commandName + " 2");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("OOPS!!! That task number doesn't exist. You have " + taskCount + " task(s).");
        }
    }

    private static void printGreeting(String banner) {
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm NICO.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    private static void printFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    private static void saveTasks() {
        try {
            File saveFile = new File(SAVE_FILE_PATH);
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(saveFile);
            for (int i = 0; i < taskCount; i++) {
                writer.write(taskToSaveFormat(tasks[i]) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("OOPS!!! I couldn't save your tasks to disk: " + e.getMessage());
        }
    }

    private static String taskToSaveFormat(Task task) {
        String doneFlag = task.isDone() ? "1" : "0";

        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D" + SAVE_FIELD_DELIMITER + doneFlag + SAVE_FIELD_DELIMITER
                    + deadline.getDescription() + SAVE_FIELD_DELIMITER + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E" + SAVE_FIELD_DELIMITER + doneFlag + SAVE_FIELD_DELIMITER
                    + event.getDescription() + SAVE_FIELD_DELIMITER
                    + event.getFrom() + SAVE_FIELD_DELIMITER + event.getTo();
        } else {
            return "T" + SAVE_FIELD_DELIMITER + doneFlag + SAVE_FIELD_DELIMITER + task.getDescription();
        }
    }

    private static void loadTasks() {
        File saveFile = new File(SAVE_FILE_PATH);
        if (!saveFile.exists()) {
            return;
        }

        try {
            Scanner fileScanner = new Scanner(saveFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = parseSaveLine(line);
                if (task != null && taskCount < MAX_TASKS) {
                    tasks[taskCount] = task;
                    taskCount++;
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("OOPS!!! I couldn't find the save file, starting with an empty list.");
        }
    }

    private static Task parseSaveLine(String line) {
        String[] fields = line.split(java.util.regex.Pattern.quote(SAVE_FIELD_DELIMITER));
        if (fields.length < 3) {
            return null;
        }

        String type = fields[0];
        boolean isDone = fields[1].equals("1");
        String description = fields[2];

        Task task;
        if (type.equals("D") && fields.length >= 4) {
            task = new Deadline(description, fields[3]);
        } else if (type.equals("E") && fields.length >= 5) {
            task = new Event(description, fields[3], fields[4]);
        } else if (type.equals("T")) {
            task = new Todo(description);
        } else {
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}