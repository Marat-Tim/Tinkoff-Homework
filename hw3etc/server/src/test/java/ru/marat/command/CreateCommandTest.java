package ru.marat.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.marat.exception.IncorrectArgSizeException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CreateCommandTest {
    @InjectMocks
    CreateCommand createCommand;

    @DisplayName("Тест с корректными входными данными")
    @Test
    void handleSimpleTest() {
        String[] args = {"v1", "1.0", "2.0", "3.0"};

        String expected = "Вектор добавлен";

        String actual = createCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным форматом чисел")
    @Test
    void handleTestWithNumberFormatException() {
        String[] args = {"v1", "1.0", "2.0", "not_a_number"};

        String expected = "Элементы вектора должны быть вещественными числами";

        String actual = createCommand.handle(args);

        assertEquals(expected, actual);
    }

    @DisplayName("Тест с неправильным количеством аргументов")
    @Test
    void handleTestWithIncorrectArgsSize() {
        String[] args = {"arg1", "arg2", "arg3"};

        String expected = IncorrectArgSizeException.defaultException(3, 4).getLocalizedMessage();

        String actual = createCommand.handle(args);

        assertEquals(expected, actual);
    }
}
