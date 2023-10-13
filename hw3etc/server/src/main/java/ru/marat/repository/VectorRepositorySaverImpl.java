package ru.marat.repository;

import org.springframework.stereotype.Component;
import ru.marat.Vector3d;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class VectorRepositorySaverImpl implements VectorRepositorySaver {
    @Override
    public void save(VectorRepository vectorRepository, Path pathToFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (var namedVector : vectorRepository.getAll()) {
            sb.append("%s|%s%n".formatted(namedVector.name(), namedVector.object()));
        }
        Files.writeString(pathToFile, sb.toString());
    }

    @Override
    public void load(VectorRepository vectorRepository, Path pathToFile) throws IOException {
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
