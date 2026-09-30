# Nico User Guide

Nico is a command-line chatbot that keeps track of your todos, deadlines and events. Type short commands, and Nico saves your list automatically so it's still there next time you open it.

## Quick start

1. Make sure you have Java 25 installed.
2. Download the latest `nico.jar` from the [Releases page](https://github.com/Nickyi29/ip/releases).
3. Copy it into an empty folder, open a terminal in that folder, and run:
   ```
   java -jar nico.jar
   ```
4. Type a command and press Enter. Type `bye` to exit.

## Features

> **Notes about the command format**
> - Words in `UPPER_CASE` are details you supply, e.g. in `todo DESCRIPTION`, `DESCRIPTION` could be `read book`.
> - `TASK_NUMBER` is the number shown next to the task in `list`.
> - Command words are not case-sensitive (`LIST` works the same as `list`).

### Adding a todo: `todo`

Adds a task with no date attached.

Format: `todo DESCRIPTION`

Example: `todo read book`

```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task(s) in the list.
```

### Adding a deadline: `deadline`

Adds a task that must be done by a certain time.

Format: `deadline DESCRIPTION /by DATE`

Example: `deadline return book /by Sunday`

```
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 task(s) in the list.
```

### Adding an event: `event`

Adds a task with a start and end time.

Format: `event DESCRIPTION /from START /to END`

Example: `event project meeting /from Mon 2pm /to 4pm`

```
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 task(s) in the list.
```

### Listing all tasks: `list`

Shows every task. `[X]` means the task is done.

Format: `list`

```
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

### Marking a task as done: `mark`

Format: `mark TASK_NUMBER`

Example: `mark 1`

```
Nice! I've marked this task as done:
  [T][X] read book
```

### Marking a task as not done: `unmark`

Format: `unmark TASK_NUMBER`

Example: `unmark 1`

```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

### Deleting a task: `delete`

Format: `delete TASK_NUMBER`

Example: `delete 3`

```
Noted. I've removed this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 2 task(s) in the list.
```

### Finding tasks: `find`

Shows tasks whose description contains the keyword. The search ignores upper and lower case.

Format: `find KEYWORD`

Example: `find book`

```
Here are the matching tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: Sunday)
```

### Exiting: `bye`

Format: `bye`

### Saving your data

Your tasks are saved automatically after every change to `data/nico.txt`, in the folder you ran Nico from. There is no need to save manually.

## Command summary

| Command | Format | Example |
|---|---|---|
| Todo | `todo DESCRIPTION` | `todo read book` |
| Deadline | `deadline DESCRIPTION /by DATE` | `deadline return book /by Sunday` |
| Event | `event DESCRIPTION /from START /to END` | `event meeting /from Mon 2pm /to 4pm` |
| List | `list` | `list` |
| Mark | `mark TASK_NUMBER` | `mark 1` |
| Unmark | `unmark TASK_NUMBER` | `unmark 1` |
| Delete | `delete TASK_NUMBER` | `delete 3` |
| Find | `find KEYWORD` | `find book` |
| Exit | `bye` | `bye` |
