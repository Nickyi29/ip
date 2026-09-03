import java.util.Scanner;

public class Nico {
    private static final String DIVIDER =
            "____________________________________________________________";

    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";
    private static int taskCount = 0;

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

        while (!command.equals(BYE_COMMAND)){
            System.out.println(DIVIDER);
            handleCommand(command);
            System.out.println(DIVIDER);
            command = scanner.nextLine();
        }
    }

    private static void handleCommand(String command) {
        if (command.equals(LIST_COMMAND)) {
            printTaskList();
        } else if (command.startsWith(MARK_COMMAND)) {
            markTask(command, true);
        } else if (command.startsWith(UNMARK_COMMAND)) {
            markTask(command, false);
        } else {
            addTask(command);
        }
    }

    private static void addTask(String description) {
        tasks[taskCount] = new Task(description);
        taskCount++;
        System.out.println("added: " + description);
    }

    private static void printTaskList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    private static void markTask(String command, boolean isDone) {
        int prefixLength = isDone ? MARK_COMMAND.length() : UNMARK_COMMAND.length();
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