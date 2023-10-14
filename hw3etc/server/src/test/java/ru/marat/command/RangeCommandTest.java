package ru.marat.command;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.marat.Vector3d;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RangeCommandTest {

    @InjectMocks
    RangeCommand rangeCommand;

    @Mock
    VectorRepository vectorRepository;

    @DisplayName("Тест с корректными входными данными")
    @Test
    @SneakyThrows
    void handleSimpleTest() {
        String name = "v1";
        String[] args = {name};
        Vector3d vector = new Vector3d(1.0, 2.0, 3.0);

        when(vectorRepository.getByName(name)).thenReturn(vector);

        String expected = Double.toString(vector.length());
        String actual = rangeCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест, когда вектор с указанным именем отсутствует")
    @Test
    @SneakyThrows
    void handleTestWithMissingVector() {
        String name = "v1";
        String[] args = {name};

        when(vectorRepository.getByName(name)).thenThrow(NameNotFoundException.defaultException(name));

        String expected = NameNotFoundException.defaultException(name).getLocalizedMessage();
        String actual = rangeCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным количеством аргументов")
    @Test
    void handleTestWithIncorrectArgsSize() {
        String[] args = {"arg1", "arg2"};

        String expected = IncorrectArgSizeException.defaultException(2, 1).getLocalizedMessage();
        String actual = rangeCommand.handle(args);

        assertEquals(expected, actual);
    }
}
