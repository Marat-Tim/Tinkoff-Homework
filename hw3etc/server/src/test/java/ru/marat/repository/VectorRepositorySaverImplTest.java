package ru.marat.repository;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.marat.Vector3d;
import ru.marat.io.FileSystem;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VectorRepositorySaverImplTest {
    @InjectMocks
    VectorRepositorySaverImpl saver;

    @Mock
    FileSystem fileSystem;

    @DisplayName("Тест корректного сохранения")
    @Test
    @SneakyThrows
    void saveTest() {
        VectorRepository vectorRepository = mock(VectorRepository.class);
        when(vectorRepository.getAll()).thenReturn(List.of(
                new Named<>("v1", new Vector3d(1, 2, 3)),
                new Named<>("v2", new Vector3d(4, 5, 6)),
                new Named<>("v3", new Vector3d(7, 8, 9))));

        StringBuilder sb = new StringBuilder();
        doAnswer(args -> {
            sb.append(args.<String>getArgument(1));
            return null;
        }).when(fileSystem).writeString(any(), any());

        String expected = """
                v1|(1.0, 2.0, 3.0)
                v2|(4.0, 5.0, 6.0)
                v3|(7.0, 8.0, 9.0)
                """.replace("\n", System.lineSeparator());

        saver.save(vectorRepository, Path.of("file.txt"));

        assertEquals(expected, sb.toString());
    }

    @DisplayName("Тест корректного восстановления")
    @Test
    @SneakyThrows
    void loadTest() {
        when(fileSystem.readAllLines(any())).thenReturn(Arrays.stream("""
                v1|(1.0, 2.0, 3.0)
                v2|(4.0, 5.0, 6.0)
                v3|(7.0, 8.0, 9.0)
                """.split("\n")).toList());

        VectorRepository vectorRepository = mock(VectorRepository.class);

        List<Named<Vector3d>> vectors = new ArrayList<>();
        doAnswer(args -> {
            vectors.add(new Named<>(args.getArgument(0), args.getArgument(1)));
            return null;
        }).when(vectorRepository).addVector(any(), any());

        saver.load(vectorRepository, Path.of("file.txt"));

        assertEquals(vectors, List.of(new Named<>("v1", new Vector3d(1, 2, 3)),
                new Named<>("v2", new Vector3d(4, 5, 6)),
                new Named<>("v3", new Vector3d(7, 8, 9))));
    }
}