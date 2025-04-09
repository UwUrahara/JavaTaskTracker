package com.UwUrahara.pet1.repository;

import com.UwUrahara.pet1.entity.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> getAll();

    void add(Task task);

    void delete(int taskNumber);

    Task getByNumber(int taskNumber);

    void update(int taskNumber, Task task);

    void deleteAll();

    void saveAll(List<Task> newTasks);

    int getListSize();
}
