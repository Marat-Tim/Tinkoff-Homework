package ru.marat.command;

import ru.marat.repository.VectorRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductCommand implements Command {
    private final Map<String, Command> commands = new HashMap<>();

    public ProductCommand(VectorRepository vectorRepository) {
        commands.put("dot", new DotProductCommand(vectorRepository));
        commands.put("cross", new CrossProductCommand(vectorRepository));
        commands.put("triple", new TripleProductCommand(vectorRepository));
    }

    @Override
    public void handle(String[] args) {
        String command = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        if (commands.containsKey(command)) {
            commands.get(command).handle(newArgs);
        } else {
            System.out.println("Неправильный тип произведения(должен быть " +
                    String.join(" или ", commands.keySet()) + ")");
        }
    }
}
