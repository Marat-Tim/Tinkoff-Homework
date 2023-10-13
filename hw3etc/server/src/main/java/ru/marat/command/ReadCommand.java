package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

@Component("/read")
@RequiredArgsConstructor
public class ReadCommand implements Command {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 2);
            int pageSize = Integer.parseInt(args[0]);
            int pageNumber = Integer.parseInt(args[1]);
            var vectors = vectorRepository.getPage(pageSize, pageNumber - 1).stream().toList();
            StringBuilder sb = new StringBuilder();
            sb.append("Страница %d, размер страниц: %d%n".formatted(pageNumber, pageSize));
            if (vectors.isEmpty()) {
                sb.append("Пусто");
                return sb.toString();
            }
            for (int i = 0; i < vectors.size() - 1; i++) {
                sb.append("%s: %s%n".formatted(vectors.get(i).name(), vectors.get(i).object()));
            }
            sb.append("%s: %s".formatted(vectors.get(vectors.size() - 1).name(),
                    vectors.get(vectors.size() - 1).object()));
            return sb.toString();
        } catch (IncorrectArgSizeException | NumberFormatException e) {
            return "У этой команды должно быть 2 аргумента: 1-ый - размер страницы, " +
                    "2-ой - номер страницы(нумерация с 1)";
        }
    }
}
