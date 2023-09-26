package ru.marat.command;

import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.Vector3d;
import ru.marat.repository.VectorRepository;

public class ProductCommand implements Command {
    private final VectorRepository vectorRepository;

    public ProductCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 3);
            var vector1 = vectorRepository.getByName(args[1]);
            var vector2 = vectorRepository.getByName(args[2]);
            switch (args[0]) {
                case "dot" -> handleDot(vector1, vector2);
                case "cross" -> handleCross(vector1, vector2);
                default -> System.out.println("Неправильный тип произведения(должен быть dot или cross)");
            }
        } catch (NameNotFoundException | IncorrectArgSizeException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void handleCross(Vector3d vector1, Vector3d vector2) {
        System.out.println(vector1.cross(vector2));
    }

    private void handleDot(Vector3d vector1, Vector3d vector2) {
        System.out.println(vector1.dot(vector2));
    }
}
