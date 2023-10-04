package ru.marat.command;

import ru.marat.Vector3d;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.Named;
import ru.marat.repository.VectorRepository;

public class GetAllCommand implements Command {
    private final VectorRepository vectorRepository;

    public GetAllCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 0);
            for (Named<Vector3d> namedVector3d : vectorRepository.getAll()) {
                System.out.printf("%s: %s\n", namedVector3d.name(), namedVector3d.object());
            }
        } catch (IncorrectArgSizeException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
