package com.UwUrahara.pet1.enumeration;

public enum PartOption {
    NAME(1),
    DESCRIPTION(2),
    DATE(3),
    STATUS(4);

    private final int partIndex;

    PartOption(int partIndex) {
        this.partIndex = partIndex;
    }

    public static PartOption getByIndex(int number) {
        for (PartOption option: values()) {
            if (option.partIndex == number) {
                return option;
            }
        }
        throw new IllegalArgumentException("Неверное число: " + number);
    }
}
