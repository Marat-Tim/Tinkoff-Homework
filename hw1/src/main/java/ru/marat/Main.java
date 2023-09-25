package ru.marat;

public class Main {
    public static void main(String[] args) {
        VectorRepository vectorRepository = new InMemoryVectorRepository();
        try {
            new CommandHandler(vectorRepository).start();
        } catch (Exception e) {
            System.out.printf("Неожиданная ошибка:\n%s\n", e.getMessage());
        }
    }
}