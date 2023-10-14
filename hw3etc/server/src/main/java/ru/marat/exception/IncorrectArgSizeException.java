package ru.marat.exception;

public class IncorrectArgSizeException extends Exception {
    public IncorrectArgSizeException(String message) {
        super(message);
    }

    public static IncorrectArgSizeException defaultException(int actualSize, int expectedSize) {
        return new IncorrectArgSizeException("Неправильное количество аргументов(%s, а должно быть %s)"
                .formatted(actualSize, expectedSize));
    }
}
