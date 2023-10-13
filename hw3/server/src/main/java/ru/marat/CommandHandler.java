package ru.marat;

import ru.marat.command.Command;
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
        String command;
        String[] args;
        do {
            var splitLine = reader.readLine().split(" ");
            command = splitLine[0];
            args = Arrays.copyOfRange(splitLine, 1, splitLine.length);
            if (commands.containsKey(command)) {
                var tmp = commands.get(command).handle(args);
                writer.println(tmp);
            } else {
                writer.println("Неправильная команда");
            }
            writer.sendToClient();
        } while (!command.equals("/exit"));
    }
}
