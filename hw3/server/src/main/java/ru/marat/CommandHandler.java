package ru.marat;

import ru.marat.command.*;
import ru.marat.repository.VectorRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final BufferedReader reader;

    private final ServerPrintStream out;

    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler(BufferedReader reader, ServerPrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        this.reader = reader;
        commands.put("/create", new CreateCommand(out, vectorRepository));
        commands.put("/read", new GetAllCommand(out, vectorRepository));
        commands.put("/range", new RangeCommand(out, vectorRepository));
        commands.put("/angle", new AngleCommand(out, vectorRepository));
        commands.put("/product", new ProductCommand(out, vectorRepository));
        commands.put("/save", new SaveCommand(out, vectorRepository));
        commands.put("/load", new LoadCommand(out, vectorRepository));
        commands.put("/exit", args -> {
        });
    }

    public void start() throws IOException {
        String command;
        String[] args;
        do {
            var splitLine = reader.readLine().split(" ");
            command = splitLine[0];
            args = Arrays.copyOfRange(splitLine, 1, splitLine.length);
            if (commands.containsKey(command)) {
                commands.get(command).handle(args);
            } else {
                out.println("Неправильная команда");
            }
            out.sendToClient();
        } while (!command.equals("/exit"));
    }
}
