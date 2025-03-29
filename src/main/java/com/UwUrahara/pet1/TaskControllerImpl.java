package com.UwUrahara.pet1;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;
    private final Scanner scanner;
    public TaskControllerImpl(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }


    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n[1]Add   [2]List   [3]Edit   [4]Delete   [5]Filter   [6]Sort   [0]Exit");
            System.out.print("Введите номер действия: ");
            int numberOfAction = Integer.parseInt(scanner.nextLine());

            if (numberOfAction == 0) {
                break;
            }

            switch (numberOfAction) {
                case 1 -> addTask();
                case 2 -> showList();
                case 3 -> editTask();
                case 4 -> deleteTask();
                case 5 -> filterTaskList();
                case 6 -> sortTaskList();
                default -> System.out.println("Invalid number");
            }
            System.out.println("\n");
        }
    }

    @Override
    public void addTask() {
        System.out.print("Введите название задачи: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание: ");
        String description = scanner.nextLine();
        System.out.print("Введите дедлайн в формате ДД.ММ.ГГГГ: ");
        LocalDate date = TaskTracker.dateFromString(scanner.nextLine());
        taskService.addTask(name, description, date);
    }

    @Override
    public void deleteTask() {
        System.out.print("Введите номер задачи, которую хотите удалить: ");
        int taskNumber = Integer.parseInt(scanner.nextLine());
        try {
            taskService.deleteTask(taskNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editTask() {
        System.out.print("Введите номер задачи, которую хотите отредактировать: ");
        int taskNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("[1]Название   [2]Описание   [3]Дедлайн   [4]Статус");
        System.out.print("Выберите, что именно необходимо отредактировать, и введитe немер: ");
        int partNumber = Integer.parseInt(scanner.nextLine());
        String newText = null;
        if (partNumber != 4) {
            System.out.print("Введите новое значение и нажмите ENTER, чтобы отправить: ");
            newText = scanner.nextLine();
        } else {
            System.out.println("Статус обновлён");
        }
        try {
            taskService.editTask(taskNumber, partNumber, newText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sortTaskList() {
        System.out.println("[1]Name   [2]Date   [3]Status");
        System.out.print("Введите номер поля, по которому необходимо отсортировать: ");
        int partNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("[1]По возрастанию   [2]По убыванию");
        System.out.println("Выберите порядок сортировки: ");
        int sortNumber = Integer.parseInt(scanner.nextLine());
        try {
            taskService.sortTaskList(partNumber, sortNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void filterTaskList() {System.out.println("[0]To do   [1]In progress   [2]Done");
        System.out.print("Введите номер статуса, по которому необходимо отфильтровать: ");
        int numberOfStatus = Integer.parseInt(scanner.nextLine());
        List<Task> taskList;
        try {
            taskList = taskService.filterTaskList(numberOfStatus);
            for (int i = 0; i < taskList.size(); i++) {
                printItem(i, taskList.get(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showList() {
        List<Task> taskList = taskService.getAll();
        for (int i = 0; i < taskList.size(); i++) {
            printItem(i, taskList.get(i));
        }
    }

    private void printItem(int number, Task task) {
        System.out.println((number+1) + ") " + task.getName());
        System.out.println(task.getDescription());
        System.out.println(task.getDate());
        switch (task.getStatus()) {
            case 0 -> System.out.println("TO DO");
            case 1 -> System.out.println("IN PROGRESS");
            case 2 -> System.out.println("DONE");
        }
    }
}
