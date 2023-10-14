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
class DotProductCommandTest {
    @InjectMocks
    DotProductCommand dotProductCommand;

    @Mock
    VectorRepository vectorRepository;

    @DisplayName("Тест с корректными входными данными")
    @Test
    @SneakyThrows
    void handleSimpleTest() {
        String name1 = "v1", name2 = "v2";
        Vector3d vector1 = new Vector3d(1.0, 2.0, 3.0);
        Vector3d vector2 = new Vector3d(4.0, 5.0, 6.0);

        when(vectorRepository.getByName(name1)).thenReturn(vector1);
        when(vectorRepository.getByName(name2)).thenReturn(vector2);

        String[] args = {name1, name2};
        double expected = vector1.dot(vector2);
        String actual = dotProductCommand.handle(args);

        assertEquals(Double.toString(expected), actual);
    }

    @DisplayName("Тест, когда вектор с указанным именем отсутствует")
    @Test
    @SneakyThrows
    void handleTestWithMissingVector() {
        String name1 = "v1", name2 = "v2";
        Vector3d vector1 = new Vector3d(1.0, 2.0, 3.0);

        when(vectorRepository.getByName(name1)).thenReturn(vector1);
        when(vectorRepository.getByName(name2)).thenThrow(NameNotFoundException.defaultException(name2));

        String[] args = {name1, name2};
        String expected = NameNotFoundException.defaultException(name2).getLocalizedMessage();
        String actual = dotProductCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным количеством аргументов")
    @Test
    void handleTestWithIncorrectArgsSize() {
        String[] args = {"arg1"};

        String expected = IncorrectArgSizeException.defaultException(1, 2).getLocalizedMessage();
        String actual = dotProductCommand.handle(args);

        assertEquals(expected, actual);
    }
}
