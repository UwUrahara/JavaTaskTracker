package com.UwUrahara.pet1.service;

import com.UwUrahara.pet1.entity.Task;
import com.UwUrahara.pet1.enumeration.PartOption;
import com.UwUrahara.pet1.enumeration.Status;
import com.UwUrahara.pet1.repository.TaskRepository;

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
        taskRepository.add(new Task(name, description, date, Status.TO_DO));
    }

    @Override
    public void deleteTask(int taskNumber) throws Exception {
        taskNumber--;
        if (numberIsInvalid(taskNumber, taskRepository.getListSize())) {
            throw new Exception("Неверный номер задачи");
        }
        taskRepository.delete(taskNumber);
    }

    @Override
    public void editTask(int taskNumber, int partNumber, String newText) throws Exception {
        taskNumber--;
        if (numberIsInvalid(taskNumber, taskRepository.getListSize())) {
            throw new Exception("Неверный номер задачи");
        }
        Task current = taskRepository.getByNumber(taskNumber);

        switch (PartOption.getByIndex(partNumber)) {
            case NAME -> current.setName(newText);
            case DESCRIPTION -> current.setDescription(newText);
            case DATE -> current.setDate(newText);
            case STATUS -> {
                if (current.getStatus() != Status.DONE) {
                    current.setStatus(Status.getByIndex(current.getStatus().ordinal()+1));
                }
            }
        }
        taskRepository.update(taskNumber, current);
    }

    @Override
    public void sortTaskList(int partNumber, int sortNumber) {
        boolean reversed = sortNumber != 1;
        List<Task> taskList = taskRepository.getAll();

        switch (PartOption.getByIndex(partNumber)) {
            case NAME -> taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getName)))
                    .collect(Collectors.toList());
            case DATE -> taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getDate)))
                    .collect(Collectors.toList());
            case STATUS -> taskList = taskList.stream()
                    .sorted(reverseIfNeed(reversed, Comparator.comparing(Task::getStatus)))
                    .collect(Collectors.toList());
            default -> throw new IllegalArgumentException("Неверное число: " + partNumber);
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
            throw new Exception("Неверное число");
        }
        return taskRepository.getAll().stream()
                .filter(task -> numberOfStatus == task.getStatus().ordinal())
                .collect(Collectors.toList());
    }

    private boolean numberIsInvalid(int number, int max) {
        return !(number >= 0 && number < max);
    }
}
