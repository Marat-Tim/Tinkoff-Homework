package ru.marat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler(VectorRepository vectorRepository) {
        commands.put("/create", new CreateCommand(vectorRepository));
        commands.put("/read", new GetAllCommand(vectorRepository));
        commands.put("/range", new RangeCommand(vectorRepository));
        commands.put("/angle", new AngleCommand(vectorRepository));
        commands.put("/product", new ProductCommand(vectorRepository));
        commands.put("/save", new SaveCommand(vectorRepository));
        commands.put("/load", new LoadCommand(vectorRepository));
        commands.put("/exit", args -> {});
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;
        String[] args;
        do {
            var splitLine = scanner.nextLine().split(" ");
            command = splitLine[0];
            args = Arrays.copyOfRange(splitLine, 1, splitLine.length);
            if (commands.containsKey(command)) {
                commands.get(command).handle(args);
            } else {
                System.out.println("Неправильная команда");
            }
        } while (!command.equals("/exit"));
    }
}
