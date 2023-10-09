package ru.marat.command;

import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

import java.io.PrintStream;

public class TripleProductCommand implements Command {
    private final PrintStream out;

    private final VectorRepository vectorRepository;

    public TripleProductCommand(PrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 3);
            var vector1 = vectorRepository.getByName(args[0]);
            var vector2 = vectorRepository.getByName(args[1]);
            var vector3 = vectorRepository.getByName(args[2]);
            out.println(vector1.dot(vector2.cross(vector3)));
        } catch (IncorrectArgSizeException | NameNotFoundException e) {
            out.println(e.getLocalizedMessage());
        }
    }
}
