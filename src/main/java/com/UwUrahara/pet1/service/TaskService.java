package com.UwUrahara.pet1.service;

import com.UwUrahara.pet1.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    void addTask(String name, String description, LocalDate date);
    void deleteTask(int taskNumber) throws Exception;
    void editTask(int taskNumber, int partNumber, String newText) throws Exception;
    void sortTaskList(int partNumber, int sortNumber) throws Exception;
    List<Task> filterTaskList(int numberOfStatus) throws Exception;

    List<Task> getAll();
}
