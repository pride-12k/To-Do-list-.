import java.io.*;
import java.util.*;

public class TaskManager {

    private ArrayList<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    public TaskManager() {
        loadTasks();
    }

    public void addTask(String title) {
        tasks.add(new Task(title));
        saveTasks();
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
            saveTasks();
            System.out.println("Task completed.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.saveFormat());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);

        if (!file.exists())
            return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                tasks.add(new Task(parts[0], Boolean.parseBoolean(parts[1])));
            }
        } catch (Exception e) {
            System.out.println("Error loading tasks.");
        }
    }
}
