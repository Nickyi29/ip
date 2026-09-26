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

public class Storage {
    private static final String SAVE_FILE_PATH = "data/nico.txt";
    private static final String SAVE_FIELD_DELIMITER = " | ";

    public void save(TaskList taskList) {
        try {
            File saveFile = new File(SAVE_FILE_PATH);
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(saveFile);
            for (int i = 0; i < taskList.size(); i++) {
                writer.write(taskToSaveFormat(taskList.get(i)) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("OOPS!!! I couldn't save your tasks to disk: " + e.getMessage());
        }
    }

    public void load(TaskList taskList) {
        File saveFile = new File(SAVE_FILE_PATH);
        if (!saveFile.exists()) {
            return;
        }

        try {
            Scanner fileScanner = new Scanner(saveFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Task task = extractTask(line);
                if (task != null && !taskList.isFull()) {
                    taskList.add(task);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("OOPS!!! I couldn't find the save file, starting with an empty list.");
        }
    }

    private String taskToSaveFormat(Task task) {
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

    private Task extractTask(String line) {
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