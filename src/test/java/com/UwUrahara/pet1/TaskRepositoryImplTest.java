package com.UwUrahara.pet1;

import com.UwUrahara.pet1.entity.Task;
import com.UwUrahara.pet1.enumeration.Status;
import com.UwUrahara.pet1.repository.TaskRepository;
import com.UwUrahara.pet1.repository.TaskRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class TaskRepositoryImplTest {
    private final TaskRepository taskRepository = new TaskRepositoryImpl();

    @Test
    public void getAll() {
        // Given
        // When
        List<Task> list = taskRepository.getAll();

        // Then
        Assertions.assertEquals(3, list.size());
    }

    @Test
    public void add() {
        //Given
        List<Task> list = taskRepository.getAll();
        int size = list.size();

        //When
        taskRepository.add(new Task("gh", "hjvjgh", LocalDate.of(2024, 5, 26), Status.TO_DO));
        List<Task> list1 = taskRepository.getAll();

        //Then
        Assertions.assertEquals(++size, list1.size());
    }

    @Test
    public void delete() {
        //Given
        List<Task> list = new ArrayList<>(taskRepository.getAll());
        int size = list.size();

        //When
        taskRepository.delete(0);
        List<Task> list1 = taskRepository.getAll();
        list.removeFirst();

        //Then
        Assertions.assertEquals(--size, list.size());
        Assertions.assertEquals(list, list1);

    }

    @Test
    public void getByNumber() {
        //Given
        List<Task> list = taskRepository.getAll();
        Task task = list.getFirst();

        //When
        Task task1 = taskRepository.getByNumber(0);

        //Then
        Assertions.assertEquals(task, task1);
    }

    @Test
    public void update() {
        //Given
        Task task = new Task("gh", "hjvjgh", LocalDate.of(2024, 5, 26), Status.TO_DO);

        //When
        taskRepository.update(1, task);
        Task task1 = taskRepository.getByNumber(1);

        //Then
        Assertions.assertEquals(task, task1);
    }

    @Test
    public void deleteAll() {
        //Given
        //When
        taskRepository.deleteAll();
        List<Task> list = taskRepository.getAll();

        //Then
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void saveAll() {
        //Given
        List<Task> list = taskRepository.getAll();
        int size = list.size();

        //When
        taskRepository.saveAll(list);

        //Then
        Assertions.assertEquals((size*2), list.size());
    }

    @Test
    public void getListSize() {
        //Given
        List<Task> list = taskRepository.getAll();

        //When
        int size = taskRepository.getListSize();

        //Then
        Assertions.assertEquals(list.size(), size);
    }
}