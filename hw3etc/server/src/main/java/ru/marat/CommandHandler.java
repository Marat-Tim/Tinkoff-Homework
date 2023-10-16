package ru.marat;

import ru.marat.command.Command;
import ru.marat.exception.VectorRepositoryException;
import ru.marat.io.ServerPrintStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class CommandHandler {
    private final BufferedReader reader;

    private final ServerPrintStream writer;

    private final Map<String, Command> commands;

    public CommandHandler(ServerPrintStream writer, BufferedReader reader, Map<String, Command> commands) {
        this.writer = writer;
        this.reader = reader;
        this.commands = commands;
    }

    public void start() throws IOException {
        String[] splitLine = reader.readLine().split(" ");
        String command = splitLine[0];
        String[] args = Arrays.copyOfRange(splitLine, 1, splitLine.length);
        while (!command.equals("/exit")) {
            if (commands.containsKey(command)) {
                try {
                    String tmp = commands.get(command).handle(args);
                    writer.println(tmp);
                } catch (VectorRepositoryException e) {
                    writer.println(e.getLocalizedMessage());
                }
            } else {
                writer.println("Неправильная команда");
            }
            writer.sendToClient();

            splitLine = reader.readLine().split(" ");
            command = splitLine[0];
            args = Arrays.copyOfRange(splitLine, 1, splitLine.length);
        }
    }
}
