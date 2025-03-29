package com.UwUrahara.pet1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TaskTracker {

    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepositoryImpl();
        Scanner scanner = new Scanner(System.in);
        TaskService taskService = new TaskServiceImpl(taskRepository);
        TaskController taskController = new TaskControllerImpl(taskService, scanner);
        taskController.showMenu();
    }

    public static LocalDate dateFromString(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
