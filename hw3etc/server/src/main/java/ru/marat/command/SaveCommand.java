package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;
import ru.marat.repository.VectorRepositorySaver;

import java.io.IOException;
import java.nio.file.Path;

@Component("/save")
@RequiredArgsConstructor
public class SaveCommand implements Command {
    private final VectorRepository vectorRepository;

    private final VectorRepositorySaver vectorRepositorySaver;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            vectorRepositorySaver.save(vectorRepository, Path.of(args[0]));
            return "Векторы сохранены в файл";
        } catch (IncorrectArgSizeException e) {
            return e.getLocalizedMessage();
        } catch (IOException e) {
            return "Не получилось сохранить векторы в файл%nОшибка: %s".formatted(e);
        }
    }
}
