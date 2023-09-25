package ru.marat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoadCommand implements Command {
    private final VectorRepository vectorRepository;

    public LoadCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            var lines = Files.readAllLines(Path.of(args[0]));
            for (var line : lines) {
                var splitLine = line.split("\\|");
                vectorRepository.addVector(splitLine[0], Vector3d.parseVector3d(splitLine[1]));
            }
            System.out.println("LOADED");
        } catch (IOException e) {
            throw new UncheckedIOException("Не получилось считать вектора из файла", e);
        }
    }
}
