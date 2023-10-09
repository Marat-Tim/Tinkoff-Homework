package ru.marat.command;

import ru.marat.VectorRepositorySaver;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

public class LoadCommand implements Command {
    private final PrintStream out;

    private final VectorRepository vectorRepository;

    public LoadCommand(PrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            new VectorRepositorySaver(vectorRepository).load(Path.of(args[0]));
            out.println("Векторы из файла добавлены в коллекцию");
        } catch (IOException e) {
            out.printf("Не получилось считать векторы из файла%nОшибка: %s%n", e);
        } catch (IncorrectArgSizeException | IllegalArgumentException e) {
            out.println(e.getLocalizedMessage());
        }
    }
}
