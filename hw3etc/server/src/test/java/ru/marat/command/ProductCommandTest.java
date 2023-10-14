package ru.marat.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductCommandTest {

    @Mock
    private ProductCommandInterface mockCommandHandler;

    private ProductCommand productCommand;

    @BeforeEach
    void setUp() {
        Map<String, ProductCommandInterface> commands = new HashMap<>();
        commands.put("mockCommand", mockCommandHandler);

        productCommand = new ProductCommand(commands);
    }

    @Test
    void testHandleValidCommand() {
        Mockito.when(mockCommandHandler.handle(new String[]{"arg1", "arg2"})).thenReturn("MockCommandResult");

        String result = productCommand.handle(new String[]{"mockCommand", "arg1", "arg2"});

        assertEquals("MockCommandResult", result);
    }

    @Test
    void testHandleInvalidCommand() {
        String result = productCommand.handle(new String[]{"invalidCommand", "arg1", "arg2"});

        assertEquals("Неправильный тип произведения(должен быть mockCommand)", result);
    }
}
