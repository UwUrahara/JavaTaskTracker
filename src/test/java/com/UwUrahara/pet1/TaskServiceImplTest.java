package com.UwUrahara.pet1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    private final TaskRepository taskRepository = mock(TaskRepository.class);
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
    void showList() {
        // Given
        List<Task> taskList = new ArrayList<>();
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

        when(taskRepository.getAll()).thenReturn(taskList);
        Scanner mockScanner = new Scanner(System.in);
        taskService = new TaskServiceImpl(taskRepository, mockScanner);
        // When
        taskService.showList();

        // Then
        verify(taskRepository, times(1)).getAll();
        Assertions.assertEquals("""
                1) Три
                Каждый из нас понимает очевидную вещь:\s
                перспективное планирование играет важную роль\s
                в формировании глубокомысленных рассуждений.
                2025-10-10
                TO DO
                2) Задачи
                В рамках спецификации современных стандартов,\s
                элементы политического процесса в равной степени\s
                предоставлены сами себе.
                2024-10-10
                TO DO
                3) Для начала
                Таким образом, высокотехнологичная концепция\s
                общественного уклада является качественно новой\s
                ступенью направлений прогрессивного развития!
                2023-10-10
                TO DO""", outputStreamCaptor.toString().trim());
    }

    @Test
    void addTask() {
        // Given
        String input = "Название\nОписание\n26.05.2007\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner mockScanner = new Scanner(in);
        Task task = new Task("Название", "Описание", LocalDate.of(2007, 5, 26), 0);

        taskService = new TaskServiceImpl(taskRepository, mockScanner);
        // When
        taskService.addTask();
        // Then
        verify(taskRepository, times(1)).add(task);
        Assertions.assertEquals("""
               Введите название задачи: Введите описание: Введите дедлайн в формате ДД.ММ.ГГГГ: Готово!""", outputStreamCaptor.toString().trim());
    }

    @Test
    void deleteTask() {
        //Given
        when(taskRepository.getListSize()).thenReturn(2);

        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner mockScanner = new Scanner(in);
        taskService = new TaskServiceImpl(taskRepository, mockScanner);

        // When
        taskService.deleteTask();

        // Then
        verify(taskRepository).delete(0);
    }

    @Test
    void editTask() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("Три",
                "Каждый из нас понимает очевидную вещь.",
                LocalDate.of(2025, 10, 10),
                0));

        when(taskRepository.getListSize()).thenReturn(1);
        when(taskRepository.getByNumber(0)).thenReturn(taskList.getFirst());

        String input = "1\n1\nПримерные\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner mockScanner = new Scanner(in);
        taskService = new TaskServiceImpl(taskRepository, mockScanner);
        // Создаем captor
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);

        //When
        taskService.editTask();

        //Then
        // Захватываем аргумент
        verify(taskRepository).update(eq(0), taskCaptor.capture());
        // Проверяем захваченный аргумент
        Task capturedUser = taskCaptor.getValue();
        assertEquals("Примерные", capturedUser.getName());
    }

    @Test
    void testSortTaskListByNameAscending() {
        String simulatedInput = "1\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        List<Task> unsortedTasks = Arrays.asList(
                new Task("Z Task", "Desc", LocalDate.of(2024, 5, 10), 0),
                new Task("A Task", "Desc", LocalDate.of(2024, 5, 10), 0)
        );

        when(taskRepository.getAll()).thenReturn(unsortedTasks);

        taskService = new TaskServiceImpl(taskRepository, scanner);
        taskService.sortTaskList();

        verify(taskRepository, times(1)).deleteAll();
        verify(taskRepository, times(1)).saveAll(argThat(sortedTasks ->
                sortedTasks.getFirst().getName().equals("A Task") &&
                        sortedTasks.get(1).getName().equals("Z Task")
        ));
    }
}