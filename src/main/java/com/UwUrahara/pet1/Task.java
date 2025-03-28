package com.UwUrahara.pet1;

import java.time.LocalDate;
import java.util.Objects;

public class Task implements Comparable<Task>{

    private String name;
    private String description;
    private LocalDate date;
    private int status;

    public Task(String name, String description, LocalDate date, int status) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public Task(String description, LocalDate date, int status) {
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return(this.name);
    }
    public String getDescription() {
        return(this.description);
    }
    public LocalDate getDate() {
        return(this.date);
    }
    public int getStatus() {
        return(this.status);
    }

    @Override
    public boolean equals(Object o) {
        // 1. Проверка на ссылочное равенство
        if (this == o) return true;

        // 2. Проверка на null и совпадение классов
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Приведение типа
        Task task = (Task) o;

        // 4. Сравнение полей
        return status == task.status && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, date, status);
    }

    @Override
    public int compareTo(Task other) {
        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) {
            return nameCompare;
        }

        int descriptionCompare = this.description.compareTo(other.description);
        if (descriptionCompare != 0) {
            return descriptionCompare;
        }

        int dateCompare = this.date.compareTo(other.date);
        if (dateCompare != 0) {
            return dateCompare;
        }

        return Integer.compare(this.status, other.status);
    }
}

// STATUS https://www.cyberforum.ru/java-gui/thread1792099.html