package com.UwUrahara.pet1;

import java.util.Scanner;

public class TaskTracker {

    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepositoryImpl();
        Scanner scanner = new Scanner(System.in);
        TaskService taskService = new TaskServiceImpl(taskRepository, scanner);
        TaskController taskController = new TaskControllerImpl(taskService);
        taskController.showMenu();
    }
}
