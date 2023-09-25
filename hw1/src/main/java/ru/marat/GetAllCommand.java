package ru.marat;

public class GetAllCommand implements Command {
    private final VectorRepository vectorRepository;

    public GetAllCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        for (Named<Vector3d> namedVector3d : vectorRepository.getAll()) {
            System.out.printf("%s: %s\n", namedVector3d.name(), namedVector3d.object());
        }
    }
}
