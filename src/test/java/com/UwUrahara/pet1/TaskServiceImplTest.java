package com.UwUrahara.pet1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    private TaskService taskService;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void addTask() {}

    @Test
    public void deleteTask() {}

    @Test
    public void deleteTaskWithException() {}

    @Test
    public void editTask() {}

    @Test
    public void editTaskWithExceptionByTaskNumber() {}

    @Test
    public void editTaskWithExceptionByPartNumber() {}

    @Test
    public void sortTasks() {}

    @Test
    public void sortTasksWithException() {}

    @Test
    public void filterTask() {}

    @Test
    public void filterTaskWithException() {}

    @Test
    public void getAll() {}
}