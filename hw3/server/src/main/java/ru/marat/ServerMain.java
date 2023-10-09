package ru.marat;

import ru.marat.repository.InMemoryVectorRepository;
import ru.marat.repository.SaveToFileDecorator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class ServerMain {
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
        System.out.println("Новый клиент подключился");
        new CommandHandler(reader, out, vectorRepository).start();
    }

    private static void startProcessClients(ServerSocket socket) {
        //noinspection InfiniteLoopStatement
        while (true) {
            try (Socket client = socket.accept();
                 ServerPrintStream out = new ServerPrintStream(client.getOutputStream());
                 BufferedReader scanner =
                         new BufferedReaderWithLog(
                                 new InputStreamReader(client.getInputStream()),
                                 System.out,
                                 client);
                 SaveToFileDecorator vectorRepository =
                         new SaveToFileDecorator(new InMemoryVectorRepository(), pathToFile)) {
                processClient(out, scanner, vectorRepository);
            } catch (NullPointerException e) {
                System.out.printf("Пропало соединение с клиентом%n%s%n", e);
            } catch (IOException e) {
                System.out.printf("%s%n%s%n", e.getLocalizedMessage(), e.getCause());
            }
        }
    }

    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(getPort(args))) {
            System.out.println("Сервер начал работу");
            startProcessClients(socket);
        } catch (IOException e) {
            System.out.printf("Не получилось запустить сервер%n%s%n", e);
        }
    }
}