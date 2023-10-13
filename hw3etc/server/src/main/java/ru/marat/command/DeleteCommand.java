package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.exception.NameNotFoundException;
import ru.marat.repository.VectorRepository;

@Component("/delete")
@RequiredArgsConstructor
public class DeleteCommand implements Command {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            vectorRepository.deleteByName(args[0]);
            return "Вектор удален";
        } catch (IncorrectArgSizeException | NameNotFoundException e) {
            return e.getLocalizedMessage();
        }
    }
}
