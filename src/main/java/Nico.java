import java.util.Scanner;

public class Nico {
    private static final String DIVIDER =
            "____________________________________________________________";

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

    private static void printGreeting(String banner) {
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm NICO.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    private static void runCommandLoop() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while(!command.equals("bye")) {
            System.out.println(DIVIDER);
            System.out.println(command);
            System.out.println(DIVIDER);
            command = scanner.nextLine();
        }
    }

    private static void handleCommand(String command){
        if (command.equals("list")){
            printTaskList();
        } else{
            addTask(command);
        }

        else if(command.equals("blah")){
            printBlah();
        }

    }


    private static void printTaskList(){
        System.out.println("list");
    }

    private static void printBlah(){
        System.out.println("blah");
    }


    private static void printFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }
}