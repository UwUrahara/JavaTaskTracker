package com.UwUrahara.pet1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskControllerImplTest {
    @Mock
    private TaskService taskService;
    private TaskController taskController;

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
    public void showMenu() {
        // Given
        String input = "2\n2\n0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        taskController = new TaskControllerImpl(taskService, new Scanner(in));
        // When
        taskController.showMenu();
        // Then
        verify(taskService, times(2)).getAll();
    }

    @Test
    public void addTask() {
        // Given
        String input = """
                1
                qwe
                qwer
                26.05.2024
                0""";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        taskController = new TaskControllerImpl(taskService, new Scanner(in));
        // When
        taskController.addTask();
        // Then
        verify(taskService, times(1))
                .addTask("qwe", "qwer", TaskTracker.dateFromString("26.05.2024"));
    }

    @Test
    public void deleteTask() {

    }

    @Test
    public void deleteTaskWithException () {

    }

    @Test
    public void editTask() {

    }

    @Test
    public void editTaskWithException() {

    }

    @Test
    public void editTaskNumberFour() {

    }
    @Test
    public void sortTask() {

    }

    @Test
    public void sortTaskWithException() {

    }

    @Test
    public void filterTask() {

    }

    @Test
    public void filterTaskWithException() {}
}