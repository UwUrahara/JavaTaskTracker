package com.UwUrahara.pet1.enumeration;

public enum Status {
    TO_DO(1),
    IN_PROGRESS(2),
    DONE(3);

    private final int statusIndex;

    Status(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public static Status getByIndex(int number) {
        for (Status status: values()) {
            if (status.statusIndex == number) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неверное число: " + number);
    }
}
