package com.UwUrahara.pet1.controller;

import com.UwUrahara.pet1.entity.Task;
import com.UwUrahara.pet1.enumeration.PartOption;
import com.UwUrahara.pet1.service.TaskService;
import com.UwUrahara.pet1.TaskTracker;

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
        while (true) {
            System.out.println("\n[1]Add   [2]List   [3]Edit   [4]Delete   [5]Filter   [6]Sort   [0]Exit");
            System.out.print("Введите номер действия: ");
            int numberOfAction = Integer.parseInt(scanner.nextLine());

            if (numberOfAction == 0) {
                break;
            }

            switch (numberOfAction) {
                case 1 -> add();
                case 2 -> showList();
                case 3 -> edit();
                case 4 -> delete();
                case 5 -> filterList();
                case 6 -> sortList();
                default -> System.out.println("Invalid number");
            }
            System.out.println("\n");
        }
    }

    @Override
    public void add() {
        System.out.print("Введите название задачи: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание: ");
        String description = scanner.nextLine();
        System.out.print("Введите дедлайн в формате ДД.ММ.ГГГГ: ");
        LocalDate date = TaskTracker.dateFromString(scanner.nextLine());
        taskService.addTask(name, description, date);
    }

    @Override
    public void delete() {
        System.out.print("Введите номер задачи, которую хотите удалить: ");
        int taskNumber = Integer.parseInt(scanner.nextLine());
        try {
            taskService.deleteTask(taskNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.print("Введите номер задачи, которую хотите отредактировать: ");
        int taskNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("[1]Название   [2]Описание   [3]Дедлайн   [4]Статус");
        System.out.print("Выберите, что именно необходимо отредактировать, и введитe немер: ");
        int partNumber = Integer.parseInt(scanner.nextLine());
        String newText = null;
        if (PartOption.getByIndex(partNumber) != PartOption.STATUS) {
            System.out.print("Введите новое значение и нажмите ENTER, чтобы отправить: ");
        } else {
            System.out.println("[1]To do   [2]In progress   [3]Done");
            System.out.print("Введите номер нового значения: ");
        }
        newText = scanner.nextLine();
        try {
            taskService.editTask(taskNumber, partNumber, newText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sortList() {
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
    public void filterList() {System.out.println("[0]To do   [1]In progress   [2]Done");
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

    private void printItem(int index, Task task) {
        int number = index + 1;
        System.out.println((number) + ") " + task.getName());
        System.out.println(task.getDescription());
        System.out.println(task.getDate());
        switch (task.getStatus()) {
            case TO_DO -> System.out.println("TO DO");
            case IN_PROGRESS -> System.out.println("IN PROGRESS");
            case DONE -> System.out.println("DONE");
        }
    }
}
