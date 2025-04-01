package com.UwUrahara.pet1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    private final TaskRepository taskRepository = mock(TaskRepository.class);

    private final TaskService taskService = new TaskServiceImpl(taskRepository);

    @Test
    public void addTask() {
        //Given
        String name = "Test Task";
        String description = "Test Description";
        LocalDate date = LocalDate.now();
        //When
        taskService.addTask(name, description, date);
        //Then
        verify(taskRepository).add(argThat(task ->
                task.getName().equals(name) &&
                        task.getDescription().equals(description) &&
                        task.getDate().equals(date) &&
                        task.getStatus() == 0
        ));
    }

    @Test
    public void deleteTask() throws Exception {
        //Given
        //When
        when(taskRepository.getListSize()).thenReturn(1);
        taskService.deleteTask(1);
        //Then
        verify(taskRepository).delete(0);
    }

    @Test
    public void deleteTaskWithException() {
        //Given
        // When
        when(taskRepository.getListSize()).thenReturn(1);
        //Then
        assertThrows(Exception.class, () -> taskService.deleteTask(2));
        assertThrows(Exception.class, () -> taskService.deleteTask(0));
    }

    @Test
    public void editTask() throws Exception {
        //Given
        Task mockTask = new Task("Old", "Old Desc", LocalDate.now(), 0);
        when(taskRepository.getListSize()).thenReturn(1);
        when(taskRepository.getByNumber(0)).thenReturn(mockTask);
        //When
        taskService.editTask(1, 1, "New Name");
        //Then
        assertEquals("New Name", mockTask.getName());
        //When
        taskService.editTask(1, 4, "");
        //Then
        assertEquals(1, mockTask.getStatus());

        verify(taskRepository, times(2)).update(eq(0), eq(mockTask));
    }

    @Test
    public void editTaskWithExceptionByTaskNumber() {
        //Given
        // When
        when(taskRepository.getListSize()).thenReturn(1);
        //Then
        assertThrows(Exception.class, () -> taskService.editTask(2, 1, "New"));
        assertThrows(Exception.class, () -> taskService.editTask(0, 1, "New"));
    }

    @Test
    public void editTaskWithExceptionByPartNumber() {
        //Given
        // When
        when(taskRepository.getListSize()).thenReturn(1);
        //Then
        assertThrows(Exception.class, () -> taskService.editTask(1, 5, "New"));
        assertThrows(Exception.class, () -> taskService.editTask(1, 0, "New"));
    }

    @Test
    public void sortTasks() throws Exception {
        //Given
        Task task1 = new Task("B", "Desc1", LocalDate.of(2023, 1, 2), 1);
        Task task2 = new Task("A", "Desc2", LocalDate.of(2023, 1, 1), 2);
        List<Task> tasks = Arrays.asList(task1, task2);
        //When
        when(taskRepository.getAll()).thenReturn(tasks);
        //Then
        // Sort by name ascending
        taskService.sortTaskList(1, 1);
        verify(taskRepository).saveAll(argThat(list ->
                list.getFirst().getName().equals("A") &&
                        list.get(1).getName().equals("B")
        ));
    }

    @Test
    public void sortTasksWithException() {
        assertThrows(Exception.class, () -> taskService.sortTaskList(4, 1));
        assertThrows(Exception.class, () -> taskService.sortTaskList(0, 1));
    }

    @Test
    public void filterTask() throws Exception {
        //Given
        Task task1 = new Task("A", "Desc1", LocalDate.now(), 0);
        Task task2 = new Task("B", "Desc2", LocalDate.now(), 1);
        List<Task> tasks = Arrays.asList(task1, task2);
        //When
        when(taskRepository.getAll()).thenReturn(tasks);
        //Then
        List<Task> filtered = taskService.filterTaskList(1);
        assertEquals(1, filtered.size());
        assertEquals("B", filtered.getFirst().getName());
    }

    @Test
    public void filterTaskWithException() {
        assertThrows(Exception.class, () -> taskService.filterTaskList(-1));
        assertThrows(Exception.class, () -> taskService.filterTaskList(3));
    }

    @Test
    public void getAll() {
        //Given
        List<Task> expectedTasks = Arrays.asList(
                new Task("A", "Desc", LocalDate.now(), 0),
                new Task("B", "Desc", LocalDate.now(), 1)
        );
        //When
        when(taskRepository.getAll()).thenReturn(expectedTasks);
        //Then
        List<Task> actualTasks = taskService.getAll();
        assertEquals(expectedTasks, actualTasks);
        verify(taskRepository).getAll();
    }
}