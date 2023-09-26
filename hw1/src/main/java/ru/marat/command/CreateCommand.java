package ru.marat.command;

import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.Vector3d;
import ru.marat.repository.VectorRepository;

public class CreateCommand implements Command {
    private final VectorRepository vectorRepository;

    public CreateCommand(VectorRepository vectorRepository) {
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
            System.out.println("Вектор добавлен");
        } catch (NumberFormatException e) {
            System.out.println("Элементы вектора должны быть вещественными числами");
        } catch (IncorrectArgSizeException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
