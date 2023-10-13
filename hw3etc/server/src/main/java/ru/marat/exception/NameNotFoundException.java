package ru.marat.exception;

public class NameNotFoundException extends Exception {
    public NameNotFoundException(String message) {
        super(message);
    }

    public static NameNotFoundException defaultException(String name) {
        return new NameNotFoundException("Вектор с именем %s не существует".formatted(name));
    }
}
