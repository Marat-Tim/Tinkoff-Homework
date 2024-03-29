package ru.marat.command;

import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveCommand implements Command {
    private final VectorRepository vectorRepository;

    public SaveCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            StringBuilder sb = new StringBuilder();
            for (var namedVector : vectorRepository.getAll()) {
                sb.append("%s|%s\n".formatted(namedVector.name(), namedVector.object()));
            }
            Files.writeString(Path.of(args[0]), sb.toString());
            System.out.println("Векторы сохранены в файл");
        } catch (IncorrectArgSizeException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.printf("Не получилось сохранить векторы в файл\nОшибка: %s\n", e);
        }
    }
}
