package com.UwUrahara.pet1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {

    private final List<Task> taskList = new ArrayList<>();

    {
        taskList.add(new Task("Три",
                "Каждый из нас понимает очевидную вещь: \nперспективное планирование играет важную роль \nв формировании глубокомысленных рассуждений.",
                LocalDate.of(2025, 10, 10),
                0));
        taskList.add(new Task("Задачи",
                "В рамках спецификации современных стандартов, \nэлементы политического процесса в равной степени \nпредоставлены сами себе.",
                LocalDate.of(2024, 10, 10),
                0));
        taskList.add(new Task("Для начала",
                "Таким образом, высокотехнологичная концепция \nобщественного уклада является качественно новой \nступенью направлений прогрессивного развития!",
                LocalDate.of(2023, 10, 10),
                0));
    }
    @Override
    public List<Task> getAll() {
        return taskList;
    }

    @Override
    public void add(Task task) {
        taskList.add(task);
    }

    @Override
    public void delete(int taskNumber) {
        taskList.remove(taskNumber);
    }

    @Override
    public Task getByNumber(int taskNumber) {
        return taskList.get(taskNumber);
    }

    @Override
    public void update(int taskNumber,Task task) {
        taskList.remove(taskNumber);
        taskList.add(taskNumber, task);
    }

    @Override
    public void deleteAll() {
        taskList.clear();
    }

    @Override
    public void saveAll(List<Task> taskList) {
        this.taskList.addAll(taskList);
    }

    @Override
    public int getListSize() {
        return taskList.size();
    }
}
