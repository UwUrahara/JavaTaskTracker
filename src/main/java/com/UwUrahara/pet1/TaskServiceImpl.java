package com.UwUrahara.pet1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final Scanner scanner;

    public TaskServiceImpl(TaskRepository taskRepository, Scanner scanner) {
        this.taskRepository = taskRepository;
        this.scanner = scanner;
    }


    @Override
    public void showList() {
        List<Task> taskList = taskRepository.getAll();

        for (int number = 0; number < taskList.size(); number++) printItem(number, taskList);
    }

    @Override
    public void addTask() {

        System.out.print("Введите название задачи: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание: ");
        String description = scanner.nextLine();
        System.out.print("Введите дедлайн в формате ДД.ММ.ГГГГ: ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        taskRepository.add(new Task(name, description, date, 0));
        System.out.println("Готово!");
    }

    @Override
    public void deleteTask() {
        System.out.print("Введите номер задачи, которую хотите удалить: ");
        int taskNumber = enterCheck(taskRepository.getListSize(),1);
        taskRepository.delete(taskNumber-1);
        System.out.println("Готово!");
    }

    @Override
    public void editTask() {

        System.out.print("Введите номер задачи, которую хотите отредактировать: ");
        int taskNumber = enterCheck(taskRepository.getListSize(),1);
        taskNumber--;
        System.out.println("[1]Название   [2]Описание   [3]Дедлайн   [4]Статус");
        System.out.print("Выберите, что именно необходимо отредактировать, и введитe немер: ");
        int partNumber = enterCheck(4,1);
        Task current = taskRepository.getByNumber(taskNumber);
        switch (partNumber) {
            case 1 -> {
                System.out.print("Введите новое название и нажмите ENTER, чтобы отправить: ");
                String newText = scanner.nextLine();
                current.setName(newText);
            }
            case 2 -> {
                System.out.print("Введите новое описание и нажмите ENTER, чтобы отправить: ");
                String newText = scanner.nextLine();
                current.setDescription(newText);
            }
            case 3 -> {
                System.out.print("Введите новую дату и нажмите ENTER, чтобы отправить: ");
                String newText = scanner.nextLine();
                current.setDate(newText);
            }
            case 4 -> {
                System.out.println("Статус обновлён");
                if (current.getStatus() < 2) {
                    current.setStatus(current.getStatus()+1);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + partNumber);
        }
        taskRepository.update(taskNumber, current);
        System.out.println("Готово!");
    }

    @Override
    public void sortTaskList() {
        System.out.println("[1]Name   [2]Date   [3]Status");
        System.out.print("Введите номер поля, по которому необходимо отсортировать: ");
        int partNumber = enterCheck(3,1);
        System.out.println("[1]По возрастанию   [2]По убыванию");
        System.out.print("Выберите порядок сортировки: ");
        int sortNumber = enterCheck(2,1);
        boolean reversed = sortNumber != 1;

        List<Task> taskList = taskRepository.getAll();

        if (partNumber == 1) {
            taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getName)))
                    .collect(Collectors.toList());
        } else if (partNumber == 2) {
            taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getDate)))
                    .collect(Collectors.toList());
        } else if (partNumber == 3) {
            taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getStatus)))
                    .collect(Collectors.toList());
        }

        taskRepository.deleteAll();
        taskRepository.saveAll(taskList);

        System.out.println("Готово!");
    }
    private static Comparator<Task> reverseIfNeed(Boolean reversed, Comparator<Task> comparator) {
        return reversed ? comparator.reversed() : comparator;
    }

    @Override
    public void filterTaskList() {
        System.out.println("[0]To do   [1]In progress   [2]Done");
        System.out.print("Введите номер статуса, по которому необходимо отфильтровать: ");
        int numberOfStatus = enterCheck(2,0);

        List<Task> taskList = taskRepository.getAll();

        for (int number = 0; number < taskList.size(); number++){
            if (taskList.get(number).getStatus() == numberOfStatus) printItem(number, taskList);
        }

        System.out.println("Готово!");
    }

    @Override
    public void printItem(int number, List<Task> taskList) {
        System.out.println((number+1) + ") " + taskList.get(number).getName());
        System.out.println(taskList.get(number).getDescription());
        System.out.println(taskList.get(number).getDate());
        switch (taskList.get(number).getStatus()) {
            case 0 -> System.out.println("TO DO");
            case 1 -> System.out.println("IN PROGRESS");
            case 2 -> System.out.println("DONE");
        }
    }

    @Override
    public int enterCheck(int max, int min) {

        int number = Integer.parseInt(scanner.nextLine());
        if (number > max || number < min) {
            System.out.print("Неверное число, введите заново: ");
            number = enterCheck(max, min);
        }
        return number;
    }
}
