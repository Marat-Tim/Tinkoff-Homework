package ru.marat;

public class RangeCommand implements Command {
    private final VectorRepository vectorRepository;

    public RangeCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        var vector3d = vectorRepository.getByName(args[0]);
        System.out.println(vector3d.length());
    }
}
