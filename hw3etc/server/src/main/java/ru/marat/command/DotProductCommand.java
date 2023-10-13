package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

@Component("dot")
@RequiredArgsConstructor
public class DotProductCommand implements ProductCommandInterface {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 2);
            var vector1 = vectorRepository.getByName(args[0]);
            var vector2 = vectorRepository.getByName(args[1]);
            return Double.toString(vector1.dot(vector2));
        } catch (IncorrectArgSizeException | NameNotFoundException e) {
            return e.getLocalizedMessage();
        }
    }
}
