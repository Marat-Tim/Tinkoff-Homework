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
class TripleProductCommandTest {

    @InjectMocks
    TripleProductCommand tripleProductCommand;

    @Mock
    VectorRepository vectorRepository;

    @DisplayName("Тест с корректными входными данными")
    @Test
    @SneakyThrows
    void handleSimpleTest() {
        String name1 = "v1", name2 = "v2", name3 = "v3";
        String[] args = {name1, name2, name3};
        Vector3d vector1 = new Vector3d(1.0, 2.0, 3.0);
        Vector3d vector2 = new Vector3d(4.0, 5.0, 6.0);
        Vector3d vector3 = new Vector3d(7.0, 8.0, 9.0);

        when(vectorRepository.getByName(name1)).thenReturn(vector1);
        when(vectorRepository.getByName(name2)).thenReturn(vector2);
        when(vectorRepository.getByName(name3)).thenReturn(vector3);

        double expected = vector1.dot(vector2.cross(vector3));
        String actual = tripleProductCommand.handle(args);

        assertEquals(Double.toString(expected), actual);
    }

    @DisplayName("Тест, когда один из векторов с указанным именем отсутствует")
    @Test
    @SneakyThrows
    void handleTestWithMissingVector() {
        String name1 = "v1", name2 = "v2", name3 = "v3";
        String[] args = {name1, name2, name3};
        Vector3d vector1 = new Vector3d(1.0, 2.0, 3.0);
        Vector3d vector2 = new Vector3d(4.0, 5.0, 6.0);

        when(vectorRepository.getByName(name1)).thenReturn(vector1);
        when(vectorRepository.getByName(name2)).thenReturn(vector2);
        when(vectorRepository.getByName(name3)).thenThrow(NameNotFoundException.defaultException(name3));

        String expected = NameNotFoundException.defaultException(name3).getLocalizedMessage();
        String actual = tripleProductCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным количеством аргументов")
    @Test
    void handleTestWithIncorrectArgsSize() {
        String[] args = {"arg1", "arg2"};

        String expected = IncorrectArgSizeException.defaultException(2, 3).getLocalizedMessage();
        String actual = tripleProductCommand.handle(args);

        assertEquals(expected, actual);
    }
}
