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

    public static void main(String[] args) {
        String banner = " _   _  ___ ____ ___  \n"
                + "| \\ | |/ _ \\___ \\__ \\ \n"
                + "|  \\| | | |__) | ) |\n"
                + "| |\\  | |_| / __/ / / \n"
                + "|_| \\_|\\___/_____|___|\n";

        printGreeting(banner);
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
        String description = command.substring(TODO_COMMAND_PREFIX.length());
        addTask(new Todo(description));
    }

    private static void handleDeadline(String command) {
        String remainder = command.substring(DEADLINE_COMMAND_PREFIX.length());
        String[] parts = remainder.split(BY_DELIMITER, 2);

        if (parts.length < 2) {
            System.out.println("Please specify a deadline using: deadline <description> /by <date>");
            return;
        }

        addTask(new Deadline(parts[0], parts[1]));
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

        addTask(new Event(fromParts[0], toParts[0], toParts[1]));
    }

    private static void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    private static void printTaskList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    private static void markTask(String command, boolean isDone) {
        int prefixLength = isDone ? MARK_COMMAND_PREFIX.length() : UNMARK_COMMAND_PREFIX.length();
        int index = Integer.parseInt(command.substring(prefixLength)) - 1;

        if (isDone) {
            tasks[index].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            tasks[index].markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + tasks[index]);
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
}