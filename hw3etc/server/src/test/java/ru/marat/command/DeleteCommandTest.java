package ru.marat.command;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class DeleteCommandTest {

    @InjectMocks
    DeleteCommand deleteCommand;

    @Mock
    VectorRepository vectorRepository;

    @DisplayName("Тест с корректными входными данными")
    @Test
    void handleSimpleTest() {
        String name = "v1";

        String expected = "Вектор удален";

        String actual = deleteCommand.handle(new String[]{name});

        assertEquals(expected, actual);
    }

    @DisplayName("Тест, когда вектор с указанным именем отсутствует")
    @Test
    @SneakyThrows
    void handleTestWithMissingVector() {
        String name = "v1";

        doThrow(NameNotFoundException.defaultException(name)).when(vectorRepository).deleteByName(name);

        String expected = NameNotFoundException.defaultException(name).getLocalizedMessage();

        String actual = deleteCommand.handle(new String[]{name});

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным количеством аргументов")
    @Test
    void handleTestWithIncorrectArgsSize() {
        String expected = IncorrectArgSizeException.defaultException(2, 1).getLocalizedMessage();

        String actual = deleteCommand.handle(new String[]{"arg1", "arg2"});

        assertEquals(expected, actual);
    }
}
