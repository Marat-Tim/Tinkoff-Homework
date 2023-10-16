package ru.marat;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.marat.command.Command;
import ru.marat.exception.VectorRepositoryException;
import ru.marat.io.ServerPrintStream;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandHandlerTest {
    CommandHandler commandHandler;

    @DisplayName("Тест, где выполняется одна корректная команда")
    @Test
    @SneakyThrows
    void startTestWhenCommandIsPresent() {
        Command command = mock(Command.class);
        String commandName = "/command";
        String response = "response";

        BufferedReader reader = new BufferedReader(new StringReader("%s\n/exit".formatted(commandName)));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServerPrintStream writer = new ServerPrintStream(outputStream);

        when(command.handle(any())).thenReturn(response);

        String expected = """
                %s
                                
                """.formatted(response).replace("\n", System.lineSeparator());

        commandHandler = new CommandHandler(writer, reader, Map.of(commandName, command));
        commandHandler.start();

        String actual = outputStream.toString();

        assertEquals(expected, actual);
    }

    @DisplayName("Тест, где выполняется одна некорректная команда")
    @Test
    @SneakyThrows
    void startTestWhenCommandIsNotPresent() {
        String commandName = "/not_command";

        BufferedReader reader = new BufferedReader(new StringReader("%s\n/exit".formatted(commandName)));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServerPrintStream writer = new ServerPrintStream(outputStream);

        String expected = """
                Неправильная команда
                                
                """.replace("\n", System.lineSeparator());

        commandHandler = new CommandHandler(writer, reader, Map.of());
        commandHandler.start();

        String actual = outputStream.toString();

        assertEquals(expected, actual);
    }

    @DisplayName("Тест, где команда выдает исключение VectorRepositoryException")
    @Test
    @SneakyThrows
    void startTestWhenCommandThrowException() {
        Command command = mock(Command.class);
        String commandName = "/command";
        String errorText = "ошибка";

        BufferedReader reader = new BufferedReader(new StringReader("%s\n/exit".formatted(commandName)));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServerPrintStream writer = new ServerPrintStream(outputStream);

        when(command.handle(any())).thenThrow(new VectorRepositoryException(errorText));

        String expected = """
                %s
                                
                """.formatted(errorText).replace("\n", System.lineSeparator());

        commandHandler = new CommandHandler(writer, reader, Map.of(commandName, command));
        commandHandler.start();

        String actual = outputStream.toString();

        assertEquals(expected, actual);
    }

    @DisplayName("Тест, где выполняется несколько команд")
    @Test
    @SneakyThrows
    void startTestWithSeveralCommands() {
        String command1Name = "/command1";
        Command command1 = mock(Command.class);
        String response1 = "response1";
        when(command1.handle(any())).thenReturn(response1);

        String command2Name = "/command2";
        Command command2 = mock(Command.class);
        String response2 = "response2";
        when(command2.handle(any())).thenReturn(response2);

        String command3Name = "/command3";
        Command command3 = mock(Command.class);
        String errorText = "error";
        when(command3.handle(any())).thenThrow(new VectorRepositoryException(errorText));


        BufferedReader reader = new BufferedReader(
                new StringReader("%s\n%s\n%s\n%s\nnot_command\n/exit"
                        .formatted(command2Name, command1Name, command3Name, command2Name)));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServerPrintStream writer = new ServerPrintStream(outputStream);


        String expected = """
                %s
                                
                %s
                                
                %s
                                
                %s
                                
                Неправильная команда
                                
                """.formatted(response2, response1, errorText, response2)
                .replace("\n", System.lineSeparator());

        commandHandler = new CommandHandler(writer, reader,
                Map.of(command1Name, command1,
                        command2Name, command2,
                        command3Name, command3));
        commandHandler.start();

        String actual = outputStream.toString();

        assertEquals(expected, actual);
    }
}