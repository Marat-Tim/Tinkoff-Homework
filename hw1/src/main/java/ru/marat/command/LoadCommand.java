package ru.marat.command;

import ru.marat.Vector3d;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoadCommand implements Command {
    private final VectorRepository vectorRepository;

    public LoadCommand(VectorRepository vectorRepository) {
        this.vectorRepository = vectorRepository;
    }

    @Override
    public void handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 1);
            var lines = Files.readAllLines(Path.of(args[0]));
            for (var line : lines) {
                var splitLine = line.split("\\|");
                if (splitLine.length != 2) {
                    throw new IOException("Неправильный формат данных в файле");
                }
                vectorRepository.addVector(splitLine[0], Vector3d.parseVector3d(splitLine[1]));
            }
            System.out.println("Векторы из файла добавлены в коллекцию");
        } catch (IOException e) {
            System.out.printf("Не получилось считать векторы из файла\nОшибка: %s\n", e);
        } catch (IncorrectArgSizeException | IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
