package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

@Component("triple")
@RequiredArgsConstructor
public class TripleProductCommand implements ProductCommandInterface {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 3);
            var vector1 = vectorRepository.getByName(args[0]);
            var vector2 = vectorRepository.getByName(args[1]);
            var vector3 = vectorRepository.getByName(args[2]);
            return Double.toString(vector1.dot(vector2.cross(vector3)));
        } catch (IncorrectArgSizeException | NameNotFoundException e) {
            return e.getLocalizedMessage();
        }
    }
}
