package ru.marat.io;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class FileSystemImpl implements FileSystem {
    @Override
    public void writeString(Path path, String text) throws IOException {
        Files.writeString(path, text);
    }

    @Override
    public List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
