package com.UwUrahara.pet1;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<Task> getAll() {
        return taskRepository.getAll();
    }

    @Override
    public void addTask(String name, String description, LocalDate date) {
        taskRepository.add(new Task(name, description, date, 0));
    }

    @Override
    public void deleteTask(int taskNumber) throws Exception {
        taskNumber--;
        if (numberIsInvalid(taskNumber, taskRepository.getListSize())) {
            throw new Exception("Invalid task number");
        }
        taskRepository.delete(taskNumber);
    }

    @Override
    public void editTask(int taskNumber, int partNumber, String newText) throws Exception {
        taskNumber--;
        if (numberIsInvalid(taskNumber, taskRepository.getListSize())) {
            throw new Exception("Invalid task number");
        }
        Task current = taskRepository.getByNumber(taskNumber);

        switch (partNumber) {
            case 1 -> current.setName(newText);
            case 2 -> current.setDescription(newText);
            case 3 -> current.setDate(newText);
            case 4 -> {
                if (current.getStatus() < 2) {
                    current.setStatus(current.getStatus()+1);
                }
            }
            default -> throw new Exception("Invalid part number");
        }
        taskRepository.update(taskNumber, current);
    }

    @Override
    public void sortTaskList(int partNumber, int sortNumber) throws Exception {
        boolean reversed = sortNumber != 1;
        List<Task> taskList = taskRepository.getAll();

        switch (partNumber) {
            case 1 -> taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getName)))
                    .collect(Collectors.toList());
            case 2 -> taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getDate)))
                    .collect(Collectors.toList());
            case 3 -> taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getStatus)))
                    .collect(Collectors.toList());
            default -> throw new Exception("Invalid part number");
        }
        taskRepository.deleteAll();
        taskRepository.saveAll(taskList);
    }
    private static Comparator<Task> reverseIfNeed(Boolean reversed, Comparator<Task> comparator) {
        return reversed ? comparator.reversed() : comparator;
    }

    @Override
    public List<Task> filterTaskList(int numberOfStatus) throws Exception {
        if (numberIsInvalid(numberOfStatus, 2)) {
            throw new Exception("Invalid status");
        }
        return taskRepository.getAll().stream()
                .filter(task -> numberOfStatus == task.getStatus())
                .collect(Collectors.toList());
    }

    private boolean numberIsInvalid(int number, int max) {
        return !(number >= 0 && number <= max);
    }
}
