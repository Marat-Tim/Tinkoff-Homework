package ru.marat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.marat.repository.InMemoryVectorRepository;
import ru.marat.repository.SavingToFileVectorRepositoryDecorator;
import ru.marat.repository.VectorRepositorySaver;

import java.io.IOException;
import java.nio.file.Path;

@Configuration
public class VectorRepository {
    @Bean
    public SavingToFileVectorRepositoryDecorator vectorRepositoryBean(
            @Value("${my.file-with-vectors}") String fileWithVectors,
            VectorRepositorySaver vectorRepositorySaver) throws IOException {
        return new SavingToFileVectorRepositoryDecorator(new InMemoryVectorRepository(), vectorRepositorySaver,
                Path.of(fileWithVectors));
    }
}
