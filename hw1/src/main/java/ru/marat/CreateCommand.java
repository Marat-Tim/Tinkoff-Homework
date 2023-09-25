package ru.marat;

public class CreateCommand implements Command {
    private final VectorRepository vectorRepository;

    public CreateCommand(VectorRepository vectorRepository) {

        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        String name = args[0];
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);
        Vector3d vector3d = new Vector3d(x, y, z);
        vectorRepository.addVector(name, vector3d);
        System.out.println("CREATED");
    }
}
