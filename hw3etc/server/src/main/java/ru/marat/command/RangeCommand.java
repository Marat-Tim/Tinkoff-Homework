package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

@Component("/range")
@RequiredArgsConstructor
public class RangeCommand implements Command {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            var vector3d = vectorRepository.getByName(args[0]);
            return Double.toString(vector3d.length());
        } catch (IncorrectArgSizeException | NameNotFoundException e) {
            return e.getLocalizedMessage();
        }
    }
}
