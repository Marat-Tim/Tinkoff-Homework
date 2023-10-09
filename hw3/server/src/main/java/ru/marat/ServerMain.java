package ru.marat;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import ru.marat.repository.InMemoryVectorRepository;
import ru.marat.repository.SaveToFileDecorator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

@Log4j2
public class ServerMain implements CommandLineRunner {
    private static final Path pathToFile = Path.of("file.txt");

    private static int getPort(String[] args) {
        if (args.length == 1) {
            return Integer.parseInt(args[0]);
        } else if (args.length == 0) {
            return 8080;
        }
        throw new IllegalArgumentException("Неправильное количество аргументов");
    }

    private static void processClient(ServerPrintStream out,
                                      BufferedReader reader,
                                      SaveToFileDecorator vectorRepository) throws IOException {
        new CommandHandler(reader, out, vectorRepository).start();
    }

    private static void startProcessClients(ServerSocket socket) {
        //noinspection InfiniteLoopStatement
        while (true) {
            try (Socket client = socket.accept();
                 ServerPrintStream out = new ServerPrintStream(client.getOutputStream());
                 BufferedReader scanner =
                         new BufferedReaderWithLog(new InputStreamReader(client.getInputStream()));
                 SaveToFileDecorator vectorRepository =
                         new SaveToFileDecorator(new InMemoryVectorRepository(), pathToFile)) {
                log.info("Новый клиент {} подключился", client);
                processClient(out, scanner, vectorRepository);
            } catch (NullPointerException e) {
                log.info("Пропало соединение с клиентом", e);
            } catch (IOException e) {
                log.error(e, e.getCause());
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
    }

    @Override
    public void run(String... args) {
        try (ServerSocket socket = new ServerSocket(getPort(args))) {
            log.info("Сокет {} начал работу", socket);
            startProcessClients(socket);
        } catch (IOException e) {
            log.error("Не получилось запустить сервер", e);
        }
    }
}