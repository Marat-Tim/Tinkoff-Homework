package ru.marat.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.marat.Vector3d;
import ru.marat.repository.Named;
import ru.marat.repository.VectorRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadCommandTest {

    @InjectMocks
    ReadCommand readCommand;

    @Mock
    VectorRepository vectorRepository;

    @DisplayName("Тест с корректными входными данными и непустой страницей")
    @Test
    void handleSimpleTestWithNonEmptyPage() {
        int pageSize = 3;
        int pageNumber = 1;
        String[] args = {String.valueOf(pageSize), String.valueOf(pageNumber)};

        List<Named<Vector3d>> vectors = Arrays.asList(
                new Named<>("v1", new Vector3d(1.0, 2.0, 3.0)),
                new Named<>("v2", new Vector3d(4.0, 5.0, 6.0)),
                new Named<>("v3", new Vector3d(7.0, 8.0, 9.0))
        );

        //noinspection ConstantValue
        when(vectorRepository.getPage(pageSize, pageNumber - 1)).thenReturn(vectors);

        String expected = """
                Страница 1, размер страниц: 3
                v1: (1.0, 2.0, 3.0)
                v2: (4.0, 5.0, 6.0)
                v3: (7.0, 8.0, 9.0)""".replace("\n", System.lineSeparator());

        String actual = readCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с корректными входными данными и пустой страницей")
    @Test
    void handleSimpleTestWithEmptyPage() {
        int pageSize = 5;
        int pageNumber = 2;
        String[] args = {String.valueOf(pageSize), String.valueOf(pageNumber)};

        when(vectorRepository.getPage(pageSize, pageNumber - 1)).thenReturn(List.of());

        String expected = """
                Страница 2, размер страниц: 5
                Пусто""".replace("\n", System.lineSeparator());

        String actual = readCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным количеством аргументов")
    @Test
    void handleTestWithIncorrectArgsSize() {
        String[] args = {"arg1"};

        String expected =
                "У этой команды должно быть 2 аргумента: 1-ый - размер страницы, " +
                        "2-ой - номер страницы(нумерация с 1)";
        String actual = readCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным форматом аргументов")
    @Test
    void handleTestWithInvalidArguments() {
        String[] args = {"arg1", "arg2"};

        String expected =
                "У этой команды должно быть 2 аргумента: 1-ый - размер страницы, " +
                        "2-ой - номер страницы(нумерация с 1)";
        String actual = readCommand.handle(args);

        assertEquals(expected, actual);
    }
}
