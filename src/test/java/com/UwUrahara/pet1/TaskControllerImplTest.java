package com.UwUrahara.pet1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerImplTest {
    @Mock
    private TaskService taskService;
    @InjectMocks
    private TaskControllerImpl taskController;

    @Test
    void showMenu_ShouldCallAddTask_WhenInput1() {
        // Given
        String input = "1\n0\n"; // Вводим 1, затем 0 для выхода
        InputStream in = new ByteArrayInputStream(input.getBytes());

        System.setIn(in);
        // When
        taskController.showMenu();

        // Then
        verify(taskService, times(1)).addTask();
    }

    @Test
    void showMenu_ShouldCallShowList_WhenInput2() {
        String input = "2\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        taskController.showMenu();

        verify(taskService, times(1)).showList();
    }

    @Test
    void showMenu_ShouldExit_WhenInput0() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        taskController.showMenu();

        verifyNoInteractions(taskService);
    }

    @Test
    void showMenu_ShouldHandleInvalidInput() {
        String input = "99\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        taskController.showMenu();

        verifyNoInteractions(taskService);
    }

    @Test
    void showMenu_ShouldCallMultipleActions() {
        String input = "1\n2\n3\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        taskController.showMenu();

        verify(taskService, times(1)).addTask();
        verify(taskService, times(1)).showList();
        verify(taskService, times(1)).editTask();
    }
}