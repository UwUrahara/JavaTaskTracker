package com.UwUrahara.pet1;

import java.util.Scanner;

public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;
    public TaskControllerImpl(TaskService taskService) {
        this.taskService = taskService;
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
                case 1 -> taskService.addTask();
                case 2 -> taskService.showList();
                case 3 -> taskService.editTask();
                case 4 -> taskService.deleteTask();
                case 5 -> taskService.filterTaskList();
                case 6 -> taskService.sortTaskList();
                default -> System.out.println("Invalid number");
            }
            System.out.println("\n");
        }
    }
}
