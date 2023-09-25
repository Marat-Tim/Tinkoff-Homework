package ru.marat;

import java.io.UncheckedIOException;
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
                try {
                    commands.get(command).handle(args);
                } catch (NameNotFoundException e) {
                    System.out.println("Вектора с таким именем не существует");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Неправильное количество аргументов");
                } catch (UncheckedIOException e) {
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Значение вектора должно быть числом");
                }
            } else {
                System.out.println("Неправильная команда");
            }
        } while (!command.equals("/exit"));
    }
}
