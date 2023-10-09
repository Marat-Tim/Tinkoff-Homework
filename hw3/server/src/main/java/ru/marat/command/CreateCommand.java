package ru.marat.command;

import ru.marat.Vector3d;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

import java.io.PrintStream;

public class CreateCommand implements Command {
    private final PrintStream out;

    private final VectorRepository vectorRepository;

    public CreateCommand(PrintStream out, VectorRepository vectorRepository) {
        this.out = out;
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 4);
            String name = args[0];
            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);
            Vector3d vector3d = new Vector3d(x, y, z);
            vectorRepository.addVector(name, vector3d);
            out.println("Вектор добавлен");
        } catch (NumberFormatException e) {
            out.println("Элементы вектора должны быть вещественными числами");
        } catch (IncorrectArgSizeException e) {
            out.println(e.getLocalizedMessage());
        }
    }
}
