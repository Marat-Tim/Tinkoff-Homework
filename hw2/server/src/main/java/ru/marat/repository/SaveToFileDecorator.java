package ru.marat.repository;

import ru.marat.Vector3d;
import ru.marat.VectorRepositorySaver;
import ru.marat.exception.NameNotFoundException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class SaveToFileDecorator implements VectorRepository, AutoCloseable {
    private final VectorRepository vectorRepository;

    private final Path pathToFile;

    public SaveToFileDecorator(VectorRepository vectorRepository, Path pathToFile) throws IOException {
        this.vectorRepository = vectorRepository;
        this.pathToFile = pathToFile;
        try {
            new VectorRepositorySaver(vectorRepository).load(pathToFile);
        } catch (IOException e) {
            throw new IOException("Не получилось считать векторы из файла при запуске", e);
        }
    }

    @Override
    public void addVector(String name, Vector3d vector3d) {
        vectorRepository.addVector(name, vector3d);
    }

    @Override
    public Collection<Named<Vector3d>> getAll() {
        return vectorRepository.getAll();
    }

    @Override
    public Vector3d getByName(String name) throws NameNotFoundException {
        return vectorRepository.getByName(name);
    }

    @Override
    public void close() throws IOException {
        try {
            new VectorRepositorySaver(vectorRepository).save(pathToFile);
        } catch (IOException e) {
            throw new IOException("Не получилось сохранить векторы в файл", e);
        }
    }
}
