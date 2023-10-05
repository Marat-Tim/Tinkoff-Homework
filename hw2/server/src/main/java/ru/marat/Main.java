package ru.marat;

import ru.marat.repository.InMemoryVectorRepository;
import ru.marat.repository.VectorRepository;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static int getPort(String[] args) {
        if (args.length == 1) {
            return Integer.parseInt(args[0]);
        } else if (args.length == 0) {
            return 8080;
        }
        throw new IllegalArgumentException("Неправильное количество аргументов");
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket socket = new ServerSocket(getPort(args));
             Socket client = socket.accept();
             PrintStream out = new PrintStream(client.getOutputStream())) {
            System.out.println("Сервер начал работу");
            VectorRepository vectorRepository = new InMemoryVectorRepository();
            new CommandHandler(
                    client.getInputStream(),
                    out,
                    vectorRepository).start();
        }
    }
}