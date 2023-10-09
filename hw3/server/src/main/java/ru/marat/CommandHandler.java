package ru.marat;

import ru.marat.command.*;
import ru.marat.repository.VectorRepository;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler {
    private final InputStream in;

    private final PrintStream out;

    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler(InputStream in, PrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        this.in = in;
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

    public void start() {
        Scanner scanner = new Scanner(in);
        String command;
        String[] args;
        do {
            var splitLine = scanner.nextLine().split(" ");
            command = splitLine[0];
            args = Arrays.copyOfRange(splitLine, 1, splitLine.length);
            if (commands.containsKey(command)) {
                commands.get(command).handle(args);
            } else {
                out.println("Неправильная команда");
            }
            out.println();
            out.flush();
        } while (!command.equals("/exit"));
    }
}
