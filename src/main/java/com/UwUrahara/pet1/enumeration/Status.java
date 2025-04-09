package com.UwUrahara.pet1.enumeration;

public enum Status {
    TO_DO(1),
    IN_PROGRESS(2),
    DONE(3);

    private final int statusIndex;

    Status(int partIndex) {
        this.statusIndex = partIndex;
    }

    public static com.UwUrahara.pet1.enumeration.Status getByIndex(int number) {
        for (com.UwUrahara.pet1.enumeration.Status status: values()) {
            if (status.statusIndex == number) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неверное число: " + number);
    }
}
