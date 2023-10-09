package ru.marat.command;

import ru.marat.VectorRepositorySaver;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

public class SaveCommand implements Command {
    private final PrintStream out;

    private final VectorRepository vectorRepository;

    public SaveCommand(PrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            new VectorRepositorySaver(vectorRepository).save(Path.of(args[0]));
            out.println("Векторы сохранены в файл");
        } catch (IncorrectArgSizeException e) {
            out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            out.printf("Не получилось сохранить векторы в файл%nОшибка: %s%n", e);
        }
    }
}
