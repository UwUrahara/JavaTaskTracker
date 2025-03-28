package com.UwUrahara.pet1;

import java.util.List;

public interface TaskService {
    void showList();
    void addTask();
    void deleteTask();
    void editTask();
    void sortTaskList();
    void filterTaskList();

    void printItem(int number, List<Task> taskList);

    int enterCheck(int max, int min);
}
