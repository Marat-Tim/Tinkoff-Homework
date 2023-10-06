package ru.marat;

import ru.marat.repository.InMemoryVectorRepository;
import ru.marat.repository.VectorRepository;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Server {
    private static int getPort(String[] args) {
        if (args.length == 1) {
            return Integer.parseInt(args[0]);
        } else if (args.length == 0) {
            return 8080;
        }
        throw new IllegalArgumentException("Неправильное количество аргументов");
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket socket = new ServerSocket(getPort(args))) {
            System.out.println("Сервер начал работу");
            while (true) {
                try (Socket client = socket.accept();
                     PrintStream out = new MyPrintStream(client.getOutputStream())) {
                    System.out.println("Новый клиент подключился");
                    VectorRepository vectorRepository = new InMemoryVectorRepository();
                    new CommandHandler(
                            new LoggingInputStream(client.getInputStream(), System.out),
                            out,
                            vectorRepository).start();
                } catch (NoSuchElementException e) {
                    System.out.printf("Пропало соединение с клиентом\n%s\n", e);
                }
            }
        }
    }
}