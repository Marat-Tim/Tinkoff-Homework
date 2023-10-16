package ru.marat.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileSystem {
    void writeString(Path path, String text) throws IOException;

    List<String> readAllLines(Path path) throws IOException;
}
