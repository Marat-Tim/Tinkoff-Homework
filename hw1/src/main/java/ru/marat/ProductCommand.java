package ru.marat;

public class ProductCommand implements Command {
    private final VectorRepository vectorRepository;

    public ProductCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        var vector1 = vectorRepository.getByName(args[1]);
        var vector2 = vectorRepository.getByName(args[2]);
        if ("dot".equals(args[0])) {
            handleDot(vector1, vector2);
        }
        if ("cross".equals(args[0])) {
            handleCross(vector1, vector2);
        }
    }

    private void handleCross(Vector3d vector1, Vector3d vector2) {
        System.out.println(vector1.cross(vector2));
    }

    private void handleDot(Vector3d vector1, Vector3d vector2) {
        System.out.println(vector1.dot(vector2));
    }
}
