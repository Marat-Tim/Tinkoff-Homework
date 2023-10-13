package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component("/product")
@RequiredArgsConstructor
public class ProductCommand implements Command {
    private final Map<String, ProductCommandInterface> commands;

    @Override
    public String handle(String[] args) {
        String command = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        if (!commands.containsKey(command)) {
            return "Неправильный тип произведения(должен быть " +
                    String.join(" или ", commands.keySet()) + ")";
        }
        return commands.get(command).handle(newArgs);
    }
}
