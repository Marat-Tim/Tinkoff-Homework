package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.Vector3d;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

@Component("/create")
@RequiredArgsConstructor
public class CreateCommand implements Command {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 4);
            String name = args[0];
            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);
            Vector3d vector3d = new Vector3d(x, y, z);
            vectorRepository.addVector(name, vector3d);
            return "Вектор добавлен";
        } catch (NumberFormatException e) {
            return "Элементы вектора должны быть вещественными числами";
        } catch (IncorrectArgSizeException e) {
            return e.getLocalizedMessage();
        }
    }
}
