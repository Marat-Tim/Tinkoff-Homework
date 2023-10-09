package ru.marat.command;

import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

import java.io.PrintStream;

public class RangeCommand implements Command {
    private final PrintStream out;

    private final VectorRepository vectorRepository;

    public RangeCommand(PrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            var vector3d = vectorRepository.getByName(args[0]);
            out.println(vector3d.length());
        } catch (IncorrectArgSizeException | NameNotFoundException e) {
            out.println(e.getLocalizedMessage());
        }
    }
}
