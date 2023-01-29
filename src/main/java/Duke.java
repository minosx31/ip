import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duke {
    private static String numOfTasks(ArrayList<Task> tasks) {
        return tasks.size() == 1 ? " task " : " tasks ";
    }

    public static void main(String[] args) throws IncorrectInputException {
        Pattern mark = Pattern.compile("(mark|unmark)([0-9])?");
        Pattern index = Pattern.compile("(mark|unmark|delete)?([0-9])");
        Pattern delete = Pattern.compile("(delete)\\s([0-9])");
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner sc = new Scanner(System.in );

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello I'm Duke!\n" + "How can I help you?");

        while (true) {
            try {
                String input = sc.nextLine();
                Matcher matchMark = mark.matcher(input);
                Matcher matchIndex = index.matcher(input);
                Matcher matchDelete = delete.matcher(input);
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again!");
                    break;
                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list");
                    if (tasks.size() != 0) {
                        for (int i=0; i < tasks.size(); i++) {
                            System.out.println((i+1) + "." + tasks.get(i).toString());
                        }
                    }
                } else if (matchMark.find() && matchIndex.find()) {
                    Task currTask = tasks.get(Integer.parseInt(matchIndex.group())-1);
                    if (matchMark.group().equals("mark")) {
                        currTask.markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("[" + currTask.getStatusIcon() + "] " + currTask.getDescription());
                    } else if (matchMark.group().equals("unmark")) {
                        currTask.markAsUndone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("[" + currTask.getStatusIcon() + "] " + currTask.getDescription());
                    }
                } else if (Task.isTask(input)) {
                    String type = Task.taskType(input);
                    Task toAdd = null;
                    if (type.equals("todo")) {
                        try {
                            toAdd = new Todo(Todo.description(input));
                            tasks.add(toAdd);
                        } catch (IllegalStateException e) {
                            throw new IncorrectInputException("Enter a description!");
                        }
                    } else if (type.equals("deadline")) {
                        try {
                            toAdd = new Deadline(Deadline.description(input), Deadline.deadline(input));
                            tasks.add(toAdd);
                        } catch (IllegalStateException e) {
                            throw new IncorrectInputException("Enter a description with a deadline!");
                        }
                    } else if (type.equals("event")) {
                        try {
                            toAdd = new Event(Event.description(input), Event.period(input));
                            tasks.add(toAdd);
                        } catch (IllegalStateException e) {
                            throw new IncorrectInputException("Enter a description and event period!");
                        }
                    }
                    System.out.println("Got it. I've added this task:");
                    System.out.println(toAdd.toString());
                    System.out.println("Now you have " + tasks.size() + Duke.numOfTasks(tasks) + "in the list.");
                } else if (matchDelete.find()) {
                    Task toDelete = tasks.get(Integer.parseInt(matchDelete.group(2))-1);
                    System.out.println("Alright, removing this task:");
                    System.out.println(toDelete.toString());
                    tasks.remove(toDelete);
                    System.out.println("Now you have " + tasks.size() + Duke.numOfTasks(tasks) + "in the list.");
                } else {
                    throw new IncorrectInputException("Enter a valid task!");
                }
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
