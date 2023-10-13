package ru.marat.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.marat.exception.IncorrectArgSizeException;
import ru.marat.repository.VectorRepository;

@Component("/read")
@RequiredArgsConstructor
public class GetAllCommand implements Command {
    private final VectorRepository vectorRepository;

    @Override
    public String handle(String[] args) {
        try {
            ArgsUtils.checkArgsSize(args, 0);
            StringBuilder sb = new StringBuilder();
            var vectors = vectorRepository.getAll().stream().toList();
            for (int i = 0; i < vectors.size() - 1; i++) {
                sb.append("%s: %s%n".formatted(vectors.get(i).name(), vectors.get(i).object()));
            }
            sb.append("%s: %s".formatted(vectors.get(vectors.size() - 1).name(),
                    vectors.get(vectors.size() - 1).object()));
            return sb.toString();
        } catch (IncorrectArgSizeException e) {
            return e.getLocalizedMessage();
        }
    }
}
