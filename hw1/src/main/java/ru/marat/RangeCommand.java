package ru.marat;

public class RangeCommand implements Command {
    private final VectorRepository vectorRepository;

    public RangeCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            var vector3d = vectorRepository.getByName(args[0]);
            System.out.println(vector3d.length());
        } catch (IncorrectArgSizeException | NameNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
