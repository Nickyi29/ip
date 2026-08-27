import java.util.Scanner;

public class Nico {
    private static final String DIVIDER =
            "____________________________________________________________";
    private static final String[] tasks = new String[100];
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

        while (!command.equals("bye")) {
            System.out.println(DIVIDER);
            handleCommand(command);
            System.out.println(DIVIDER);
            command = scanner.nextLine();
        }
    }

    private static void handleCommand(String command) {
        if (command.equals("list")) {
            printTaskList();
        } else {
            addTask(command);
        }
    }

    private static void addTask(String description) {
        tasks[taskCount] = description;
        taskCount++;
        System.out.println("added: " + description);
    }

    private static void printTaskList() {
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
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
}