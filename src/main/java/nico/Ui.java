package nico;

public class Ui {
    private static final String DIVIDER =
            "____________________________________________________________";

    public void printGreeting(String banner) {
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm NICO.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    public void printFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }

    public void printDivider() {
        System.out.println(DIVIDER);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printTaskList(TaskList taskList) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + "." + taskList.get(i));
        }
    }
}