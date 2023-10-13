package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;
import ru.marat.repository.VectorRepositorySaver;

import java.io.IOException;
import java.nio.file.Path;

@Component("/load")
@RequiredArgsConstructor
public class LoadCommand implements Command {
    private final VectorRepository vectorRepository;

    private final VectorRepositorySaver vectorRepositorySaver;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            vectorRepositorySaver.load(vectorRepository, Path.of(args[0]));
            return "Векторы из файла добавлены в коллекцию";
        } catch (IOException e) {
            return "Не получилось считать векторы из файла%nОшибка: %s".formatted(e);
        } catch (IncorrectArgSizeException | IllegalArgumentException e) {
            return e.getLocalizedMessage();
        }
    }
}
