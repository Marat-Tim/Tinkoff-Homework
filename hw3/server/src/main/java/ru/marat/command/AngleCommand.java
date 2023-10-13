package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

@Component("/angle")
@RequiredArgsConstructor
public class AngleCommand implements Command {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 2);
            var vector1 = vectorRepository.getByName(args[0]);
            var vector2 = vectorRepository.getByName(args[1]);
            return "%s deg".formatted(Math.toDegrees(vector1.minAngleWith(vector2)));
        } catch (NameNotFoundException | IncorrectArgSizeException e) {
            return e.getLocalizedMessage();
        }
    }
}
