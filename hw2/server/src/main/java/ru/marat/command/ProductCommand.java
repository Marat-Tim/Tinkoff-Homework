package ru.marat.command;

import ru.marat.repository.VectorRepository;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductCommand implements Command {
    private final PrintStream out;

    private final Map<String, Command> commands = new HashMap<>();

    public ProductCommand(PrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        commands.put("dot", new DotProductCommand(out, vectorRepository));
        commands.put("cross", new CrossProductCommand(out, vectorRepository));
        commands.put("triple", new TripleProductCommand(out, vectorRepository));
    }

    @Override
    public void handle(String[] args) {
        String command = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        if (!commands.containsKey(command)) {
            out.println("Неправильный тип произведения(должен быть " +
                    String.join(" или ", commands.keySet()) + ")");
        }
        commands.get(command).handle(newArgs);
    }
}
