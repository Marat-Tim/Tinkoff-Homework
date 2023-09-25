package ru.marat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveCommand implements Command {
    private final VectorRepository vectorRepository;

    public SaveCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (var namedVector : vectorRepository.getAll()) {
            sb.append("%s|%s\n".formatted(namedVector.name(), namedVector.object()));
        }
        try {
            Files.writeString(Path.of(args[0]), sb.toString());
        } catch (IOException e) {
            throw new UncheckedIOException("Не получилось записать вектора в файл", e);
        }
        System.out.println("SAVED");
    }
}
