package ru.marat.repository;

import ru.marat.Vector3d;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VectorRepositorySaver {
    private final VectorRepository vectorRepository;

    public VectorRepositorySaver(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    public void save(Path pathToFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (var namedVector : vectorRepository.getAll()) {
            sb.append("%s|%s%n".formatted(namedVector.name(), namedVector.object()));
        }
        Files.writeString(pathToFile, sb.toString());
    }

    public void load(Path pathToFile) throws IOException {
        var lines = Files.readAllLines(pathToFile);
        for (var line : lines) {
            var splitLine = line.split("\\|");
            if (splitLine.length != 2) {
                throw new IOException("Неправильный формат данных в файле");
            }
            vectorRepository.addVector(splitLine[0], Vector3d.parseVector3d(splitLine[1]));
        }
    }
}
