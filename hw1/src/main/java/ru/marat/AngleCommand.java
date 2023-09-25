package ru.marat;

public class AngleCommand implements Command {
    private final VectorRepository vectorRepository;

    public AngleCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        var vector1 = vectorRepository.getByName(args[0]);
        var vector2 = vectorRepository.getByName(args[1]);
        System.out.printf("%s deg\n", Math.toDegrees(vector1.minAngleWith(vector2)));
    }
}
