package ru.marat.command;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;
import ru.marat.repository.VectorRepositorySaver;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class SaveCommandTest {

    @InjectMocks
    SaveCommand saveCommand;

    @Mock
    VectorRepository vectorRepository;

    @Mock
    VectorRepositorySaver vectorRepositorySaver;

    @DisplayName("Тест с корректными входными данными")
    @Test
    void handleSimpleTest() {
        String filePath = "file.txt";
        String[] args = {filePath};

        String expected = "Векторы сохранены в файл";
        String actual = saveCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с ошибкой ввода/вывода")
    @Test
    @SneakyThrows
    void handleTestWithIOException() {
        String filePath = "file.txt";
        String[] args = {filePath};

        doThrow(new IOException("Test IOException"))
                .when(vectorRepositorySaver).save(vectorRepository, Path.of(filePath));

        String expected = ("Не получилось сохранить векторы в файл%n" +
                "Ошибка: java.io.IOException: Test IOException").formatted();
        String actual = saveCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным количеством аргументов")
    @Test
    void handleTestWithIncorrectArgsSize() {
        String[] args = {"arg1", "arg2"};

        String expected = IncorrectArgSizeException.defaultException(2, 1).getLocalizedMessage();
        String actual = saveCommand.handle(args);

        assertEquals(expected, actual);
    }
}
