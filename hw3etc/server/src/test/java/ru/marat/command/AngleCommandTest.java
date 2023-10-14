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
class AngleCommandTest {
    @InjectMocks
    AngleCommand angleCommand;

    @Mock
    VectorRepository vectorRepository;

    @DisplayName("Тест с корректными входными данными")
    @Test
    @SneakyThrows
    void handleSimpleTest() {
        String name1 = "v1", name2 = "v2";
        Vector3d vector1 = new Vector3d(1, 1, 0), vector2 = new Vector3d(1, 0, 0);
        when(vectorRepository.getByName(name1)).thenReturn(vector1);
        when(vectorRepository.getByName(name2)).thenReturn(vector2);

        String expected = Math.toDegrees(vector1.minAngleWith(vector2)) + " deg";

        String actual = angleCommand.handle(new String[]{name1, name2});

        assertEquals(expected, actual);
    }

    @DisplayName("Тест, когда вектора с таким именем нету в хранилище")
    @Test
    @SneakyThrows
    void handleTestWithMissingVector() {
        String name1 = "v1", name2 = "v2";
        Vector3d vector1 = new Vector3d(1, 1, 0);
        when(vectorRepository.getByName(name1)).thenReturn(vector1);
        when(vectorRepository.getByName(name2)).thenThrow(NameNotFoundException.defaultException(name2));

        String expected = NameNotFoundException.defaultException(name2).getLocalizedMessage();

        String actual = angleCommand.handle(new String[]{name1, name2});

        assertEquals(expected, actual);
    }

    @DisplayName("Тест, когда передается неправильное количество аргументов")
    @Test
    @SneakyThrows
    void handleTestWithIncorrectArgsSize() {
        String expected = IncorrectArgSizeException.defaultException(3, 2).getLocalizedMessage();

        String actual = angleCommand.handle(new String[]{"arg1", "arg2", "arg3"});

        assertEquals(expected, actual);
    }
}