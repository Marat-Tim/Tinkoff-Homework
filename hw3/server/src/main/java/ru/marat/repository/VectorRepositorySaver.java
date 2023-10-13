package ru.marat.repository;

import java.io.IOException;
import java.nio.file.Path;

public interface VectorRepositorySaver {
    void save(VectorRepository vectorRepository, Path pathToFile) throws IOException;

    void load(VectorRepository vectorRepository, Path pathToFile) throws IOException;
}
