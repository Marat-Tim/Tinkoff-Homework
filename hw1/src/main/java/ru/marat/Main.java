package ru.marat;

import ru.marat.repository.InMemoryVectorRepository;
import ru.marat.repository.VectorRepository;

class Main {
    public static void main(String[] args) {
        VectorRepository vectorRepository = new InMemoryVectorRepository();
        try {
            new CommandHandler(vectorRepository).start();
        } catch (Exception e) {
            System.out.printf("Неожиданная ошибка:\n%s\n", e.getMessage());
        }
    }
}